package com.jeovan.trainingmicroservice.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerMonthReportRequestDTO {
    private String username;
    private String yearMonth;
}
