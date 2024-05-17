package com.jeovan.trainingmicroservice.services;

import com.google.common.collect.Maps;
import com.jeovan.trainingmicroservice.daos.TrainerScheduleDao;
import com.jeovan.trainingmicroservice.dtos.TrainingDetailsDto;
import com.jeovan.trainingmicroservice.models.TrainerSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TrainerScheduleServiceImpl implements TrainerScheduleService{
    private final TrainerScheduleDao trainerScheduleDao;

    @Override
    public void modifySchedule(TrainingDetailsDto trainingDetailsDto) {

        TrainerSchedule trainerSchedule = trainerScheduleDao.findByUsername(trainingDetailsDto.getUsername())
                .orElseGet(() -> transformDtoToTrainerScheduleModel(trainingDetailsDto));

        // Transform training Date to a YYYY-MM format
        String yearMonth = trainingDetailsDto.getTrainingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // Add or subtract duration based on actionType
        double newDuration = trainerSchedule.getDurationByMonth().getOrDefault(yearMonth, 0.0);
        if ("add".equalsIgnoreCase(trainingDetailsDto.getActionType())) {
            newDuration += trainingDetailsDto.getDuration();
        } else if ("delete".equalsIgnoreCase(trainingDetailsDto.getActionType())) {
            newDuration -= trainingDetailsDto.getDuration();
        }
        trainerSchedule.getDurationByMonth().put(yearMonth, newDuration);

        trainerScheduleDao.save(trainerSchedule);
    }

    @Override
    public Double calculateScheduledHoursByMonth(String username, String yearMonth) {
        return trainerScheduleDao.findDurationByYearMonth(username, yearMonth);
    }

    private TrainerSchedule transformDtoToTrainerScheduleModel(TrainingDetailsDto trainingDetailsDto){
        return TrainerSchedule.builder()
                .username(trainingDetailsDto.getUsername())
                .firstName(trainingDetailsDto.getFirstName())
                .lastName(trainingDetailsDto.getLastName())
                .status(trainingDetailsDto.getIsActive())
                .durationByMonth(Maps.newHashMap())
                .build();
    }

}
