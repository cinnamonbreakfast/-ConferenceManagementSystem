package com.controller;
import com.session.SessionKeeper;
import converter.ConferenceConverter;
import converter.PaperConverter;
import converter.UserConverter;
import dto.*;
import model.Conference;
import model.User;
import model.util.Assigns;
import model.util.Paper;
import model.util.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ConferenceController {

    private final ConferenceService conferenceService;
    private final UserService userService;
    private final PermissionService permissionService;
    private final PaperService paperService;
    private final AssignService assignService;

    private final SessionKeeper sessionKeeper;


    private final ConferenceConverter conferenceConverter;
    private final UserConverter userConverter;
    private final PaperConverter paperConverter;

    @Autowired
    public ConferenceController(ConferenceService service, UserService userService, SessionKeeper sessionKeeper, ConferenceConverter conferenceConverter, UserConverter userConverter, PermissionService permissionService, PaperService paperService, AssignService assignService, PaperConverter paperConverter) {
        this.conferenceService = service;
        this.userService = userService;
        this.sessionKeeper = sessionKeeper;
        this.conferenceConverter = conferenceConverter;
        this.userConverter = userConverter;
        this.permissionService = permissionService;
        this.paperService = paperService;
        this.assignService = assignService;
        this.paperConverter = paperConverter;
    }

    @RequestMapping(value ="/conference/get", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody HttpEntity<ConferencesDTO> test(@RequestBody @Validated ConferencesDTO probe, @RequestHeader(value = "SESSION") String token) {
        if(sessionKeeper.sessionExists(token)) {
            String username = sessionKeeper.getUsername(token);

            ConferencesDTO response = new ConferencesDTO();
            List<Conference> conferences = conferenceService.getForUsername(username);
            response.setSize(conferences.size());

            response.setConferences(conferenceConverter.convertModelsToDtos(conferences));

            response.getConferences().forEach(e -> {
                e.setChair(userConverter.convertModelToDto(conferenceService.getChair(e.getId())));
            });

            return new HttpEntity<>(response);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/papers", method = RequestMethod.POST)
    ResponseEntity<String> fileUpload(@RequestHeader(value = "SESSION") String token, @RequestBody PaperDTO paper) {
        String username = sessionKeeper.getUsername(token);
        User response = userService.getByUsername(username);
        Conference conference = conferenceService.getById(paper.getConferenceDTO().getId());

        Paper paperus = Paper.builder()
                .title(paper.getTitle())
                .keywords(paper.getKeywords())
                .topics(paper.getTopics())
                .user(userConverter.convertDtoToModel(paper.getUser()))
                .conference(conferenceConverter.convertDtoToModel(paper.getConferenceDTO()))
                .build();

        paperService.save(paperus);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @RequestMapping(value = "/papers/get", method = RequestMethod.GET)
    PapersDTO getMyPapers(@RequestHeader(value = "SESSION") String token, @RequestParam Long id) {
        String username = sessionKeeper.getUsername(token);
        User response = userService.getByUsername(username);

        List<PaperDTO> papers = paperService.getByUandC(username, id).stream().map(e -> {
            PaperDTO p = PaperDTO.builder()
                    .title(e.getTitle())
                    .topics(e.getTopics())
                    .keywords(e.getKeywords())
                    .user(userConverter.convertModelToDto(e.getUser()))
                    .conferenceDTO(conferenceConverter.convertModelToDto(e.getConference()))
                    .build();
            p.setId(e.getId());

            return p;
        }).collect(Collectors.toList());

        List<PaperDTO> papersR = paperService.rAssigned(username, id).stream().map(e -> {
            PaperDTO p = PaperDTO.builder()
                    .title(e.getTitle())
                    .topics(e.getTopics())
                    .keywords(e.getKeywords())
                    .user(userConverter.convertModelToDto(e.getUser()))
                    .conferenceDTO(conferenceConverter.convertModelToDto(e.getConference()))
                    .build();
            p.setId(e.getId());

            return p;
        }).collect(Collectors.toList());

        System.out.println(papersR);

        PapersDTO pdd = new PapersDTO();
        pdd.setPapers(papers);

        return pdd;
    }

    @RequestMapping(value = "/reviewer/assigned", method = RequestMethod.GET)
    PapersDTO getRAssigned(@RequestHeader(value = "SESSION") String token, @RequestParam Long id) {
        String username = sessionKeeper.getUsername(token);

        List<PaperDTO> papers = paperService.rAssigned(username, id).stream().map(e -> {
            PaperDTO p = PaperDTO.builder()
                    .title(e.getTitle())
                    .topics(e.getTopics())
                    .keywords(e.getKeywords())
                    .user(userConverter.convertModelToDto(e.getUser()))
                    .conferenceDTO(conferenceConverter.convertModelToDto(e.getConference()))
                    .build();
            p.setId(e.getId());

            return p;
        }).collect(Collectors.toList());

        PapersDTO pdd = new PapersDTO();
        pdd.setPapers(papers);

        return pdd;
    }

    @RequestMapping(value = "/bidding", method = RequestMethod.PUT)
    ResponseEntity<AssignDTO> bidding(Long assignId, Long conferenceId, Boolean bid, @RequestHeader(value = "SESSION") String token){
        if(sessionKeeper.sessionExists(token)){
            String username = sessionKeeper.getUsername(token);
            Permission permission = permissionService.getPermission(username, conferenceId);
            if (permission.getChair()||permission.getCoChair()){
                Assigns assigns = assignService.findById(assignId);
                if(assigns.getPaper().getConference().getBiddingDeadline().isAfter(LocalDateTime.now())) {
                    return new ResponseEntity<>(
                            null,
                            HttpStatus.I_AM_A_TEAPOT
                    );

                }
                assigns.setDeclined(bid);
                assignService.update(assigns);
                AssignDTO assignDTO = AssignDTO.builder()
                        .paper(paperConverter.convertModelToDto(assigns.getPaper()))
                        .reviewer(userConverter.convertModelToDto(assigns.getUser()))
                        .declined(assigns.getDeclined())
                        .review(assigns.getReview())
                        .rating(assigns.getRating())
                        .build();
                assignDTO.setId(assigns.getId());
                return new ResponseEntity<>(
                        assignDTO,
                        HttpStatus.OK
                );

            }
            return new ResponseEntity<>(
                    null,
                    HttpStatus.FORBIDDEN
            );
        }
        return new ResponseEntity<>(
                null,
                HttpStatus.FORBIDDEN
        );
    }

    @RequestMapping(value = "/review", method = RequestMethod.PUT)
    ResponseEntity<AssignDTO> updateAssign(Long assignId, Long conferenceId, String review, String rating, @RequestHeader(value = "SESSION") String token){
        if(sessionKeeper.sessionExists(token)){
            String username = sessionKeeper.getUsername(token);
            Permission permission = permissionService.getPermission(username, conferenceId);
            if (permission.getCoChair()||permission.getChair()){
                Assigns assigns = assignService.findById(assignId);
                assigns.setRating(rating);
                assigns.setReview(review);
                assignService.update(assigns);
                AssignDTO assignDTO = AssignDTO.builder()
                        .paper(paperConverter.convertModelToDto(assigns.getPaper()))
                        .reviewer(userConverter.convertModelToDto(assigns.getUser()))
                        .declined(assigns.getDeclined())
                        .review(assigns.getReview())
                        .rating(assigns.getRating())
                        .build();
                assignDTO.setId(assigns.getId());
                return new ResponseEntity<>(
                        assignDTO,
                        HttpStatus.OK
                );
            }
            return new ResponseEntity<>(
                    null,
                    HttpStatus.FORBIDDEN
            );
        }
        return new ResponseEntity<>(
                null,
                HttpStatus.FORBIDDEN
        );
    }

    @RequestMapping(value = "/review/assign", method = RequestMethod.POST)
    ResponseEntity<AssignDTO> assignPaper(String reviewer, Long paperId, Long conferenceId, @RequestHeader(value = "SESSION") String token){
        if(sessionKeeper.sessionExists(token)){
            String username = sessionKeeper.getUsername(token);
            Permission permission = permissionService.getPermission(username,conferenceId);
            if(permission.getChair()||permission.getCoChair())
            {
                Conference conference = conferenceService.getById(conferenceId);
                User rev = userService.getByUsername(reviewer);
                Paper paper = paperService.getById(paperId);
                Assigns assigns = Assigns.builder()
                        .paper(paper)
                        .user(rev)
                        .build();
                System.out.println(assigns);
                Assigns assigns2 = assignService.save(assigns);
                AssignDTO assignDTO = AssignDTO.builder()
                        .paper(paperConverter.convertModelToDto(paper))
                        .reviewer(userConverter.convertModelToDto(rev))
                        .build();
                assignDTO.setId(assigns2.getId());
                return new ResponseEntity<>(
                        assignDTO,
                        HttpStatus.OK
                );

            }
            return new ResponseEntity<>(
                    HttpStatus.FORBIDDEN
            );
        }
        return new ResponseEntity<>(
                HttpStatus.FORBIDDEN
        );

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
