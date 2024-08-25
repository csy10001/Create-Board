package com.example.board.service;

import com.example.board.model.Post;
import com.example.board.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    // 모든 게시물 조회
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    // 게시물 ID로 조회
    public Optional<Post> findPostById(Integer post_id) {
        return postRepository.findById(post_id);
    }

    // 게시물 생성
    public Post createPost(Post newPost) {
        return postRepository.save(newPost);
    }

    // 게시물 업데이트
    public Post updatePost(Integer post_id, Post updatedPost) {
        return postRepository.findById(post_id)
                .map(post -> {
                    post.setTitle(updatedPost.getTitle());
                    post.setContent(updatedPost.getContent());
                    post.setAuthorEmail(updatedPost.getAuthorEmail());
                    return postRepository.save(post);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + post_id));
    }

    // 게시물 삭제
    public boolean deletePost(Integer post_id) {
        if (postRepository.existsById(post_id)) {
            postRepository.deleteById(post_id);
            return true;
        }
        return false;
    }
}
