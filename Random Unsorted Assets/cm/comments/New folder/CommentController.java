package com.controller;

import com.session.SessionKeeper;
import dto.CommentDTO;
import model.Comment;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.CommentService;
import service.PermissionService;
import service.UserService;

@RestController
public class CommentController {
    private final CommentService commentService;

    private final UserService userService;

    private SessionKeeper sessionKeeper;

    private PermissionService permissionService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService, SessionKeeper sessionKeeper, PermissionService permissionService) {
        this.commentService = commentService;
        this.userService = userService;
        this.sessionKeeper = sessionKeeper;
        this.permissionService = permissionService;
    }

    @RequestMapping(value="/comment",method= RequestMethod.PUT)
    String addComment(@RequestBody CommentDTO commentDTO){
        User reviewer=userService;

        Comment comment=new Comment();
        comment.setAuthor(reviewer);
        comment.setText(commentDTO.getText());
        comment.setUpvotes(commentDTO.getUpvotes());
        comment.setPosttime(commentDTO.getPosttime());

        System.out.println(comment);
        commentService.addComment(comment);
        return "OK";

    }
}
