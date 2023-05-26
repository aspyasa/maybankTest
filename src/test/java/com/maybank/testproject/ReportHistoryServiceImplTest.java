package com.maybank.testproject;

import com.maybank.testproject.DTO.request.SearchDto;
import com.maybank.testproject.DTO.response.SuccessDto;
import com.maybank.testproject.DTO.response.github.SearchResultDTO;
import com.maybank.testproject.exception.GlobalException;
import com.maybank.testproject.models.DownloadHistory;
import com.maybank.testproject.models.ReportHistory;
import com.maybank.testproject.repositories.DownloadHistoryRepository;
import com.maybank.testproject.repositories.ReportHistoryRepository;
import com.maybank.testproject.service.impl.JasperServiceImpl;
import com.maybank.testproject.service.impl.ReportHistoryServiceImpl;
import com.maybank.testproject.utils.ApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportHistoryServiceImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private JasperServiceImpl jasperService;

    @Mock
    private ReportHistoryRepository reportHistoryRepository;

    @Mock
    private DownloadHistoryRepository downloadHistoryRepository;

    @InjectMocks
    private ReportHistoryServiceImpl reportHistoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void export_ShouldReturnSuccessDtoWithReportHistory() throws MalformedURLException {
        // Arrange
        SearchDto searchDto = new SearchDto();
        SearchResultDTO searchResultDTO = new SearchResultDTO();
        when(apiClient.searchUrl(searchDto)).thenReturn(searchResultDTO);

        String fileName = "report.pdf";
        when(jasperService.generateReport(same(searchResultDTO), anyString())).thenReturn(fileName);

        ReportHistory expectedReportHistory = new ReportHistory();
        expectedReportHistory.setFileName(fileName);
        expectedReportHistory.setKeyDownload(UUID.randomUUID().toString());
        when(reportHistoryRepository.save(any(ReportHistory.class))).thenReturn(expectedReportHistory);

        // Act
        SuccessDto<ReportHistory> result = reportHistoryService.export(searchDto);

        // Assert
        assertEquals("SUCCESS", result.getMessage());
        ReportHistory actualReportHistory = result.getResult();
        assertNotNull(actualReportHistory);
        assertEquals(expectedReportHistory.getFileName(), actualReportHistory.getFileName());
        verify(apiClient).searchUrl(searchDto);
        verify(jasperService).generateReport(same(searchResultDTO), anyString());
        verify(reportHistoryRepository).save(any(ReportHistory.class));
    }



    @Test
    void getAll_ShouldReturnSuccessDtoWithReportHistoryList() {
        // Arrange
        List<ReportHistory> reportHistoryList = Arrays.asList(new ReportHistory(), new ReportHistory());
        when(reportHistoryRepository.findAll()).thenReturn(reportHistoryList);

        // Act
        SuccessDto<List<ReportHistory>> result = reportHistoryService.getAll();

        // Assert
        assertEquals("SUCCESS", result.getMessage());
        assertEquals(reportHistoryList, result.getResult());
        verify(reportHistoryRepository).findAll();
    }

    @Test
    void getDownloadReport_WithValidId_ShouldReturnResource() throws MalformedURLException {
        // Arrange
        String id = "report.pdf";
        ReportHistory reportHistory = new ReportHistory();
        Optional<ReportHistory> reportHistoryOptional = Optional.of(reportHistory);
            when(reportHistoryRepository.findByFileName(id)).thenReturn(reportHistoryOptional);

        DownloadHistory downloadHistory = new DownloadHistory();
        when(downloadHistoryRepository.save(any(DownloadHistory.class))).thenReturn(downloadHistory);

        Resource resource = mock(Resource.class);
        when(jasperService.downloadReport(reportHistory.getFileName())).thenReturn(resource);

        // Act
        Resource result = reportHistoryService.getDownloadReport(id);

        // Assert
        assertNotNull(result);
        verify(reportHistoryRepository).findByFileName(id);
        verify(downloadHistoryRepository).save(any(DownloadHistory.class));
        verify(jasperService).downloadReport(reportHistory.getFileName());
    }

    @Test
    void getDownloadReport_WithInvalidId_ShouldThrowGlobalException() {
        // Arrange
        String id = "invalid-id";
        Optional<ReportHistory> reportHistoryOptional = Optional.empty();
        when(reportHistoryRepository.findByFileName(id)).thenReturn(reportHistoryOptional);

        // Act & Assert
        assertThrows(GlobalException.class, () -> reportHistoryService.getDownloadReport(id));
    }

    @Test
    void getDownloadHistory_ShouldReturnSuccessDtoWithDownloadHistoryList() {
        // Arrange
        List<DownloadHistory> downloadHistoryList = Arrays.asList(new DownloadHistory(), new DownloadHistory());
        when(downloadHistoryRepository.findAll()).thenReturn(downloadHistoryList);

        // Act
        SuccessDto<List<DownloadHistory>> result = reportHistoryService.getDownloadHistory();

        // Assert
        assertEquals("SUCCESS", result.getMessage());
        assertEquals(downloadHistoryList, result.getResult());
        verify(downloadHistoryRepository).findAll();
    }
}

