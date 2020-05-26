package com.controller;

import com.session.SessionKeeper;
import dto.ConferenceDTO;
import dto.LoginCredentials;
import dto.UserDTO;
import dto.UserRegisterDTO;
import javafx.util.Pair;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import javax.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
public class UserController {
    public static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SessionKeeper sessionKeeper;

    @Autowired
    private SessionController sessionController;

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

            UserDTO userDTO = new UserDTO();
            userDTO.setId(resultUser.getId());
            userDTO.setUsername(resultUser.getUsername());
            userDTO.setFirstName(resultUser.getFirstName());
            userDTO.setLastName(resultUser.getLastName());
            userDTO.setEmail(resultUser.getEmail());

            if(session != null)
            {
                userDTO.setToken(session.getKey());
                userDTO.setLoginTime(session.getValue());
            }

            List<ConferenceDTO> conferenceDTOList = new ArrayList<>();

            resultUser.getConferences().forEach(e -> {
                ConferenceDTO conferenceDTO = new ConferenceDTO();
                conferenceDTO.setChair(null);
                conferenceDTO.setTitle(e.getTitle());
                conferenceDTO.setDescription(e.getDescription());
                conferenceDTO.setPhase(e.getPhase());

                conferenceDTOList.add(conferenceDTO);
            });

            userDTO.setConferences(conferenceDTOList);


            return userDTO;
        }

        return null;
    }

    // use this as a template for your controllers
    // check the login with request parameter, by looking for an active session
    @RequestMapping(value = "/testAccessPage", method = RequestMethod.POST)
    String test(HttpEntity<String> request) {


        if(request.getHeaders().get("SESSION") != null)
        {
            String token = request.getHeaders().get("SESSION").get(0);

            if(sessionKeeper.sessionExists(token))
            {
                return "ok";
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
