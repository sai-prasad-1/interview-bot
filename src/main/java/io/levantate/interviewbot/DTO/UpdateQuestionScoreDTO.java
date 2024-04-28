package io.levantate.interviewbot.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateQuestionScoreDTO {
    private Long questionId;
    private String answer;
}
