package io.levantate.interviewbot.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneratedQuestionsDTO {
    private List<String> questions;

    public GeneratedQuestionsDTO(List<String> questions) {
        this.questions = questions;
    }

    
    
}
