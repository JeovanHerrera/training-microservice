package com.jeovan.trainingmicroservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeovan.trainingmicroservice.constants.MessagingQueues;
import com.jeovan.trainingmicroservice.dtos.TrainerMonthReportRequestDTO;
import com.jeovan.trainingmicroservice.dtos.TrainingDetailsDto;
import com.jeovan.trainingmicroservice.services.TrainerScheduleService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile("local")
public class ScheduledTrainingConsumerAws {
    private final TrainerScheduleService trainerScheduleService;
    private final ObjectMapper mapper;

    @SqsListener(MessagingQueues.SCHEDULED_TRAINING_QUEUE)
    public void saveTrainingScheduled(String trainingDetailsDTOMessage) throws JsonProcessingException{
        TrainingDetailsDto trainingDetailsDto = mapper.readValue(trainingDetailsDTOMessage, TrainingDetailsDto.class);
        trainerScheduleService.modifySchedule(trainingDetailsDto);
        log.info("Added {} minutes for trainer {}", trainingDetailsDto.getDuration(), trainingDetailsDto.getUsername());
    }
    @SqsListener(MessagingQueues.SCHEDULED_HOURS_QUEUE)
    public void reportTrainerScheduledTrainings(String trainerMonthReportRequestDTOMessage) throws JsonProcessingException {
        TrainerMonthReportRequestDTO trainerMonthReportRequestDTO = mapper.readValue(trainerMonthReportRequestDTOMessage, TrainerMonthReportRequestDTO.class);
        trainerScheduleService.calculateScheduledHoursByMonth(trainerMonthReportRequestDTO.getUsername(), trainerMonthReportRequestDTO.getYearMonth());
        log.info("Reported scheduled hours for trainer {} for period {}", trainerMonthReportRequestDTO.getUsername(), trainerMonthReportRequestDTO.getYearMonth());
    }

}
