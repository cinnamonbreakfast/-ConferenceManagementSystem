package com.controller;

import com.session.SessionKeeper;
import dto.ConferenceDTO;
import model.Conference;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.ConferenceService;
import service.UserService;

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

    @RequestMapping(value ="/conference/test", method = RequestMethod.POST)
    String test(HttpEntity<String> request) {
        if(request.getHeaders().get("SESSION") != null)
        {
            String token = request.getHeaders().get("SESSION").get(0);

            if(sessionKeeper.sessionExists(token))
            {
                return "ok" + sessionKeeper.getUsername(token);
            } else {
                return "not_logged_in";
            }
        } else {
            return "not_logged_in";
        }
    }

    @RequestMapping(value = "/conference", method = RequestMethod.PUT)
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
