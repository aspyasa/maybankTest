package com.maybank.testproject.DTO.response;

import com.maybank.testproject.models.ReportHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessDto<T> {
    private T result;
    private String message;

    public SuccessDto(String success, ReportHistory reportHistory) {
        this.result = (T) reportHistory;
    }

    public SuccessDto(String success, List<ReportHistory> reportHistories) {
        this.result = (T) reportHistories;
    }

    public SuccessDto(String success, T downloadHistories) {
        this.result = downloadHistories;
    }
}
