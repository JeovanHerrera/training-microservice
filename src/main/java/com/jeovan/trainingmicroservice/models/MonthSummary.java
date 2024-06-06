package com.jeovan.trainingmicroservice.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class MonthSummary {
    private String month;
    private Double duration;
}