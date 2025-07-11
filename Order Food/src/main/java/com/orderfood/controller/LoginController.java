package com.orderfood.controller;

import com.orderfood.DTO.LoginDTO;
import com.orderfood.model.Login;
import com.orderfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginDTO> generateToken(@RequestBody Login login){
        LoginDTO loginDTO = userService.generateToken(login);
        return new ResponseEntity<>(loginDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<String> logout(){
        String responseClient = userService.isValidLogout();
        return new ResponseEntity<>(responseClient, HttpStatus.OK);
    }

}
