package com.dmdev.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterUser {

    private String lastname;
    private String firstname;
}
