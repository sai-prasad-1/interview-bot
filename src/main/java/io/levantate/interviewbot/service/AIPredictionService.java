package io.levantate.interviewbot.service;

import org.springframework.stereotype.Service;

import com.google.cloud.aiplatform.v1beta1.PredictResponse;

import io.levantate.interviewbot.utils.AIPredictionHelper;

import java.io.IOException;
import java.util.List;

@Service
public class AIPredictionService {

    public List<String> predictTextPrompt(String prompt) throws IOException {
        return AIPredictionHelper.predictTextPrompt(prompt);
    }
}
