package com.primeholding.rushhour.model;

public class UserGetModel {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private RoleGetModel role;

    public UserGetModel() {

    }

    public UserGetModel(Integer id, String firstName, String lastName, String email, String password, RoleGetModel role) {
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setRole(role);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleGetModel getRole() {
        return role;
    }

    public void setRole(RoleGetModel role) {
        this.role = role;
    }
}

