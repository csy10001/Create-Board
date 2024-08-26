package com.example.board.controller;

import com.example.board.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    public ResponseEntity<Void> addLike(@PathVariable Long postId) {
        likeService.addLike(postId);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeLike(@PathVariable Long id) {
        likeService.removeLike(id);
        return ResponseEntity.noContent().build();
    }
}

