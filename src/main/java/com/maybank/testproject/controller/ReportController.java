package com.maybank.testproject.controller;

import com.maybank.testproject.DTO.request.SearchDto;
import com.maybank.testproject.DTO.response.SuccessDto;
import com.maybank.testproject.models.DownloadHistory;
import com.maybank.testproject.models.ReportHistory;
import com.maybank.testproject.service.impl.ReportHistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping(path = "/report")
public class ReportController {

    @Autowired
    ReportHistoryServiceImpl reportHistoryService;

    @GetMapping("/export")
    public SuccessDto<ReportHistory> export(SearchDto searchDto) {
        return reportHistoryService.export(searchDto);
    }

    @GetMapping("/all")
    public SuccessDto<List<ReportHistory>> getAll() {
        return reportHistoryService.getAll();
    }

    @GetMapping("/download/{key}")
    public ResponseEntity<Resource> download(@PathVariable String key) throws MalformedURLException {
        Resource resource = reportHistoryService.getDownloadReport(key);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + resource.getFilename());
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @GetMapping("/getDownloadHistory")
    public SuccessDto<List<DownloadHistory>> getDownloadHistory() {
        return reportHistoryService.getDownloadHistory();
    }
}
