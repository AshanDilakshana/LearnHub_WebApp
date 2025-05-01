package com.paf.learnhub.repositories;

import com.paf.learnhub.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * This interface connects to the MongoDB "comments" collection.
 * It helps to save, update, delete, and find comments easily.
 */
public interface CommentRepository extends MongoRepository<Comment, String> {

    // Get a list of comments by the post's ID
    List<Comment> findByPostId(String postId);
}