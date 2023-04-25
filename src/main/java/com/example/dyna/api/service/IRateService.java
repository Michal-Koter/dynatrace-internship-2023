package com.example.dyna.api.service;

import com.example.dyna.api.contract.AverageRateDto;
import com.example.dyna.api.contract.BidAskRateDto;

import java.time.LocalDate;
import java.util.Map;

public interface IRateService {
    AverageRateDto findAvgRateByDate(String code, LocalDate date) throws Exception;
    Map<String, AverageRateDto> findMinMaxAvgLastRates(String code, int quotations) throws Exception;
    BidAskRateDto findMaxRateDiff(String code, int quotations) throws Exception;
}
