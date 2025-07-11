package com.orderfood.controller;

import com.orderfood.DTO.PasswordDTO;
import com.orderfood.DTO.UserDTO;
import com.orderfood.model.User;
import com.orderfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/ragistration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody User user) {
        String responseToClient = userService.validateUser(user);
        if (responseToClient.equals("valid")) {
            responseToClient = userService.saveUser(user);
            return new ResponseEntity<>(responseToClient, HttpStatus.OK);
        }
        else {
            responseToClient = "invalid";
            return new ResponseEntity<>(responseToClient, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/createEmployee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createEmployee(@RequestBody User user) {
        String responseToClient = userService.validateUser(user);
        if (responseToClient.equals("valid")) {
            responseToClient = userService.saveUser(user);
            return new ResponseEntity<>(responseToClient, HttpStatus.OK);
        }
        else {
            responseToClient = "invalid";
            return new ResponseEntity<>(responseToClient, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> usersDTO = userService.findAllUsers();
        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllEmployees")
    public ResponseEntity<List<UserDTO>> getAlEmployees() {
        List<UserDTO> usersDTO = userService.findAllEmployees();
        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/updateUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO) {
        String validationStatus = userService.validateUserUpdate(userDTO);
        if (!validationStatus.equals("valid")) {
            return new ResponseEntity<>(validationStatus, HttpStatus.OK);
        }
        String response = userService.updateUser(userDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/updateUserByIdAndDetails/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUserByIdAndDetails(@PathVariable Long id, @RequestBody User employeeDetails) {
        if (userService.findOne(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = userService.findOne(id);
        employeeDetails.setId(user.getId());
        employeeDetails.setRole(user.getRole());
        String validationStatus = userService.validateEmployeeUpdate(employeeDetails);
        if (!validationStatus.equals("valid")) {
            return new ResponseEntity<>(validationStatus, HttpStatus.OK);
        }
        String response = userService.updateEmployee(employeeDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getCurrentUser")
    public ResponseEntity<User> getCurrentUser() {
        User user = userService.getCurrentUser();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        if (userService.findOne(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = userService.findOne(id);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }
    @PutMapping(value = "/deactivateUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        if (userService.findOne(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String response = userService.deactivateUser(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changePassword(@RequestBody PasswordDTO passwordDTO) {
        String responseToClient = userService.changePassword(passwordDTO);
        return new ResponseEntity<>(responseToClient, HttpStatus.OK);
    }


}
