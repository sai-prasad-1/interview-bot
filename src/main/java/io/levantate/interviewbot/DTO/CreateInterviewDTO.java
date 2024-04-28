package io.levantate.interviewbot.DTO;

import lombok.Getter;

@Getter
public class CreateInterviewDTO {
    private String role;
    private String level;
    private Integer count;
    private Long userId;
}
