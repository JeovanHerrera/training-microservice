package com.jeovan.trainingmicroservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeovan.trainingmicroservice.constants.MessagingQueues;
import com.jeovan.trainingmicroservice.constants.ScheduledTrainingActions;
import com.jeovan.trainingmicroservice.daos.TrainerScheduleDao;
import com.jeovan.trainingmicroservice.dtos.TrainerMonthReportDTO;
import com.jeovan.trainingmicroservice.dtos.TrainingDetailsDto;
import com.jeovan.trainingmicroservice.models.MonthSummary;
import com.jeovan.trainingmicroservice.models.TrainerSchedule;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainerScheduleServiceImpl implements TrainerScheduleService{
    private final TrainerScheduleDao trainerScheduleDao;
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper mapper;
    private final SqsTemplate sqsTemplate;
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    @Transactional
    @Override
    public void modifySchedule(TrainingDetailsDto trainingDetailsDto) {

        // Transform training Date to a YYYY-MM format
        String yearMonth = getYearMonthFromTrainingDate(trainingDetailsDto.getTrainingDate());

        TrainerSchedule trainerSchedule = trainerScheduleDao.findByUsername(trainingDetailsDto.getUsername())
                .orElseGet(() -> transformDtoToTrainerScheduleModel(trainingDetailsDto, yearMonth));

        MonthSummary monthSummary = findOrCreateMonthSummary(trainerSchedule, yearMonth);

        // Add or subtract duration based on actionType
        Double currentDuration = monthSummary.getDuration();
        if (ScheduledTrainingActions.ADD.equalsIgnoreCase(trainingDetailsDto.getActionType())) {
            monthSummary.setDuration(currentDuration + trainingDetailsDto.getDuration());
        } else if (ScheduledTrainingActions.DELETE.equalsIgnoreCase(trainingDetailsDto.getActionType())) {
            monthSummary.setDuration(currentDuration - trainingDetailsDto.getDuration());
        }
        trainerScheduleDao.save(trainerSchedule);
    }

    @Override
    public void calculateScheduledHoursByMonth(String username, String yearMonth) {
        Optional<TrainerSchedule> trainerSchedule = trainerScheduleDao.findByUsernameAndDurationByMonthMonth(username, yearMonth);
        Double minutes = 0.0;
        if(trainerSchedule.isPresent()){
           Optional<MonthSummary> monthSummary = trainerSchedule.get().getDurationByMonth().stream().filter(monthSummary1 -> monthSummary1.getMonth().equalsIgnoreCase(yearMonth)).findFirst();
           if(monthSummary.isPresent()){
               minutes = monthSummary.get().getDuration();
           }
        }

        try {
            var message = mapper.writeValueAsString(TrainerMonthReportDTO.builder()
                    .username(username)
                    .yearMonth(yearMonth)
                    .minutes(minutes)
                    .build());
            sqsTemplate.send(to -> to.queue(MessagingQueues.SCHEDULED_HOURS_RESULT_QUEUE).payload(message));
            log.info("Scheduled hours results shared with main microservice successfully for user {} with duration {}", username, minutes);
        }catch (JsonProcessingException e){
            log.error("Unable to share scheduled hours results with main microservice");
        }
        /*jmsTemplate.convertAndSend(MessagingQueues.SCHEDULED_HOURS_RESULT_QUEUE,
                TrainerMonthReportDTO.builder()
                        .username(username)
                        .yearMonth(yearMonth)
                        .minutes(minutes)
                        .build());*/


    }

    private TrainerSchedule transformDtoToTrainerScheduleModel(TrainingDetailsDto trainingDetailsDto, String yearMonth){
        ArrayList<MonthSummary> durationByMonth = new ArrayList<>();
        durationByMonth.add(new MonthSummary(yearMonth, 0.0));
        return TrainerSchedule.builder()
                .username(trainingDetailsDto.getUsername())
                .firstName(trainingDetailsDto.getFirstName())
                .lastName(trainingDetailsDto.getLastName())
                .status(trainingDetailsDto.getIsActive())
                .durationByMonth(durationByMonth)
                .build();
    }

    private String getYearMonthFromTrainingDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(FORMATTER);
    }

    private MonthSummary findOrCreateMonthSummary(TrainerSchedule trainerSchedule, String yearMonth) {
        Optional<MonthSummary> monthSummary = trainerSchedule.getDurationByMonth().stream().filter(monthSummary1 -> monthSummary1.getMonth().equalsIgnoreCase(yearMonth)).findFirst();

        if(monthSummary.isEmpty()){
            monthSummary = Optional.of(new MonthSummary(yearMonth, 0.0));
            trainerSchedule.getDurationByMonth().add(monthSummary.get());
        }
        return monthSummary.get();
    }

}
