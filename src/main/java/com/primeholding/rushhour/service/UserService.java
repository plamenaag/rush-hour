package com.primeholding.rushhour.service;

import com.primeholding.rushhour.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();

    Optional<User> get(Integer userId);

    Optional<User> create(User user);

    Optional<User> update(User user);

    void delete(Integer userId);
}
