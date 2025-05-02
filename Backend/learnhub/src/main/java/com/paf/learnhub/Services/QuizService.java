package com.paf.learnhub.Services;

import com.paf.learnhub.models.Quiz;
import com.paf.learnhub.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    // Create a new quiz with validation
    public Quiz createQuiz(String videoId, String userId, String userName, List<Quiz.Question> questions) {
        // Ensure there are at least 5 questions
        if (questions.size() < 5) {
            throw new RuntimeException("Quiz must have at least 5 questions");
        }
        // Validate each question has 4 answers and a valid correct answer index
        for (Quiz.Question q : questions) {
            if (q.getAnswers().size() != 4 || q.getCorrectAnswerIndex() < 0 || q.getCorrectAnswerIndex() > 3) {
                throw new RuntimeException("Each question must have exactly 4 answers and a valid correct answer index");
            }
        }
        // Create and save the quiz
        Quiz quiz = new Quiz();
        quiz.setId(UUID.randomUUID().toString());
        quiz.setVideoId(videoId);
        quiz.setUserId(userId);
        quiz.setUserName(userName);
        quiz.setQuestions(questions);
        return quizRepository.save(quiz);
    }

    // Update an existing quiz with new questions
    public Quiz updateQuiz(String quizId, String userId, List<Quiz.Question> questions) {
        // Retrieve quiz or throw if not found
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        // Ensure the quiz belongs to the user
        if (!quiz.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        // Ensure updated quiz has valid structure
        if (questions.size() < 5) {
            throw new RuntimeException("Quiz must have at least 5 questions");
        }
        for (Quiz.Question q : questions) {
            if (q.getAnswers().size() != 4 || q.getCorrectAnswerIndex() < 0 || q.getCorrectAnswerIndex() > 3) {
                throw new RuntimeException("Each question must have exactly 4 answers and a valid correct answer index");
            }
        }
        // Update and save quiz
        quiz.setQuestions(questions);
        return quizRepository.save(quiz);
    }

    // Delete a quiz by ID, ensuring the user is authorized
    public void deleteQuiz(String quizId, String userId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        if (!quiz.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        quizRepository.deleteById(quizId);
    }

    // Retrieve a quiz based on the associated video ID
    public Quiz getQuizByVideoId(String videoId) {
        return quizRepository.findByVideoId(videoId)
                .orElseThrow(() -> new RuntimeException("Quiz not found for video"));
    }

    // Calculate score based on user's submitted answers
    public int calculateScore(String quizId, List<Integer> userAnswers) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        // Ensure the number of answers matches the number of questions        
        if (userAnswers.size() != quiz.getQuestions().size()) {
            throw new RuntimeException("Invalid number of answers submitted");
        }
        int score = 0;
        // Compare each answer with the correct index
        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            if (userAnswers.get(i) == quiz.getQuestions().get(i).getCorrectAnswerIndex()) {
                score++;
            }
        }
        return score;
    }
}