package com.example.dpm.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MissionDto {
    private int missionId;
    private Long memberId; // Member reference by memberId
    private boolean status;
    private LocalDate missionDate;
}