package com.primeholding.rushhour.controller;

import com.primeholding.rushhour.Constants;
import com.primeholding.rushhour.entity.Role;
import com.primeholding.rushhour.entity.User;
import com.primeholding.rushhour.model.UserGetModel;
import com.primeholding.rushhour.model.UserPostModel;
import com.primeholding.rushhour.security.JwtTokenProvider;
import com.primeholding.rushhour.security.exception.AppException;
import com.primeholding.rushhour.security.model.JwtAuthenticationResponse;
import com.primeholding.rushhour.security.model.LoginRequest;
import com.primeholding.rushhour.security.model.SignUpRequest;
import com.primeholding.rushhour.service.RoleService;
import com.primeholding.rushhour.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private RoleService roleService;
    private JwtTokenProvider tokenProvider;
    private ModelMapper modelMapper;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService,
                          RoleService roleService, JwtTokenProvider tokenProvider,
                          ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.tokenProvider = tokenProvider;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        User user = modelMapper.map(signUpRequest, User.class);
        Role userRole = roleService.get(Constants.ROLE_USER).orElseThrow(() -> new AppException("User Role not set."));
        user.setRole(userRole);

        Optional<User> createdUser = userService.create(user);

        if (!createdUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok(modelMapper.map(createdUser.get(), UserGetModel.class));
    }
}