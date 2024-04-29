package io.levantate.interviewbot.controllers;

import java.io.IOException;
import java.net.URI;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import io.levantate.interviewbot.DTO.CreateInterviewDTO;
import io.levantate.interviewbot.DTO.UpdateQuestionScoreDTO;
import io.levantate.interviewbot.models.Interview;
import io.levantate.interviewbot.service.InterviewService;

@Controller
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @PostMapping("/interview")
    public ResponseEntity<?> createinterview(@RequestBody CreateInterviewDTO data) {

        try{
            Interview interview = interviewService.createInterview(data);
            return ResponseEntity.created(URI.create("interviews/"+interview.getId())).body(interview);

        }catch(Exception ex){
            return ResponseEntity.status(500).body("An Error Occured"+ex.getMessage());
        }

    }

    @GetMapping("/interviews")
    public @ResponseBody Iterable<Interview> getAllInterviews() {
        return interviewService.getInterviews();
    }
    
    @PostMapping("/interview/question")
    @ResponseBody
    public ResponseEntity<?> updateQuestionScore(@RequestBody UpdateQuestionScoreDTO data) {
        try{
            Integer score = interviewService.setQuestionScore(data.getQuestionId(), data.getAnswer());
            return ResponseEntity.ok(score);
        }catch(Exception ex){
            return ResponseEntity.status(500).body("An Error Occured"+ex.getMessage());
        }
    }


    // gert interviews for a certain userid
    @GetMapping("/interviews/user/{userId}")
    public @ResponseBody Iterable<Interview> getInterviewsForUser(@PathVariable("userId") Long userId) {
        return interviewService.getInterviewsForUser(userId);
    }

    // get interview for a certain interviewId
    @GetMapping("/interview/{interviewId}")
    public @ResponseBody Interview getInterview(@PathVariable("interviewId") Long interviewId) {
        return interviewService.getInterview(interviewId);
    }
}
