package com.jeovan.trainingmicroservice.services;

import com.jeovan.trainingmicroservice.dtos.TrainingDetailsDto;

import java.util.Map;

public interface TrainerScheduleService {

    void modifySchedule(TrainingDetailsDto trainingDetailsDto);

    Double calculateScheduledHoursByMonth(String username, String yearMonth);
}
