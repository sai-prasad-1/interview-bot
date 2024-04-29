package io.levantate.interviewbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.aiplatform.v1beta1.PredictResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.levantate.interviewbot.DTO.CreateInterviewDTO;
import io.levantate.interviewbot.models.Interview;
import io.levantate.interviewbot.models.Question;
import io.levantate.interviewbot.models.User;
import io.levantate.interviewbot.repository.InterviewRepository;
import io.levantate.interviewbot.repository.QuestionRepository;
import io.levantate.interviewbot.repository.UserRepository;
import io.levantate.interviewbot.utils.AIPredictionHelper;

@Service
public class InterviewService {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private UserRepository userRepository;

    public Interview createInterview(CreateInterviewDTO parameters) throws IOException {
        User user = userService.findById(parameters.getUserId());
        Interview interview = new Interview();
        interview.setUser(user);
        if (user == null) {
            // Handle the case where the User is not found:
            throw new IllegalArgumentException("User with id " + parameters.getUserId() + " not found"); 
            // Or, potentially create a default user if that fits your logic
       }
        interview.setRole(parameters.getRole());
        interview.setLevel(parameters.getLevel());
        interview = interviewRepository.save(interview);
        String prompt = "Could you generate " + parameters.getCount() + " technical questions tailored for a " +
                parameters.getLevel() + " level " + parameters.getRole()
                + "? These questions should cover advanced topics relevant to " +
                parameters.getRole()
                + ", such as [specific areas based on the role, e.g., software development, network engineering, data science]. Please ensure the questions require in-depth knowledge and expertise in the respective field.";

        PredictResponse predictResponse = AIPredictionHelper.predictTextPrompt(prompt);
        List<String> questionsList = AIPredictionHelper.extractQuestions(predictResponse);

        List<Question> questions = new ArrayList<>();
        for (String questionText : questionsList) {
            Question question = new Question();
            question.setQuestion(questionText);
            question.setScore(0);
            question.setInterview(interview);
            questions.add(questionRepository.save(question)); // Save each question
        }
        interview.setQuestions(questions);
        return interview;
    }



    public Integer setQuestionScore(Long questionId,String answer) throws IOException{

        // get the question from the database
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            question.setAnswer(answer);
            questionRepository.save(question);

            // create a propmpt to get the score for the question by comparing the answer and question 
            String prompt = "Given the question and answer, predict the similarity score between them. The score should be an integer value representing the degree of similarity between the question and the answer. Use techniques like semantic similarity or word embeddings to calculate the score. Question: " + question.getQuestion() + ". Answer: " + question.getAnswer() + ". The score should be an integer value between 0 and 100. For example, if the answer is 90% similar to the question, the score should be 90. If the answer is 100% similar to the question, the score should be 100. If the answer is 50% similar to the question, the score should be 50. If the answer is 0% similar to the question, the score should be 0. ";

            // get the score from the prompt
            PredictResponse predictResponse = AIPredictionHelper.predictTextPrompt(prompt);
            Integer score = AIPredictionHelper.extractScore(predictResponse);
            question.setScore(score);
            questionRepository.save(question);
            System.out.println("Score: " + score);
            return question.getScore();
        }
        throw new IllegalArgumentException("Question not found");
    }

    public List<Interview> getInterviews() {
        return interviewRepository.findAll();
    }
    public Interview getInterview(Long interviewId) {
        return interviewRepository.findById(interviewId).get();
    }

    public Iterable<Interview> getInterviewsForUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return interviewRepository.findByUser(user.get());
            }
            throw new IllegalArgumentException("User not found");
            }

}
