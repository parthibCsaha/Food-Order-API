package com.orderfood.service;

import com.orderfood.DTO.LoginDTO;
import com.orderfood.DTO.PasswordDTO;
import com.orderfood.DTO.UserDTO;
import com.orderfood.model.Login;
import com.orderfood.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<UserDTO> findAllUsers();
    List<UserDTO> findAllEmployees();

    User findOne(Long id);
    User findByUsername(String username);
    User delete(User user);
    User getCurrentUser();
    String validateUser(User user);
    String validateUserUpdate(UserDTO user);

    String validateEmployeeUpdate(UserDTO user);

    String validateEmployeeUpdate(User user);

    String updateUser(UserDTO userDTO);
    String updateEmployee(User user);
    LoginDTO generateToken(Login login);
    String isValidLogout();
    String deactivateUser(Long id);
    String saveUser(User user);
    String saveEmployee(User user);
    void setCurrentUser(User user);
    String changePassword(PasswordDTO passwordDTO);

}
