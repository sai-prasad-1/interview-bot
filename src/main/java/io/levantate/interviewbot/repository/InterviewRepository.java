package io.levantate.interviewbot.repository;

import io.levantate.interviewbot.models.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
}
