package com.maybank.testproject.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybank.testproject.DTO.response.github.ItemDTO;
import com.maybank.testproject.DTO.response.github.SearchResultDTO;
import com.maybank.testproject.exception.GlobalException;
import com.maybank.testproject.service.JasperService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

@Service
public class JasperServiceImpl implements JasperService {
    @Value("${report.folder}")
    private String reportFolder;

    @Override
    public String generateReport(SearchResultDTO dtoList, String name) {
        try {
            JasperPrint jasperPrint = generateJasperPrint(dtoList.getItems());
            byte[] pdfBytes = exportToPdf(jasperPrint);
            String fileName = name + ".pdf";
            String filePath = reportFolder + fileName;
            saveReportToFile(pdfBytes, filePath);

            return fileName;
        } catch (Exception e) {
            throw new GlobalException("FAILED", "Failed to generate report");
        }
    }

    @Override
    public Resource downloadReport(String id) throws MalformedURLException {
        String uploadDir = StringUtils.cleanPath(reportFolder);
        String uploadPath = Paths.get(uploadDir, id ).toString();
        File reportFile = new File(uploadPath);
        if (!reportFile.exists()) {
            throw new GlobalException("FAILED", "Report not found");
        }
        return new UrlResource(reportFile.toURI());
    }

    private void saveReportToFile(byte[] pdfBytes, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(pdfBytes);
        }
    }
    private JasperPrint generateJasperPrint(List<ItemDTO> dtoList) throws JRException, JsonProcessingException {
        InputStream reportStream = this.getClass().getResourceAsStream("/templates/reporting.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dtoList);
        List<Object> dataList = objectMapper.readValue(json, new TypeReference<List<Object>>(){});
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        return JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);
    }

    private byte[] exportToPdf(JasperPrint jasperPrint) throws JRException {
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

}
