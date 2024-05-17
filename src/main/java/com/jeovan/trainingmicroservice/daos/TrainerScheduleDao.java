package com.jeovan.trainingmicroservice.daos;

import com.jeovan.trainingmicroservice.models.TrainerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface TrainerScheduleDao extends JpaRepository<TrainerSchedule, UUID> {
    Optional<TrainerSchedule> findByUsername(String username);

    @Query("SELECT VALUE(d) FROM TrainerSchedule t JOIN t.durationByMonth d WHERE t.username = :username AND KEY(d) = :yearMonth")
    Double findDurationByYearMonth(@Param("username") String username, @Param("yearMonth") String yearMonth);
}
