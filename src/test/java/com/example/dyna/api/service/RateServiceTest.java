package com.example.dyna.api.service;

import com.example.dyna.client.INbpClient;
import com.example.dyna.client.contract.RateDto;
import com.example.dyna.api.contract.AverageRateDto;
import com.example.dyna.api.mapper.IRateMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RateServiceTest {
    @Mock
    private INbpClient nbpClient;
    @Mock
    private IRateMapper rateMapper;

    @InjectMocks
    private RateService service;

    @Before
    public void init() {
        service.getCurrencyCodes().add("USD");
    }

    @Test
    public void findAvgRateByDateValidRequestTest() throws Exception {
        String code = "usd";
        LocalDate date = LocalDate.of(2022,5,4);
        double mid = 4.5232;

        RateDto rateDto = new RateDto();
        rateDto.setCode(code);
        rateDto.setEffectiveDate(date);
        rateDto.setMid(mid);

        AverageRateDto avgRateDto = new AverageRateDto();
        avgRateDto.setCode(code);
        avgRateDto.setEffectiveDate(date);
        avgRateDto.setAverage(mid);

        when(nbpClient.getAvgRateByDate(code, date)).thenReturn(rateDto);
        when(rateMapper.mapToAvg(rateDto)).thenReturn(avgRateDto);

        AverageRateDto actualResult = service.findAvgRateByDate(code, date);

        verify(nbpClient).getAvgRateByDate(code, date);
        verify(rateMapper).mapToAvg(rateDto);
        Assert.assertEquals(avgRateDto.getAverage(), actualResult.getAverage());
        Assert.assertEquals(code, actualResult.getCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAvgRateByDateInvalidRequestTest() throws Exception {
        String code = "xyz";
        LocalDate date = LocalDate.of(2021,2,24);

        service.findAvgRateByDate(code, date);
    }
}
