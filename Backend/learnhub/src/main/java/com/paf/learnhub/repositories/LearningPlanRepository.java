package com.paf.learnhub.repositories;

import com.paf.learnhub.models.LearningPlan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LearningPlanRepository extends MongoRepository<LearningPlan, String> {
}