package io.levantate.interviewbot.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.levantate.interviewbot.DTO.GeneratedQuestionsDTO;
import io.levantate.interviewbot.DTO.GetQuestionsDTO;
import io.levantate.interviewbot.service.QuestionsService;

@RestController
public class QuestionsContoller {

    @Autowired
    private QuestionsService questionsService;



    @PostMapping("/questions")
    public ResponseEntity<Object> submitRegisterForm(@RequestBody GetQuestionsDTO questions) {
        
        try {
          
            List<String> generatedQuestions = questionsService.genarateQuestions(questions);
            GeneratedQuestionsDTO responseDTO = new GeneratedQuestionsDTO(generatedQuestions);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate questions: " + e.getMessage());
        }
    }
}
