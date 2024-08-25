package com.example.board.service;

import com.example.board.model.Comment;
import com.example.board.model.Post;
import com.example.board.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment addComment(Comment newComment, Post post_id) {
        return commentRepository.save(newComment);
    }

}
