package com.jeovan.trainingmicroservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDetailsDto {

    @JsonProperty("username")
    private String username;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("isActive")
    private Boolean isActive;
    @JsonProperty("trainingDate")
    private Date trainingDate;
    @JsonProperty("duration")
    private double duration;
    @JsonProperty("actionType")
    private String actionType;
}
