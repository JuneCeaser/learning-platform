package com.skillsharing.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillsharing.backend.model.Comment;
import com.skillsharing.backend.model.Post;
import com.skillsharing.backend.repo.CommentRepository;
import com.skillsharing.backend.repo.PostRepository;
import com.skillsharing.backend.service.CommentService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;
    @Override
    public List<Comment> getCommentsForPost(String postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Comment addCommentToPost(String postId, String content, String commentBy, String commentById ,String commentByProfile) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCommentBy(commentBy);
        comment.setCommentById(commentById);
        comment.setCommentByProfile(commentByProfile);
        comment.setCreatedAt(String.valueOf(new Date()));

        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            comment.setPostId(post.getId());
            return commentRepository.save(comment);
        } else {
            return null;
        }
    }

    @Override
    public void deleteComment(String postId, String commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment editComment(String commentId, String content) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(content);
            return commentRepository.save(comment);
        } else {

            return null;
        }
    }
}

