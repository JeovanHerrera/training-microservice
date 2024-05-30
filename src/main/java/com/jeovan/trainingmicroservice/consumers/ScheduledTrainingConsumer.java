package com.jeovan.trainingmicroservice.consumers;

import com.jeovan.trainingmicroservice.constants.MessagingQueues;
import com.jeovan.trainingmicroservice.dtos.TrainerMonthReportRequestDTO;
import com.jeovan.trainingmicroservice.dtos.TrainingDetailsDto;
import com.jeovan.trainingmicroservice.services.TrainerScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTrainingConsumer {
    private final TrainerScheduleService trainerScheduleService;

    @JmsListener(destination = MessagingQueues.SCHEDULED_TRAINING_QUEUE)
    public void saveTrainingScheduled(TrainingDetailsDto trainingDetailsDto){
        trainerScheduleService.modifySchedule(trainingDetailsDto);
        log.info("Added {} minutes for trainer {}", trainingDetailsDto.getDuration(), trainingDetailsDto.getUsername());
    }

    @JmsListener(destination = MessagingQueues.SCHEDULED_HOURS_QUEUE)
    public void reportTrainerScheduledTrainings(TrainerMonthReportRequestDTO trainerMonthReportRequestDTO){
        trainerScheduleService.calculateScheduledHoursByMonth(trainerMonthReportRequestDTO.getUsername(), trainerMonthReportRequestDTO.getYearMonth());
        log.info("Reported scheduled hours for trainer {} for period {}", trainerMonthReportRequestDTO.getUsername(), trainerMonthReportRequestDTO.getYearMonth());
    }
}
