package com.orderfood.DTO;

import com.orderfood.model.User;

public class UserEditDTO {

    private Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String email;

    private String phoneNumber;
    private String address;

    public UserEditDTO() {

    }
    public UserEditDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.lastName = user.getLastName();
        this.firstName = user.getFirstName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress();
    }

}
