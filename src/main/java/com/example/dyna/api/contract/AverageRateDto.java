package com.example.dyna.api.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AverageRateDto {
    private String code;
    private Double average;
    @JsonProperty("effective_date")
    private LocalDate effectiveDate;
}
