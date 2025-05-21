package com.paf.learnhub.Services;

import com.paf.learnhub.models.Comment;
import com.paf.learnhub.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // Add a new comment to a specific post by a user
    public Comment addComment(String postId, String userId, String userName, String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new RuntimeException("Comment content cannot be empty");
        }
        Comment comment = new Comment();// Generate unique ID
        comment.setId(UUID.randomUUID().toString());
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setUserName(userName);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now().toString());// Add current timestamp
        return commentRepository.save(comment);// Save to DB
    }

    // Get all comments related to a specific post
    public List<Comment> getCommentsByPostId(String postId) {
        return commentRepository.findByPostId(postId);
    }

    // Changed Code Section
    public Comment updateComment(String commentId, String content, String userId) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (!commentOpt.isPresent() || !commentOpt.get().getUserId().equals(userId)) {
            throw new RuntimeException("Comment not found or unauthorized");
        }
        Comment comment = commentOpt.get();
        if (content == null || content.trim().isEmpty()) {
            throw new RuntimeException("Comment content cannot be empty");
        }
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now().toString()); // Update timestamp
        return commentRepository.save(comment);
    }

    // Delete all comments under a specific post (e.g., when post is deleted)
    public void deleteComment(String commentId, String userId) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (!commentOpt.isPresent() || !commentOpt.get().getUserId().equals(userId)) { // Check if the comment exists
                                                                                       // and if the user owns it
            throw new RuntimeException("Comment not found or unauthorized");
        }
        commentRepository.deleteById(commentId);
    }

    // Delete all comments made by a specific user (e.g., if user account is
    // removed)
    public void deleteCommentsByPostId(String postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        commentRepository.deleteAll(comments);
    }

    public void deleteCommentsByUserId(String userId) {
        List<Comment> comments = commentRepository.findByUserId(userId);
        commentRepository.deleteAll(comments);
    }

}