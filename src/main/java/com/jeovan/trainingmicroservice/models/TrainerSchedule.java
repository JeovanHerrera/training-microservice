package com.jeovan.trainingmicroservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class TrainerSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean status;

    @ElementCollection
    @CollectionTable(name = "duration_by_month", joinColumns = {@JoinColumn(name = "trainer_username", referencedColumnName = "username")})
    @MapKeyColumn(name = "year_month")
    @Column(name = "duration")
    private Map<String, Double> durationByMonth;
}
