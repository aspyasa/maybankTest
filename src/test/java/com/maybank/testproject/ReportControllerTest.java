package com.maybank.testproject;

import com.maybank.testproject.DTO.request.SearchDto;
import com.maybank.testproject.DTO.response.SuccessDto;
import com.maybank.testproject.controller.ReportController;
import com.maybank.testproject.models.DownloadHistory;
import com.maybank.testproject.models.ReportHistory;
import com.maybank.testproject.service.impl.ReportHistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ReportControllerTest {

    @Mock
    private ReportHistoryServiceImpl reportHistoryService;

    @InjectMocks
    private ReportController reportController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test export() method")
    void testExport() {
        SearchDto searchDto = new SearchDto();
        ReportHistory reportHistory = new ReportHistory();
        SuccessDto<ReportHistory> expectedResponse = new SuccessDto<>("Success", reportHistory);
        when(reportHistoryService.export(searchDto)).thenReturn(expectedResponse);

        SuccessDto<ReportHistory> response = reportController.export(searchDto);

        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Test getAll() method")
    void testGetAll() {
        List<ReportHistory> reportHistories = Arrays.asList(new ReportHistory(), new ReportHistory());
        SuccessDto<List<ReportHistory>> expectedResponse = new SuccessDto<>("Success", reportHistories);
        when(reportHistoryService.getAll()).thenReturn(expectedResponse);

        SuccessDto<List<ReportHistory>> response = reportController.getAll();

        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Test download() method")
    void testDownload() throws MalformedURLException {
        String key = "example-key";
        Resource resource = new ByteArrayResource("Test content".getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + resource.getFilename());
        ResponseEntity<Resource> expectedResponse = ResponseEntity.ok().headers(headers).body(resource);
        when(reportHistoryService.getDownloadReport(key)).thenReturn(resource);

        ResponseEntity<Resource> response = reportController.download(key);

        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Test getDownloadHistory() method")
    void testGetDownloadHistory() {
        List<DownloadHistory> downloadHistories = Arrays.asList(new DownloadHistory(), new DownloadHistory());
        SuccessDto<List<DownloadHistory>> expectedResponse = new SuccessDto<List<DownloadHistory>>("Success", downloadHistories);
        when(reportHistoryService.getDownloadHistory()).thenReturn(expectedResponse);

        SuccessDto<List<DownloadHistory>> response = reportController.getDownloadHistory();

        assertEquals(expectedResponse, response);
    }

}
