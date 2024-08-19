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
    public Optional<Post> findPostById(Long id) {
        return postRepository.findById(id);
    }

    // 게시물 생성
    public Post createPost(Post newPost) {
        return postRepository.save(newPost);
    }

    // 게시물 업데이트
    public Post updatePost(Long id, Post updatedPost) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(updatedPost.getTitle());
                    post.setContent(updatedPost.getContent());
                    post.setAuthorEmail(updatedPost.getAuthorEmail());
                    return postRepository.save(post);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
    }

    // 게시물 삭제
    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
