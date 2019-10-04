package com.primeholding.rushhour.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role() {

    }

    public Role(Integer id, String name, List<User> users) {
        this.setId(id);
        this.setName(name);
        this.setUsers(users);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<>();
        }

        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}