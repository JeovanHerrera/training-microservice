package com.jeovan.trainingmicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDetailsDto {

    private String username;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private Date trainingDate;
    private double duration;
    private String actionType;
}
