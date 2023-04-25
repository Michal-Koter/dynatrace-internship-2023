package com.example.dyna.api.service;

import com.example.dyna.client.INbpClient;
import com.example.dyna.client.contract.RateDto;
import com.example.dyna.api.contract.AverageRateDto;
import com.example.dyna.api.contract.BidAskRateDto;
import com.example.dyna.api.mapper.IRateMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.example.nbpclient")
public class RateService implements IRateService {
    private final INbpClient nbpClient;
    private final IRateMapper mapper;
    private List<String> currencyCodes = new ArrayList<>();

    public List<String> getCurrencyCodes() {
        return currencyCodes;
    }

    @PostConstruct
    public void setCurrencyCodes() {
        var list = nbpClient.getCurrencyList();
        this.currencyCodes = list.stream().map(RateDto::getCode).toList();
    }

    @Override
    public AverageRateDto findAvgRateByDate(String code, LocalDate date) throws Exception {
        if (!isValidRequest(code, date)) {
            throw new IllegalArgumentException();
        }
        RateDto rateDto = nbpClient.getAvgRateByDate(code, date);
        AverageRateDto avgRate = mapper.mapToAvg(rateDto);
        avgRate.setCode(code);
        return avgRate;
    }

    @Override
    public Map<String, AverageRateDto> findMinMaxAvgLastRates(String code, int quotations) throws Exception {
        if (!isValidRequest(code, quotations)) {
            throw new IllegalArgumentException();
        }

        List<RateDto> rateDtoList = nbpClient.getLastRates("a", code, quotations);

        List<AverageRateDto> avgRateList = rateDtoList.stream()
                .map(mapper::mapToAvg)
                .toList();

        Optional<AverageRateDto> maxOptional = avgRateList.stream()
                .max(Comparator.comparingDouble(AverageRateDto::getAverage));
        Optional<AverageRateDto> minOptional = avgRateList.stream()
                .min(Comparator.comparingDouble(AverageRateDto::getAverage));

        if (maxOptional.isPresent() && minOptional.isPresent()) {
            AverageRateDto max = maxOptional.get();
            max.setCode(code);

            AverageRateDto min = minOptional.get();
            min.setCode(code);

            Map<String, AverageRateDto> result = new HashMap<>();
            result.put("min", min);
            result.put("max", max);
            return result;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public BidAskRateDto findMaxRateDiff(String code, int quotations) throws Exception {
        if (!isValidRequest(code,quotations)) {
            throw new IllegalAccessException();
        }

        List<RateDto> rateDtoList = nbpClient.getLastRates("c", code, quotations);

        List<BidAskRateDto> ratesDto = rateDtoList.stream().map(mapper::mapToBidAsk).toList();

        Optional<BidAskRateDto> maxOptional = ratesDto.stream()
                .max(Comparator.comparingDouble(BidAskRateDto::getDifference));

        if (maxOptional.isPresent()) {
            BidAskRateDto max = maxOptional.get();
            max.setCode(code);
            return max;
        } else {
            throw new NullPointerException();
        }
    }

    private boolean isValidRequest(String code, LocalDate date) {
        return currencyCodes.stream().anyMatch(code::equalsIgnoreCase) && !date.isAfter(LocalDate.now());
    }

    private boolean isValidRequest(String code, int quotations) {
        return currencyCodes.stream().anyMatch(code::equalsIgnoreCase) && (quotations >= 0 && quotations <= 255);
    }
}
