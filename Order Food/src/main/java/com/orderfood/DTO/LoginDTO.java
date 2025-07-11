package com.orderfood.DTO;

import lombok.Data;

@Data
public class LoginDTO {

    private String token;

    private String message;

    public LoginDTO(String token, String message) {

        this.token = token;
        this.message = message;

    }

    public LoginDTO() {

    }
}
