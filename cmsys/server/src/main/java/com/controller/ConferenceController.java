package com.controller;

import com.session.SessionKeeper;
import dto.ConferenceDTO;
import dto.ConferencesDTO;
import model.Conference;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.ConferenceService;
import service.UserService;

import java.awt.print.Pageable;
import java.util.Map;

@RestController
public class ConferenceController {

    private final ConferenceService conferenceService;

    private final UserService userService;

    private final SessionKeeper sessionKeeper;

    @Autowired
    public ConferenceController(ConferenceService service, UserService userService, SessionKeeper sessionKeeper) {
        this.conferenceService = service;
        this.userService = userService;
        this.sessionKeeper = sessionKeeper;
    }

    @RequestMapping(value ="/conference/get", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody ConferencesDTO test(@RequestBody @Validated ConferencesDTO probe, @RequestHeader(value = "SESSION") String token) {
        if(sessionKeeper.sessionExists(token))
        {
            String username = sessionKeeper.getUsername(token);

            ConferencesDTO response = new ConferencesDTO();
            response.setSize(0);

            return response;
        } else {
            System.out.println("Non existent token, " + token);
            return null;
        }
    }

    @RequestMapping(value = "/conference/test", method = RequestMethod.PUT)
    String addConference(@RequestBody ConferenceDTO conferenceDTO) {
        User chair = new User();
        chair.setEmail("candetandrei@gmail.com");
        chair.setFirstName("Candet");
        chair.setLastName("Andrei");
        chair.setPassword("12345678");
        chair.setUsername("andrewcandet");
        chair.setId(6L);

        Conference conference = new Conference();
        conference.setChair(chair);
        conference.setPhase(conferenceDTO.getPhase());
        conference.setTitle(conferenceDTO.getTitle());
        conference.setDescription(conferenceDTO.getDescription());

        System.out.println(conference);

        conferenceService.add(conference);

        return "OK";
    }
}
