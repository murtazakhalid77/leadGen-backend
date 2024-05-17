package com.leadgen.backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SummaryDto {
    Double overAllRating;
    Double totalEarning;
    Integer totalRequestServed;
}
