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

    // Add a new comment to a post
    public Comment addComment(String postId, String userId, String userName, String content) {
        // Check if the comment is empty
        if (content == null || content.trim().isEmpty()) {
            throw new RuntimeException("Comment content cannot be empty");
        }
        // Create a new comment and set its data
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID().toString());// Generate a unique ID
        comment.setPostId(postId);// set post id
        comment.setUserId(userId);// set user id
        comment.setUserName(userName);// set user name
        comment.setContent(content);// set content
        comment.setCreatedAt(LocalDateTime.now().toString());// Set current time
        return commentRepository.save(comment);
    }

    // Get all comments for a specific post
    public List<Comment> getCommentsByPostId(String postId) {
        return commentRepository.findByPostId(postId);
    }

    // Update a comment (only allowed if it's the same user)
    public Comment updateComment(String commentId, String content, String userId) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        // Check if comment exists and if the user is the owner
        if (!commentOpt.isPresent() || !commentOpt.get().getUserId().equals(userId)) {
            throw new RuntimeException("Comment not found or unauthorized");
        }
        Comment comment = commentOpt.get();
        // Check if new content is empty
        if (content == null || content.trim().isEmpty()) {
            throw new RuntimeException("Comment content cannot be empty");
        }
        comment.setContent(content);// Update comment text
        comment.setCreatedAt(LocalDateTime.now().toString()); // Update time
        return commentRepository.save(comment);// Save updated comment
    }

    // Delete a comment (only allowed if it's the same user)
    public void deleteComment(String commentId, String userId) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        // Check if comment exists and if the user is the owner
        if (!commentOpt.isPresent() || !commentOpt.get().getUserId().equals(userId)) {
            throw new RuntimeException("Comment not found or unauthorized");
        }
        commentRepository.deleteById(commentId);// Delete the comment
    }

    // Delete all comments for a given post
    public void deleteCommentsByPostId(String postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        commentRepository.deleteAll(comments);// Delete all comments for that post
    }

}