package io.levantate.interviewbot.repository;

import io.levantate.interviewbot.models.Interview;
import io.levantate.interviewbot.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findByUser(User user);
}
