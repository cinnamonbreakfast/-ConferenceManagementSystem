package com.controller;

import com.session.SessionKeeper;
import converter.PermissionConverter;
import converter.UserConverter;
import dto.LoginCredentials;
import dto.PermissionDTO;
import dto.UserDTO;
import dto.UserRegisterDTO;
import javafx.util.Pair;
import model.User;
import model.util.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.PermissionService;
import service.UserService;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@SessionAttributes("token")
public class UserController {
    public static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final SessionKeeper sessionKeeper;

    private final SessionController sessionController;

    private final PermissionService permissionService;

    private final UserConverter userConverter;
    private final PermissionConverter permissionConverter;

    @Autowired
    public UserController(UserService userService, SessionKeeper sessionKeeper, SessionController sessionController, PermissionService permissionService, UserConverter userConverter, PermissionConverter permissionConverter) {
        this.userService = userService;
        this.sessionKeeper = sessionKeeper;
        this.sessionController = sessionController;
        this.permissionService = permissionService;
        this.userConverter = userConverter;
        this.permissionConverter = permissionConverter;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    ResponseEntity<HttpStatus> logout(@RequestHeader(value = "SESSION") String token)
    {
        if(sessionKeeper.sessionExists(token))
        {
            sessionKeeper.clear(token);

            ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.OK);
            return responseEntity;
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/matchusername", method = RequestMethod.POST)
    ResponseEntity<List<String>> logout(@RequestHeader(value = "SESSION") String token, @RequestBody String username)
    {
        if(sessionKeeper.sessionExists(token))
        {
            List<String> suggestions = userService.getFirstTen(username);

            ResponseEntity<List<String>> responseEntity = new ResponseEntity<>(suggestions, HttpStatus.OK);
            return responseEntity;
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    UserDTO login(@RequestBody LoginCredentials loginCredentials) {

        try {
            loginCredentials.setPassword(sessionController.passwordEncode(loginCredentials.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User resultUser = userService.loginAccount(loginCredentials);

        if(resultUser != null)
        {
            Pair<String, LocalDateTime> session = sessionKeeper.makeSession(loginCredentials.getUsername());

            if(session != null)
            {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(resultUser.getId());
                userDTO.setUsername(resultUser.getUsername());
                userDTO.setFirstName(resultUser.getFirstName());
                userDTO.setLastName(resultUser.getLastName());
                userDTO.setEmail(resultUser.getEmail());


                userDTO.setToken(session.getKey());
                userDTO.setLoginTime(session.getValue());

                return userDTO;
            }

            return null;
        }

        return null;
    }

    // use this as a template for your controllers
    // check the login with request parameter, by looking for an active session
    @RequestMapping(value = "/get/u/{username}", method = RequestMethod.GET)
    UserDTO test(@PathVariable String username) {
        User response = userService.getByUsername(username);
        if(response != null)
        {
            return userConverter.convertModelToDto(response);
        }
        return null;
    }

    @RequestMapping(value = "/user/permissions", method = RequestMethod.GET)
    ResponseEntity<PermissionDTO> getPermissions(@RequestParam Long conferenceID, @RequestHeader(value = "SESSION") String token)
    {
        if(sessionKeeper.sessionExists(token))
        {
            String username = sessionKeeper.getUsername(token);
//            User user = userService.getByUsername(username);

            Permission permission = permissionService.findByUsernameAndConference(username, conferenceID);

            if(permission != null)
            {
                return new ResponseEntity<>(permissionConverter.convertModelToDto(permission), HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    String register(@RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            userRegisterDTO.setPassword(sessionController.passwordEncode(userRegisterDTO.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User registeredUser = null;

        try {
            registeredUser = userService.registerNewUserAccount(userRegisterDTO);
        } catch (Exception e) {
            return e.getMessage();
        }

        if(registeredUser != null)
        {
            return "Account successfully registered! You can sign in now.";
        }

        return "An error occurred. Try again later.";
    }
}
