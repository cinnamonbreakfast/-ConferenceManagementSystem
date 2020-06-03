package com.controller;


import com.session.SessionKeeper;
import dto.PaperDTO;
import model.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import service.PaperService;

@RestController
public class PaperController {
    private final PaperService paperService;

    private final SessionKeeper sessionKeeper;

    @Autowired
    public PaperController(PaperService paperService, SessionKeeper sessionKeeper)
    {
        this.paperService = paperService;
        this.sessionKeeper = sessionKeeper;
    }

    @RequestMapping(value = "/paper/test", method = RequestMethod.POST)
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

    @RequestMapping(value = "/paper", method = RequestMethod.PUT)
    String addPaper(@RequestBody PaperDTO paperDTO)
    {
        Paper paper = new Paper();
        paper.setName(paperDTO.getName());
        paper.setAbstractURL(paperDTO.getAbstractURL());
        /*paper.setKeywords(paperDTO.getKeywords());
        paper.setTopic(paperDTO.getTopic());*/
       // paper.setOtherAuthors(paperDTO.getOtherAuthors());
        System.out.println(paper);

        paperService.add(paper);
        return "OK";
    }

    @RequestMapping(value = "/paper/{id}", method = RequestMethod.DELETE)
    String deletePaper(@PathVariable Long id)
    {
        if(paperService.delete(id))
            return "ok";
        else return "not ok";
    }
}
