package com.jeovan.trainingmicroservice.controllers;

import com.jeovan.trainingmicroservice.constants.EndPoint;
import com.jeovan.trainingmicroservice.dtos.TrainingDetailsDto;
import com.jeovan.trainingmicroservice.services.TrainerScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndPoint.TRAINER_SCHEDULE)
@RequiredArgsConstructor
public class TrainerScheduleController {
    private final TrainerScheduleService trainerScheduleService;

    @PostMapping
    public ResponseEntity<String> scheduleTraining(@RequestBody TrainingDetailsDto trainingDetailsDto){
        trainerScheduleService.modifySchedule(trainingDetailsDto);
        return ResponseEntity.ok("Training updated");
    }

    @GetMapping("/{username}/duration")
    public ResponseEntity<Double> getTrainerDurationByMonth(@PathVariable String username, @RequestParam String yearMonth) {
        Double duration = trainerScheduleService.calculateScheduledHoursByMonth(username, yearMonth);

        // if no record found
        if (duration == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(duration);
    }

    @GetMapping
    public String hello(){
        return "hello madafakas";
    }

}
