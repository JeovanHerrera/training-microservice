package com.jeovan.trainingmicroservice.services;

import com.jeovan.trainingmicroservice.dtos.TrainingDetailsDto;


public interface TrainerScheduleService {

    void modifySchedule(TrainingDetailsDto trainingDetailsDto);
    void calculateScheduledHoursByMonth(String username, String yearMonth);
}
