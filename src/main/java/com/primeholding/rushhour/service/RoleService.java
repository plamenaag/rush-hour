package com.primeholding.rushhour.service;

import com.primeholding.rushhour.entity.Role;
import java.util.Optional;

public interface RoleService {
    Optional<Role> get(Integer id);

    Optional<Role> get(String name);
}
