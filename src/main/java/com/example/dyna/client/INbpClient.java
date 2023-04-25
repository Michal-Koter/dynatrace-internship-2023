package com.example.dyna.client;

import com.example.dyna.client.contract.RateDto;

import java.time.LocalDate;
import java.util.List;

public interface INbpClient {
    RateDto getAvgRateByDate(String code, LocalDate date) throws NullPointerException;
    List<RateDto> getLastRates(String table, String code, int quotations) throws Exception;
    List<RateDto> getCurrencyList();
}
