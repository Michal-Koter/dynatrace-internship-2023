package com.example.dyna.api.mapper;

import com.example.dyna.client.contract.RateDto;
import com.example.dyna.api.contract.AverageRateDto;
import com.example.dyna.api.contract.BidAskRateDto;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class RateMapperTest {
    private final RateMapper mapper = new RateMapper();

    @Test
    public void mapToAvgTest() {
        Double average = 5.4268;
        LocalDate date = LocalDate.of(2022,6,16);
        RateDto rateDto = new RateDto();
        rateDto.setMid(average);
        rateDto.setEffectiveDate(date);

        AverageRateDto actualResult = mapper.mapToAvg(rateDto);

        Assert.assertEquals(average, actualResult.getAverage());
        Assert.assertEquals(date, actualResult.getEffectiveDate());
    }

    @Test
    public void mapToBidAskTest() {
        Double bid = 4.6648;
        Double ask = 4.4485;
        Double diff = 0.2163;
        LocalDate date = LocalDate.of(2022,5,26);

        RateDto rateDto = new RateDto();
        rateDto.setBid(bid);
        rateDto.setAsk(ask);
        rateDto.setEffectiveDate(date);

        BidAskRateDto actualResult = mapper.mapToBidAsk(rateDto);

        Assert.assertEquals(bid, actualResult.getBid());
        Assert.assertEquals(ask, actualResult.getAsk());
        Assert.assertEquals(diff, actualResult.getDifference());
        Assert.assertEquals(date, actualResult.getEffectiveDate());
    }
}
