package com.orderfood.DTO;

import com.orderfood.model.Role;
import com.orderfood.model.User;

public class UserDTO {

    private Long id;


    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Role role;
    private String phoneNumber;
    private String address;
    private boolean deleted;
    private String password;

    public UserDTO() {

    }
    public UserDTO(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getFirstName(), user.getPassword(), user.getRole(), user.getAddress(), user.getPhoneNumber(), user.isDeleted());
    }
    public UserDTO(Long id, String firstName,
                   String lastName, String email, String username, String password, Role role, String address, String phoneNumber, boolean deleted) {
        this.id = id;

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.deleted = deleted;

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
