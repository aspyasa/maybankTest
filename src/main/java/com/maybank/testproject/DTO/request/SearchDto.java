package com.maybank.testproject.DTO.request;

import lombok.Data;

@Data
public class SearchDto {

    private String search;
    private String sort = "followers";
    private String order = "desc";
    private Integer per_page = 10;
    private Integer page = 1;
}
