package io.levantate.interviewbot.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import io.levantate.interviewbot.DTO.GetQuestionsDTO;
import io.levantate.interviewbot.utils.AIPredictionHelper;

@Service
public class QuestionsService {

    public List<String> genarateQuestions(GetQuestionsDTO parameters) throws IOException{

        String prompt1 = "Could you generate " + parameters.getCount() + " questions tailored for a " + parameters.getLevel() + " level " + parameters.getRole() + "? These questions should cover key topics relevant to " + parameters.getRole() + ", such as technical skills, problem-solving abilities, and experience with specific tools or methodologies. Please ensure the questions are well-crafted and suitable for assessing candidates with " + parameters.getLevel() + " level expertise.";

        String prompt = "Could you generate " + parameters.getCount() + " technical questions tailored for a " + parameters.getLevel() + " level " + parameters.getRole() + "? These questions should cover advanced topics relevant to " + parameters.getRole() + ", such as [specific areas based on the role, e.g., software development, network engineering, data science]. Please ensure the questions require in-depth knowledge and expertise in the respective field.";



        List<String> questions = AIPredictionHelper.predictTextPrompt(prompt);

        return questions;
        
    }
    
}
