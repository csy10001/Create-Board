package com.example.board.controller;

import com.example.board.model.Comment;
import com.example.board.model.Post;
import com.example.board.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //댓글입력
    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Comment newComment, Post post_id) {
        Comment addComment = commentService.addComment(newComment, post_id);
        return ResponseEntity.status(201).body(addComment);
    }

}
