package com.controller;

import com.session.SessionKeeper;
import converter.ConferenceConverter;
import converter.UserConverter;
import dto.ConferenceDTO;
import dto.ConferencesDTO;
import model.Conference;
import model.User;
import model.util.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.ConferenceService;
import service.PermissionService;
import service.UserService;

import java.util.List;

@RestController
public class ConferenceController {

    private final ConferenceService conferenceService;

    private final UserService userService;

    private final SessionKeeper sessionKeeper;

    private final ConferenceConverter conferenceConverter;

    private final UserConverter userConverter;

    private final PermissionService permissionService;

    @Autowired
    public ConferenceController(ConferenceService service, UserService userService, SessionKeeper sessionKeeper, ConferenceConverter conferenceConverter, UserConverter userConverter, PermissionService permissionService) {
        this.conferenceService = service;
        this.userService = userService;
        this.sessionKeeper = sessionKeeper;
        this.conferenceConverter = conferenceConverter;
        this.userConverter = userConverter;
        this.permissionService = permissionService;
    }

    @RequestMapping(value ="/conference/get", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody HttpEntity<ConferencesDTO> test(@RequestBody @Validated ConferencesDTO probe, @RequestHeader(value = "SESSION") String token) {
        if(sessionKeeper.sessionExists(token)) {
            String username = sessionKeeper.getUsername(token);

            ConferencesDTO response = new ConferencesDTO();
            List<Conference> conferences = conferenceService.getForUsername(username);
            response.setSize(conferences.size());

            System.out.println(conferences.size());

            response.setConferences(conferenceConverter.convertModelsToDtos(conferences));

            response.getConferences().forEach(e -> {
                e.setChair(userConverter.convertModelToDto(conferenceService.getChair(e.getId())));
            });

            return new HttpEntity<>(response);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value ="/conference", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody ResponseEntity<String> addConference(@RequestBody @Validated ConferenceDTO target, @RequestHeader(value = "SESSION") String token) {
        if(sessionKeeper.sessionExists(token)) {
            String username = sessionKeeper.getUsername(token);

            Conference conference = conferenceConverter.convertDtoToModel(target);

            User chair = userService.getByUsername(username);

            if(conferenceService.add(conference) != null)
            {
                Permission chairLink = new Permission();
                chairLink.setConference(conference);
                chairLink.setUser(chair);
                chairLink.setChair(true);

                permissionService.setPermission(chairLink);

                if(target.getAuthors() != null)
                {
                    target.getAuthors().forEach(e -> {
                        User author = userService.getByUsername(e);

                        Permission authorPermission = new Permission();
                        authorPermission.setConference(conference);
                        authorPermission.setUser(author);
                        authorPermission.setAuthor(true);

                        permissionService.setPermission(authorPermission);
                    });
                } else {
                    System.out.println("No authors");
                }

                if(target.getReviewers() != null)
                {
                    target.getReviewers().forEach(e -> {
                        User author = userService.getByUsername(e);

                        Permission reviewerPermission = new Permission();
                        reviewerPermission.setConference(conference);
                        reviewerPermission.setUser(author);
                        reviewerPermission.setReviewer(true);

                        permissionService.setPermission(reviewerPermission);
                    });
                } else {
                    System.out.println("No reviewers");
                }

                if(target.getCochairs() != null)
                {
                    target.getCochairs().forEach(e -> {
                        User author = userService.getByUsername(e);

                        Permission cochairPermission = new Permission();
                        cochairPermission.setConference(conference);
                        cochairPermission.setUser(author);
                        cochairPermission.setCoChair(true);

                        permissionService.setPermission(cochairPermission);
                    });
                } else {
                    System.out.println("No co-chairs");
                }
                System.out.println("DONE SETTING PERMISSIONS");
            } else {
                ResponseEntity<String> response = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
                return response;
            }

            return new ResponseEntity<String>(HttpStatus.OK);
        }

        return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
    }
}
