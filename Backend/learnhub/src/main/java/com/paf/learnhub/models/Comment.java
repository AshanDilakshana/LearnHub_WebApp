package com.paf.learnhub.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Comment model representing the 'comments' collection in MongoDB.
 */

@Document(collection = "comments")
public class Comment {
    @Id
    private String id;// Unique identifier for the comment (MongoDB _id)
    private String postId;// ID of the post this comment belongs to
    private String userId;// ID of the user who wrote the comment
    private String userName;// Name of the user who wrote the comment
    private String content;// Actual text content of the comment
    private String createdAt;// Timestamp when the comment was created

    // Getters and Setters
    public String getId() { // id gettere
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for postId
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    // Getter and Setter for userId
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Getter and Setter for userName
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getter and Setter for content
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Getter and Setter for createdAt
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}