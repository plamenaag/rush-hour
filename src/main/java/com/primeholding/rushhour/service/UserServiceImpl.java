package com.primeholding.rushhour.service;

import com.primeholding.rushhour.entity.User;
import com.primeholding.rushhour.repository.RoleRepository;
import com.primeholding.rushhour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> get(Integer userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> create(User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            return Optional.empty();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.ofNullable(userRepository.save(user));
    }

    @Override
    public Optional<User> update(User user) {
        Optional<User> userToUpdate = get(user.getId());

        if (userToUpdate.isPresent()) {
            userToUpdate.get().setFirstName(user.getFirstName());
            userToUpdate.get().setLastName(user.getLastName());
            userToUpdate.get().setRole(user.getRole());

            if (user.getPassword() != null) {
                userToUpdate.get().setPassword(passwordEncoder.encode(user.getPassword()));
            }

            return Optional.ofNullable(userRepository.save(userToUpdate.get()));
        }

        return Optional.empty();
    }

    @Override
    public void delete(Integer userId) {
        Optional<User> user = get(userId);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        }
    }
}
