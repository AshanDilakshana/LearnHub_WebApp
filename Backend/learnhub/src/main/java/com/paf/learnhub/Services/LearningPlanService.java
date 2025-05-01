package com.paf.learnhub.Services;

import com.paf.learnhub.models.LearningPlan;
import com.paf.learnhub.repositories.LearningPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LearningPlanService {
    @Autowired
    private LearningPlanRepository learningPlanRepository;

    public LearningPlan createLearningPlan(LearningPlan plan) {
        return learningPlanRepository.save(plan);
    }

    public List<LearningPlan> getAllLearningPlans() {
        return learningPlanRepository.findAll();
    }

    public Optional<LearningPlan> getLearningPlanById(String id) {
        return learningPlanRepository.findById(id);
    }

    public LearningPlan updateLearningPlan(String id, LearningPlan plan) {
        Optional<LearningPlan> existing = learningPlanRepository.findById(id);
        if (existing.isPresent()) {
            LearningPlan updated = existing.get();
            updated.setTitle(plan.getTitle());
            updated.setTopics(plan.getTopics());
            updated.setTimeline(plan.getTimeline());
            return learningPlanRepository.save(updated);
        }
        return null;
    }

    public void deleteLearningPlan(String id) {
        learningPlanRepository.deleteById(id);
    }
}