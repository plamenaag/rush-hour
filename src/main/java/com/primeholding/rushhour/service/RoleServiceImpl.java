package com.primeholding.rushhour.service;

import com.primeholding.rushhour.entity.Role;
import com.primeholding.rushhour.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> get(String name) {
        Optional<Role> role = roleRepository.findByName(name);
        return role;
    }

    @Override
    public Optional<Role> get(Integer id) {
        Optional<Role> role = roleRepository.findById(id);
        return role;
    }
}
