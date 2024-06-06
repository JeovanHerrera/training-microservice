package com.jeovan.trainingmicroservice.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
@Builder
public class TrainerSchedule {
    @Id
    private String id;
    private String username;
    @Indexed
    private String firstName;
    @Indexed
    private String lastName;
    private Boolean status;
    private ArrayList<MonthSummary> durationByMonth;
}
