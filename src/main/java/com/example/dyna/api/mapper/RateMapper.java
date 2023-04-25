package com.example.dyna.api.mapper;

import com.example.dyna.client.contract.RateDto;
import com.example.dyna.api.contract.AverageRateDto;
import com.example.dyna.api.contract.BidAskRateDto;
import org.springframework.stereotype.Component;

@Component
public class RateMapper implements IRateMapper {
    @Override
    public AverageRateDto mapToAvg(RateDto rateDto) {
        AverageRateDto averageRateDto = new AverageRateDto();
        averageRateDto.setEffectiveDate(rateDto.getEffectiveDate());
        averageRateDto.setAverage(rateDto.getMid());
        return averageRateDto;
    }

    @Override
    public BidAskRateDto mapToBidAsk(RateDto rateDto) {
        BidAskRateDto buyAskRateDto = new BidAskRateDto();
        buyAskRateDto.setEffectiveDate(rateDto.getEffectiveDate());
        buyAskRateDto.setAsk(rateDto.getAsk());
        buyAskRateDto.setBid(rateDto.getBid());

        double diff = buyAskRateDto.getBid() - buyAskRateDto.getAsk();
        buyAskRateDto.setDifference(
                round(diff)
        );
        return buyAskRateDto;
    }

    private double round(double diff) {
        long factor = (long) Math.pow(10, 4);
        diff = diff * factor;
        return (double) Math.round(diff) / factor;
    }
}
