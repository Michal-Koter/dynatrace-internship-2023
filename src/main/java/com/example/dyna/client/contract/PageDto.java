package com.example.dyna.client.contract;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageDto {
    private String table;
    private String code;
    private List<RateDto> rates = new ArrayList<>();
}
