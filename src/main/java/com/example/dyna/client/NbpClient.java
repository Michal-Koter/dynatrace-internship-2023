package com.example.dyna.client;

import com.example.dyna.client.contract.PageDto;
import com.example.dyna.client.contract.RateDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Component
public class NbpClient implements INbpClient {
    private RestTemplate restClient;
    private final String baseUrl = "api.nbp.pl/api";

    public NbpClient() {
        this.restClient = new RestTemplate();
    }

    @Override
    public RateDto getAvgRateByDate(String code, LocalDate date) throws NullPointerException {
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

        PageDto pageDto = restClient.getForObject(uri, PageDto.class);

        if (pageDto == null) {
            throw new NullPointerException();
        }
        List<RateDto> rates = pageDto.getRates();

        if (rates.size() != 1) {
            throw new NullPointerException();
        }

        return rates.get(0);
    }

    @Override
    public List<RateDto> getLastRates(String table, String code, int quotations) throws NullPointerException {
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

        PageDto pageDto = restClient.getForObject(uri, PageDto.class);

        if (pageDto == null) {
            throw new NullPointerException();
        }

        return pageDto.getRates();
    }

    @Override
    public List<RateDto> getCurrencyList() {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(baseUrl)
                .pathSegment("exchangerates")
                .pathSegment("tables")
                .pathSegment("a")
                .queryParam("format", "json")
                .build()
                .toUri();

        ResponseEntity<PageDto[]> response = restClient.getForEntity(uri, PageDto[].class);
        PageDto pageDto = Objects.requireNonNull(response.getBody())[0];
        return pageDto.getRates();
    }
}
