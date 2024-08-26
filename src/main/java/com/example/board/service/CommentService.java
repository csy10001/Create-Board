package com.example.board.service;

import com.example.board.model.Comment;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Comment findCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
    }

    @Transactional
    public Comment createComment(Long postId, Comment newComment) {
        return postRepository.findById(postId)
                .map(post -> {
                    newComment.setPost(post);
                    return commentRepository.save(newComment);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
    }

    @Transactional
    public Comment updateComment(Long id, Comment updatedComment) {
        return commentRepository.findById(id)
                .map(comment -> {
                    comment.setContent(updatedComment.getContent());
                    return commentRepository.save(comment);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
    }

    @Transactional
    public void deleteComment(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Comment not found with id: " + id);
        }
    }
}

