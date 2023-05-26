package com.maybank.testproject.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "report_history")
public class ReportHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "export_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime exportDate;
    @Column(name = "download_url")
    private String downloadUrl;

    @Column(name = "download_key")
    private String keyDownload;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportHistory that = (ReportHistory) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(exportDate, that.exportDate) &&
                Objects.equals(downloadUrl, that.downloadUrl) &&
                Objects.equals(keyDownload, that.keyDownload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, exportDate, downloadUrl, keyDownload);
    }

}
