package io.levantate.interviewbot.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.google.cloud.aiplatform.v1beta1.PredictResponse;

import io.levantate.interviewbot.service.AIPredictionService;

@RestController
@RequestMapping("/api/predictions")
public class PredictionController {

    private final AIPredictionService predictionService;

    @Autowired
    public PredictionController(AIPredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/text")
    public List<String> predictText(@RequestBody String prompt) {
        try {
            String prompt1 = "Give me ten interview questions for the role project manager.";
            return predictionService.predictTextPrompt(prompt1);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
