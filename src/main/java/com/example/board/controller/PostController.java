package com.example.board.controller;

import com.example.board.model.Post;
import com.example.board.service.PostService;
import com.example.board.service.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // 모든 게시물 조회
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.findAllPosts();
    }

    // 게시물 ID로 조회
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return postService.findPostById(id)
                .map(post -> ResponseEntity.ok(post))
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
    }

    // 게시물 생성
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post newPost) {
        Post createdPost = postService.createPost(newPost);
        return ResponseEntity.status(201).body(createdPost);
    }

    // 게시물 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        try {
            Post updatedPost = postService.updatePost(id, post);
            return ResponseEntity.ok(updatedPost);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (postService.deletePost(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
