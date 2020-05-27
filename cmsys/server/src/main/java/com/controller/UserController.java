package com.controller;

import com.session.SessionKeeper;
import converter.UserConverter;
import dto.LoginCredentials;
import dto.UserDTO;
import dto.UserRegisterDTO;
import javafx.util.Pair;
import model.User;
import model.util.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import service.PermissionService;
import service.UserService;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;


@RestController
public class UserController {
    public static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final SessionKeeper sessionKeeper;

    private final SessionController sessionController;

    private final PermissionService permissionService;

    private final UserConverter userConverter;

    @Autowired
    public UserController(UserService userService, SessionKeeper sessionKeeper, SessionController sessionController, PermissionService permissionService, UserConverter userConverter) {
        this.userService = userService;
        this.sessionKeeper = sessionKeeper;
        this.sessionController = sessionController;
        this.permissionService = permissionService;
        this.userConverter = userConverter;
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
    @RequestMapping(value = "/testAccessPage", method = RequestMethod.GET)
    String test(HttpEntity<Long> request, @RequestParam(required = true) Long conferenceID) {


        if(request.getHeaders().get("SESSION") != null)
        {
            String token = request.getHeaders().get("SESSION").get(0);

            if(sessionKeeper.sessionExists(token))
            {
                String username = sessionKeeper.getUsername(token);

                Permission permission = permissionService.getPermission(username, conferenceID);

                return "ok" + permission.getChair() + permission.getCoChair() + permission.getAuthor();
            } else {
                return "not_logged_in";
            }
        } else {
            return "not_logged_in";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    String register(@RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            userRegisterDTO.setPassword(sessionController.passwordEncode(userRegisterDTO.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User registeredUser = userService.registerNewUserAccount(userRegisterDTO);

        if(registeredUser == null)
        {
            return "ok";
        }

        return "error";
    }
}
