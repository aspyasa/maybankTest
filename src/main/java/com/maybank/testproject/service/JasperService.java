package com.maybank.testproject.service;

import com.maybank.testproject.DTO.response.github.SearchResultDTO;
import org.springframework.core.io.Resource;
import java.net.MalformedURLException;

public interface JasperService {
    String generateReport(SearchResultDTO searchResultDTO,String name);

    Resource downloadReport(String id) throws MalformedURLException;


}
