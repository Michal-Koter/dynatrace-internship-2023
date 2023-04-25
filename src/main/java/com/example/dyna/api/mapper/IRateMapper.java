package com.example.dyna.api.mapper;

import com.example.dyna.client.contract.RateDto;
import com.example.dyna.api.contract.AverageRateDto;
import com.example.dyna.api.contract.BidAskRateDto;

public interface IRateMapper {
    AverageRateDto mapToAvg(RateDto rateDto);
    BidAskRateDto mapToBidAsk(RateDto rateDto);
}
