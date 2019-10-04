package com.primeholding.rushhour.controller;

import com.primeholding.rushhour.entity.Role;
import com.primeholding.rushhour.entity.User;
import com.primeholding.rushhour.model.UserGetModel;
import com.primeholding.rushhour.model.UserPostModel;
import com.primeholding.rushhour.security.exception.AppException;
import com.primeholding.rushhour.service.RoleService;
import com.primeholding.rushhour.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    private RoleService roleService;
    private ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, RoleService roleService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    public HttpEntity get(@PathVariable("id") Integer id) {
        Optional<User> user = userService.get(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(modelMapper.map(user.get(), UserGetModel.class));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public HttpEntity getAll() {
        List<User> users = userService.getAll();
        List<UserGetModel> userGetModels = new ArrayList<>();
        for (User user : users) {
            userGetModels.add(modelMapper.map(user, UserGetModel.class));
        }

        return ResponseEntity.ok(userGetModels);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public HttpEntity create(@RequestBody UserPostModel userPostModel) {
        User user = modelMapper.map(userPostModel, User.class);
        user.setId(null);
        Role userRole = roleService.get(userPostModel.getRoleId()).orElseThrow(() -> new AppException("User Role not set."));
        user.setRole(userRole);
        Optional<User> savedUser = userService.create(user);
        if (savedUser.isPresent()) {
            return ResponseEntity.ok(modelMapper.map(savedUser.get(), UserGetModel.class));
        }

        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    @PutMapping("/{id}")
    public HttpEntity update(@PathVariable(required = true) Integer id, @RequestBody UserPostModel userPostModel) {
        Role userRole = roleService.get(userPostModel.getRoleId()).orElseThrow(() -> new AppException("User Role not set."));
        User user = modelMapper.map(userPostModel, User.class);
        user.setId(id);
        user.setRole(userRole);
        Optional<User> updatedUser = userService.update(user);
        if (updatedUser.isPresent()) {
            return ResponseEntity.ok(modelMapper.map(updatedUser.get(), UserGetModel.class));
        }

        return ResponseEntity.badRequest().build();
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public HttpEntity delete(@PathVariable(required = true) Integer id) {
        if (userService.get(id).isPresent()) {
            userService.delete(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
