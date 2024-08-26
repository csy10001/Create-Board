package com.example.board.service;

import com.example.board.model.Comment;
import com.example.board.model.Post;
import com.example.board.repository.PostRepository;
import com.example.board.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

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
        // 댓글 삭제
        List<Comment> comments = commentRepository.findByPostId(id);
        commentRepository.deleteAll(comments);

        // 게시물 삭제
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 게시물에 좋아요 추가
    public Post addLike(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        post.setLikes(post.getLikes() + 1);
        return postRepository.save(post);
    }
    // 게시물 좋아요 증가
    public void likePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        post.setLikes(post.getLikes() + 1); // 좋아요 수 증가
        postRepository.save(post);
    }

    // 게시물에 댓글 추가
    public Post addComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        comment.setPost(post);
        commentRepository.save(comment);
        return post;
    }
}
