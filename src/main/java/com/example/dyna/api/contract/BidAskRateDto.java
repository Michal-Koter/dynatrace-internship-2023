package com.example.dyna.api.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BidAskRateDto {
    private String code;
    @JsonProperty("effective_date")
    private LocalDate effectiveDate;
    private Double bid;
    private Double ask;
    private Double difference;
}
