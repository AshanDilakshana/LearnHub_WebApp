package com.paf.learnhub.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//This class as a MongoDB document stored in the "comments" collection
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private String postId;
    private String userId;
    private String userName;
    private String content;
    private String createdAt;

    // Getters and Setters
    public String getId() {
        return id;
    } // ID

    public void setId(String id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    } // Post ID

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    } // User ID

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    } // User Name

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    } // Comment Content

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    } // Created At timestamp

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}