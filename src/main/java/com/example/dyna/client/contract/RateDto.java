package com.example.dyna.client.contract;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RateDto {
    private String code;
    private LocalDate effectiveDate;
    private Double mid;
    private Double bid;
    private Double ask;
}
