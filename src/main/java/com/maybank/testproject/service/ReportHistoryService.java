package com.maybank.testproject.service;

import com.maybank.testproject.DTO.request.SearchDto;
import com.maybank.testproject.DTO.response.SuccessDto;
import com.maybank.testproject.models.DownloadHistory;
import com.maybank.testproject.models.ReportHistory;
import org.springframework.core.io.Resource;

import java.net.MalformedURLException;
import java.util.List;

public interface ReportHistoryService {
    SuccessDto<ReportHistory> export(SearchDto searchDto);

    SuccessDto<List<ReportHistory>> getAll();

    Resource getDownloadReport(String key) throws MalformedURLException;

    SuccessDto<List<DownloadHistory>> getDownloadHistory();
}
