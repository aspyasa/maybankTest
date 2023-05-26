package com.maybank.testproject.repositories;

import com.maybank.testproject.models.ReportHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ReportHistoryRepository extends JpaRepository<ReportHistory, Long> {
    @Query("select r from ReportHistory r where r.keyDownload = ?1")
    Optional<ReportHistory>  findByFileName(String fileName);
}
