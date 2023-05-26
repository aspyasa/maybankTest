package com.maybank.testproject.service.impl;

import com.maybank.testproject.DTO.request.SearchDto;
import com.maybank.testproject.DTO.response.SuccessDto;
import com.maybank.testproject.DTO.response.github.SearchResultDTO;
import com.maybank.testproject.exception.GlobalException;
import com.maybank.testproject.models.DownloadHistory;
import com.maybank.testproject.models.ReportHistory;
import com.maybank.testproject.repositories.DownloadHistoryRepository;
import com.maybank.testproject.repositories.ReportHistoryRepository;
import com.maybank.testproject.service.ReportHistoryService;
import com.maybank.testproject.utils.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReportHistoryServiceImpl implements ReportHistoryService {
    @Autowired
    public ApiClient apiClient;
    @Autowired
    public JasperServiceImpl jasperService;

    @Autowired
    public ReportHistoryRepository reportHistoryRepository;
    @Autowired
    public DownloadHistoryRepository downloadHistoryRepository;
    @Override
    public SuccessDto<ReportHistory> export(SearchDto searchDto) {
        SearchResultDTO searchResultDTO = apiClient.searchUrl(searchDto);
        String names = String.valueOf(UUID.randomUUID());
        String fileName =  jasperService.generateReport(searchResultDTO,names);
        ReportHistory reportHistory = new ReportHistory();
        reportHistory.setFileName(fileName);
        reportHistory.setKeyDownload(names);
        reportHistory.setDownloadUrl("/report/download/"+names);
        reportHistory.setExportDate(LocalDateTime.now());
        reportHistoryRepository.save(reportHistory);

        SuccessDto<ReportHistory> reportHistorySuccessDto = new SuccessDto<>();
        reportHistorySuccessDto.setResult(reportHistory);
        reportHistorySuccessDto.setMessage("SUCCESS");
        return reportHistorySuccessDto;
    }

    @Override
    public SuccessDto<List<ReportHistory>> getAll() {
        List<ReportHistory> reportHistoryList = reportHistoryRepository.findAll();
        SuccessDto<List<ReportHistory>> reportHistorySuccessDto = new SuccessDto<>();
        reportHistorySuccessDto.setResult(reportHistoryList);
        reportHistorySuccessDto.setMessage("SUCCESS");
        return reportHistorySuccessDto;}

    @Override
    public Resource getDownloadReport(String id) throws MalformedURLException {
        Optional<ReportHistory> reportHistory = reportHistoryRepository.findByFileName(id);
        if (reportHistory.isPresent()) {
            DownloadHistory downloadHistory = new DownloadHistory();
            downloadHistory.setDownloadDate(LocalDateTime.now());
            downloadHistory.setReportingHistory(reportHistory.get());
            downloadHistoryRepository.save(downloadHistory);
            return jasperService.downloadReport(reportHistory.get().getFileName());
        }
        else {
            throw new GlobalException("KEY_NOT_FOUND", "Key Not Valid");
        }
    }

    @Override
    public SuccessDto<List<DownloadHistory>> getDownloadHistory() {
        SuccessDto<List<DownloadHistory>> downloadHistorySuccessDto = new SuccessDto<>();
        List<DownloadHistory> downloadHistory = downloadHistoryRepository.findAll();
        downloadHistorySuccessDto.setResult(downloadHistory);
        downloadHistorySuccessDto.setMessage("SUCCESS");
        return downloadHistorySuccessDto;
    }
}
