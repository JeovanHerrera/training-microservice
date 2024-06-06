package com.jeovan.trainingmicroservice.daos;

import com.jeovan.trainingmicroservice.models.TrainerSchedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface TrainerScheduleDao extends MongoRepository<TrainerSchedule, String> {
    Optional<TrainerSchedule> findByUsername(String username);

    @Query("{ 'username' : ?0, 'durationByMonth.month' : ?1 }")
    Optional<TrainerSchedule> findByUsernameAndDurationByMonthMonth(String username, String yearMonth);
}
