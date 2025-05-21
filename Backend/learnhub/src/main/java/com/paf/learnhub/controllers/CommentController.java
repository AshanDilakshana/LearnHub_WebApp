package com.paf.learnhub.controllers;

import com.paf.learnhub.models.Comment;
import com.paf.learnhub.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Marks this class as a REST controller and maps all routes under "/api/comments"
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    // Automatically injects the CommentService (business logic layer)
    @Autowired
    private CommentService commentService;

    // CREATE a new comment
    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
        try {
            Comment savedComment = commentService.addComment(
                    comment.getPostId(),
                    comment.getUserId(),
                    comment.getUserName(),
                    comment.getContent());
            return ResponseEntity.ok(savedComment);// return saved
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request on error
        }
    }

    // Read a comment
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable String postId) {
        try {
            List<Comment> comments = commentService.getCommentsByPostId(postId);
            return ResponseEntity.ok(comments); // Return list of comments
        } catch (Exception e) {
            return ResponseEntity.status(404).build();//// Return 404 if post or comments not found
        }
    }

    // UPDATE a comment
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable String id,
            @RequestBody UpdateCommentRequest request) {
        try {
            Comment updatedComment = commentService.updateComment(
                    id,
                    request.getContent(),
                    request.getUserId());
            return ResponseEntity.ok(updatedComment);// Return updated comment
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Return 400 on error
        }
    }

    // DELETE a comment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id, @RequestBody Comment comment) {
        commentService.deleteComment(id, comment.getUserId());
        return ResponseEntity.ok().build();// Return 200 OK on successful delete

    };

    // DTO(DATA TRANSFER OBJECT)
    public static class UpdateCommentRequest {
        private String userId;
        private String content;

        // Getter and Setter for userId
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        // Getter and Setter for content
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}