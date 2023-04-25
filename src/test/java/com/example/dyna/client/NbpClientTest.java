package com.example.dyna.client;

import com.example.dyna.client.contract.PageDto;
import com.example.dyna.client.contract.RateDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NbpClientTest {
    @Mock
    private RestTemplate restClient;
    private final String baseUrl = "api.nbp.pl/api";

    @InjectMocks
    private NbpClient nbpClient;

    @Test
    public void getAvgRateByDateTest() {
        String code = "USD";
        LocalDate date = LocalDate.of(2022, 5, 19);
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(baseUrl)
                .pathSegment("exchangerates")
                .pathSegment("rates")
                .pathSegment("a")
                .pathSegment(code)
                .pathSegment(date.toString())
                .queryParam("format", "json")
                .build()
                .toUri();

        RateDto expectedValue = new RateDto();
        expectedValue.setMid(5.3216);
        expectedValue.setEffectiveDate(date);

        PageDto pageDto = new PageDto();
        pageDto.setCode(code);
        pageDto.setTable("A");
        pageDto.getRates().add(expectedValue);

        when(restClient.getForObject(uri, PageDto.class)).thenReturn(pageDto);

        RateDto actualValue = nbpClient.getAvgRateByDate(code, date);

        Assert.assertEquals(expectedValue.getEffectiveDate(), actualValue.getEffectiveDate());
        Assert.assertEquals(expectedValue.getMid(), actualValue.getMid());
    }

    @Test(expected = NullPointerException.class)
    public void getAvgRateByDateNullPageDtoTest() {
        String code = "USD";
        LocalDate date = LocalDate.of(2022, 5, 19);
        nbpClient.getAvgRateByDate(code, date);
    }

    @Test(expected = NullPointerException.class)
    public void getAvgRateByDateNullRateDtoTest() {
        String code = "USD";
        LocalDate date = LocalDate.of(2022, 5, 19);
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(baseUrl)
                .pathSegment("exchangerates")
                .pathSegment("rates")
                .pathSegment("a")
                .pathSegment(code)
                .pathSegment(date.toString())
                .queryParam("format", "json")
                .build()
                .toUri();

        PageDto pageDto = new PageDto();
        pageDto.setCode(code);
        pageDto.setTable("A");

        when(restClient.getForObject(uri, PageDto.class)).thenReturn(pageDto);

        nbpClient.getAvgRateByDate(code, date);
    }

    @Test
    public void getLastRatesTest() {
        String code = "usd";
        int quotations = 3;
        String table = "a";
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(baseUrl)
                .pathSegment("exchangerates")
                .pathSegment("rates")
                .pathSegment(table)
                .pathSegment(code)
                .pathSegment("last")
                .pathSegment(String.valueOf(quotations))
                .queryParam("format", "json")
                .build()
                .toUri();

        PageDto pageDto = new PageDto();
        pageDto.setTable(table);
        pageDto.setCode(code);
        pageDto.getRates().add(new RateDto());
        pageDto.getRates().add(new RateDto());
        pageDto.getRates().add(new RateDto());

        when(restClient.getForObject(uri, PageDto.class)).thenReturn(pageDto);

        List<RateDto> actualResult = nbpClient.getLastRates(table,code,quotations);

        Assert.assertEquals(quotations, actualResult.size());
    }

    @Test(expected = NullPointerException.class)
    public void getLastRatesNullPageDtoTest() {
        String code = "usd";
        int quotations = 3;
        String table = "a";

        nbpClient.getLastRates(table,code,quotations);
    }

    @Test
    public void getCurrencyListTest() {
        String table = "a";

        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(baseUrl)
                .pathSegment("exchangerates")
                .pathSegment("tables")
                .pathSegment(table)
                .queryParam("format", "json")
                .build()
                .toUri();

        PageDto page = new PageDto();
        RateDto rateA = new RateDto();
        rateA.setCode("usd");
        page.getRates().add(rateA);
        RateDto rateB = new RateDto();
        rateB.setCode("chf");
        page.getRates().add(rateB);

        PageDto[] body = {page};
        ResponseEntity<PageDto[]> response = new ResponseEntity<>(body, HttpStatus.OK);

        when(restClient.getForEntity(uri, PageDto[].class)).thenReturn(response);

        List<RateDto> actualResult = nbpClient.getCurrencyList();

        Assert.assertEquals(page.getRates().size(), actualResult.size());
    }
}
