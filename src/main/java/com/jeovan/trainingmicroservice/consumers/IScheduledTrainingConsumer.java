package com.jeovan.trainingmicroservice.consumers;

import com.jeovan.trainingmicroservice.dtos.TrainerMonthReportRequestDTO;
import com.jeovan.trainingmicroservice.dtos.TrainingDetailsDto;

public interface IScheduledTrainingConsumer {
    void saveTrainingScheduled(TrainingDetailsDto trainingDetailsDto);
    void reportTrainerScheduledTrainings(TrainerMonthReportRequestDTO trainerMonthReportRequestDTO);
}
