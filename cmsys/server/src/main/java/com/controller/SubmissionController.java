package com.controller;

import com.session.SessionKeeper;
import converter.SubmissionConverter;
import dto.PaperDTO;
import dto.SubmissionDTO;
import model.Paper;
import model.Submission;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import service.SubmissionService;
import service.UserService;

import java.util.*;

@RestController
public class SubmissionController {
    private final SubmissionService submissionService;
    private final SessionKeeper sessionKeeper;
    private final UserService userService;
    private final SubmissionConverter submissionConverter;

    @Autowired
    public SubmissionController(SubmissionService submissionService, SessionKeeper sessionKeeper, UserService userService, SubmissionConverter submissionConverter)
    {
        this.submissionService = submissionService;
        this.sessionKeeper = sessionKeeper;
        this.userService = userService;
        this.submissionConverter = submissionConverter;
    }

    @RequestMapping(value = "/submission/test", method = RequestMethod.POST)
    String test(HttpEntity<String> request)
    {
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

    @RequestMapping(value = "/submission", method = RequestMethod.PUT)
    String addSubmission(@RequestBody SubmissionDTO submissionDTO, HttpEntity<String> request)
    {
        String token = request.getHeaders().get("SESSION").get(0);
        User author = userService.findByUsername(sessionKeeper.getUsername(token));

        /*Paper paper = new Paper();
        paper.setName("paper");
        paper.setAbstractURL("un_url");
        List<String> topic = new ArrayList<>();
        topic.add("un_topic");
        paper.setTopic(topic);
        List<String> keywords = new ArrayList<>();
        keywords.add("un_keyword");
        paper.setKeywords(keywords);
        paper.setOtherAuthors(null);*/

        //Section section = new Section();

        Submission submission = new Submission();
        submission.setAuthor(author);
        submission.setPaper(submissionConverter.convertDtoToModel(submissionDTO).getPaper());
        //submission.setSection(section);

        System.out.println(submission);

        submissionService.add(submission);

        return "OK";
    }

    @RequestMapping(value = "/submission/{id}", method = RequestMethod.DELETE)
    String deleteSubmission(@PathVariable Long id)
    {
        if(submissionService.delete(id))
            return "ok";
        else return "not ok";
    }
}
