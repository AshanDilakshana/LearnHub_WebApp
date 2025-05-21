package com.paf.learnhub.repositories;

import com.paf.learnhub.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// Inherits all basic CRUD methods from MongoRepository (e.g., save, findById, deleteById)
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostId(String postId);

    List<Comment> findByUserId(String userId);
} // part end