package com.orderfood.serviceImpl;

import com.orderfood.DTO.JWTLogin;
import com.orderfood.DTO.LoginDTO;
import com.orderfood.DTO.PasswordDTO;
import com.orderfood.DTO.UserDTO;
import com.orderfood.model.Login;
import com.orderfood.model.Role;
import com.orderfood.model.User;
import com.orderfood.repository.UserRepository;
import com.orderfood.security.JwtUtil;
import com.orderfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            if (user.getRole().equals(Role.USER)) {
                userDTOs.add(new UserDTO(user));
            }
        }
        return userDTOs;
    }

    @Override
    public List<UserDTO> findAllEmployees() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            if (user.getRole().equals(Role.EMPLOYEE) && !user.isDeleted()) {
                userDTOs.add(new UserDTO(user));
            }
        }
        return userDTOs;
    }

    @Override
    public User findOne(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User delete(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Attempt to delete non-existing user.");
        }
        userRepository.delete(user);
        return user;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username);
    }

    @Override
    public String validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty() || user.getLastName() == null || user.getLastName().trim().isEmpty() || user.getFirstName() == null || user.getFirstName().trim().isEmpty() || user.getUsername() == null || user.getUsername().trim().isEmpty() || user.getPhoneNumber() == null || user.getAddress().trim().isEmpty() || user.getPassword().trim().isEmpty() || user.getPhoneNumber().trim().isEmpty() || !user.getEmail().matches("^(.+)@(.+)$")) {
            return "invalid";
        }
        if (!isEmailUnique(user.getEmail())) {
            return "emailNotUnique";
        }
        else if (findByUsername(user.getUsername()) != null) {
            return "usernameNotUnique";
        }
        return "valid";
    }

    private boolean isEmailUnique(String email) {
        List<User> allUsers = userRepository.findAll();
        for (User u : allUsers) {
            if (u.getEmail().equals(email))
                return false;
        }
        return true;
    }

    @Override
    public String validateUserUpdate(UserDTO user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty() || user.getLastName() == null
                || user.getLastName().trim().isEmpty() || user.getFirstName() == null
                || user.getFirstName().trim().isEmpty() || user.getUsername() == null
                || user.getUsername().trim().isEmpty() || user.getPhoneNumber() == null
                || user.getAddress().trim().isEmpty() || user.getAddress() == null
                || user.getPhoneNumber().trim().isEmpty() || !user.getEmail().matches("^(.+)@(.+)$")) {
            return "invalid";
        }
        if (isEmailUniqueUpdate(user)) {
            return "emailNotUnique";
        }
        else if (isUsernameUniqueUpdate(user)) {
            return "usernameNotUnique";
        }

        return "valid";

    }

    @Override
    public String validateEmployeeUpdate(UserDTO user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty() || user.getLastName() == null
                || user.getLastName().trim().isEmpty() || user.getFirstName() == null
                || user.getFirstName().trim().isEmpty() || user.getUsername() == null
                || user.getUsername().trim().isEmpty() || user.getPhoneNumber() == null
                || user.getAddress().trim().isEmpty() || user.getAddress() == null
                || user.getPassword().trim().isEmpty() || user.getPassword() == null
                || user.getPhoneNumber().trim().isEmpty() || !user.getEmail().matches("^(.+)@(.+)$")) {
            return "invalid";
        }
        if (!isEmailUniqueUpdate(user)) {
            return "emailNotUnique";
        }
        else if (!isUsernameUniqueUpdate(user)) {
            return "usernameNotUnique";
        }

        return "valid";
    }

    @Override
    public String validateEmployeeUpdate(User user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty() || user.getLastName() == null
                || user.getLastName().trim().isEmpty() || user.getFirstName() == null
                || user.getFirstName().trim().isEmpty() || user.getUsername() == null
                || user.getUsername().trim().isEmpty() || user.getPhoneNumber() == null
                || user.getAddress().trim().isEmpty() || user.getAddress() == null
                || user.getPassword().trim().isEmpty() || user.getPassword() == null
                || user.getPhoneNumber().trim().isEmpty() || !user.getEmail().matches("^(.+)@(.+)$")) {
            return "invalid";
        }
        if (isEmailUniqueUpdate(user)) {
            return "emailNotUnique";
        }
        else if (isUsernameUniqueUpdate(user)) {
            return "usernameNotUnique";
        }

        return "valid";
    }

    private boolean isUsernameUniqueUpdate(UserDTO user) {
        List<User> allUsers = userRepository.findAll();
        allUsers.remove(userRepository.findById(user.getId()).get());
        for (User u : allUsers) {
            if (u.getUsername().equals(user.getUsername())) {
                return false;
            }
        }
        return true;
    }
    private boolean isUsernameUniqueUpdate(User user) {
        List<User> allUsers = userRepository.findAll();
        allUsers.remove(userRepository.findById(user.getId()).get());
        for (User u : allUsers) {
            if (u.getUsername().equals(user.getUsername())) {
                return false;
            }
        }
        return true;
    }

    private boolean isEmailUniqueUpdate(UserDTO user) {
        List<User> allUsers = userRepository.findAll();
        allUsers.remove(userRepository.findById(user.getId()).get());
        for (User u : allUsers) {
            if (u.getEmail().equals(user.getEmail())) {
                return false;
            }
        }
        return true;
    }

    private boolean isEmailUniqueUpdate(User user) {
        List<User> allUsers = userRepository.findAll();
        allUsers.remove(userRepository.findById(user.getId()).get());
        for (User u : allUsers) {
            if (u.getEmail().equals(user.getEmail())) {
                return false;
            }
        }
        return true;
    }


    @Override
    public String updateUser(UserDTO u) {
        try {
            User user = userRepository.findById(u.getId()).get();
            user.setFirstName(u.getFirstName());
            user.setLastName(u.getLastName());
            user.setPhoneNumber(u.getPhoneNumber());
            user.setAddress(u.getAddress());
            user.setEmail(u.getEmail());
            userRepository.save(user);
        } catch (Exception e) {
            return "fail";
        }

        return "success";
    }

    @Override
    public String updateEmployee(User u) {
        try {
            User user = userRepository.findById(u.getId()).get();
            user.setFirstName(u.getFirstName());
            user.setLastName(u.getLastName());
            user.setPassword(u.getPassword());
            user.setPhoneNumber(u.getPhoneNumber());
            user.setAddress(u.getAddress());
            user.setEmail(u.getEmail());
            userRepository.save(user);
        } catch (Exception e) {
            return "fail";
        }

        return "success";
    }

    @Override
    public LoginDTO generateToken(Login login) {
        LoginDTO loginDTO = new LoginDTO();
        User user = new User();
        try {
            User userFromDB = findByUsername(login.getUsername());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if(!(userFromDB.getPassword().equals(login.getPassword()))) {
                if(!encoder.matches(login.getPassword(), userFromDB.getPassword())) {
                    throw new Exception();
                }
                else {
                    JWTLogin jwtDetails = new JWTLogin();
                    jwtDetails.setRole(userFromDB.getRole().toString());
                    jwtDetails.setUsername(userFromDB.getUsername());
                    String token = jwtUtil.generateToken(jwtDetails);
                    loginDTO = new LoginDTO(token, "success");
                }
            }
            else {
                JWTLogin jwtDetails = new JWTLogin();
                jwtDetails.setRole(userFromDB.getRole().toString());
                jwtDetails.setUsername(userFromDB.getUsername());
                String token = jwtUtil.generateToken(jwtDetails);
                loginDTO = new LoginDTO(token, "success");
            }
        } catch (Exception e) {
            loginDTO = new LoginDTO();
            loginDTO.setMessage("fail");
            return loginDTO;
        }
        if(user.isDeleted()) {
            loginDTO = new LoginDTO();
            loginDTO.setMessage("deactivatedUser");
            return loginDTO;
        }
        return loginDTO;
    }

    @Override
    public String isValidLogout() {
        String responseToClient;
        if (getCurrentUser() != null) {
            SecurityContextHolder.clearContext();
            responseToClient = "valid";
        } else {
            responseToClient = "invalid";
        }
        return responseToClient;
    }

    @Override
    public String deactivateUser(Long id) {
        try {
            User user = userRepository.findById(id).get();
            user.setDeleted(true);
            userRepository.save(user);
            return "success";
        }
        catch (Exception e) {
            return "fail";
        }
    }

    @Override
    public String saveUser(User user) {
        try {
            user.setRole(Role.USER);
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.save(user);
            return "success";
        }
        catch (Exception e) {
            return "fail";
        }
    }

    @Override
    public String saveEmployee(User user) {
        try {
            user.setRole(Role.EMPLOYEE);
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.save(user);
            return "success";
        }
        catch (Exception e) {
            return "fail";
        }
    }

    @Override
    public void setCurrentUser(User user) {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        Authentication authentication = new PreAuthenticatedAuthenticationToken(user.getId(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public String changePassword(PasswordDTO passwordDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User loggedUser = new User();
        try {
            loggedUser = getCurrentUser();
        } catch (Exception e) {
        }

        if(!(loggedUser.getPassword().equals(passwordDTO.getOldPassword()))) {
            if(!encoder.matches(passwordDTO.getOldPassword(), loggedUser.getPassword())) {
                return "fail";
            }
            else {
                loggedUser.setPassword(new BCryptPasswordEncoder().encode(passwordDTO.getNewPassword()));
                userRepository.save(loggedUser);
                return "success";
            }
        }
        else {
            loggedUser.setPassword(new BCryptPasswordEncoder().encode(passwordDTO.getNewPassword()));
            userRepository.save(loggedUser);
            return "success";
        }
    }

    
}
