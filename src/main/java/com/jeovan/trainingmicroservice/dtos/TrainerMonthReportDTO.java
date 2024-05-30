package com.jeovan.trainingmicroservice.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerMonthReportDTO {
    private String username;
    private String yearMonth;
    private Double minutes;
}
