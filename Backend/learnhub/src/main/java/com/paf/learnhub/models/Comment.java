package com.paf.learnhub.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Comment model representing the 'comments' collection in MongoDB.
 */

@Document(collection = "comments")
public class Comment {
    @Id
    private String id;// The comment's unique ID
    private String postId;// The ID of the post this comment is for
    private String userId;// The ID of the user who wrote the comment
    private String userName;// The name of the user who wrote the comment
    private String content;// The message/text of the comment
    private String createdAt;// When the comment was posted (date and time)

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