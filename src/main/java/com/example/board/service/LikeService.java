package com.example.board.service;

import com.example.board.model.Like;
import com.example.board.repository.LikeRepository;
import com.example.board.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public void addLike(Long postId) {
        postRepository.findById(postId)
                .map(post -> {
                    Like like = new Like();
                    like.setPost(post);
                    return likeRepository.save(like);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
    }

    @Transactional
    public void removeLike(Long likeId) {
        if (likeRepository.existsById(likeId)) {
            likeRepository.deleteById(likeId);
        } else {
            throw new ResourceNotFoundException("Like not found with id: " + likeId);
        }
    }
}

