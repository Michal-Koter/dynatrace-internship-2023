package com.example.dyna.api.controller;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface IRateController {
    ResponseEntity<Object> findAvgRateByDate(String code, LocalDate date);
    ResponseEntity<Object> findMinMaxAvgLastRates(String code, int quotations);
    ResponseEntity<Object> findMaxRateDiff(String code, int quotations);
}
