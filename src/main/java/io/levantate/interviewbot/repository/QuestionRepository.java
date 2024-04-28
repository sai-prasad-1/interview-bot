package io.levantate.interviewbot.repository;

import io.levantate.interviewbot.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
