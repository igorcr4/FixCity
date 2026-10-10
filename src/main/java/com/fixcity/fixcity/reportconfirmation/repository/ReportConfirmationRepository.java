package com.fixcity.fixcity.reportconfirmation.repository;

import com.fixcity.fixcity.reportconfirmation.ReportCountProjection;
import com.fixcity.fixcity.reportconfirmation.model.ReportConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportConfirmationRepository extends JpaRepository<ReportConfirmation, Long> {
    Optional<ReportConfirmation> findByReportIdAndUserId(Long reportId, Long userId);

    int countByReportId(Long reportId);

    @Query("SELECT c.report.id AS reportId, COUNT(c) AS cnt " +
            "FROM ReportConfirmation c WHERE c.report.id IN :reportIds " +
            "GROUP BY c.report.id")
    List<ReportCountProjection> countByReportIds(@Param("reportIds") List<Long> reportIds);

    @Query("SELECT c.report.id FROM ReportConfirmation c " +
            "WHERE c.user.id = :userId AND c.report.id IN :reportIds")
    List<Long> findConfirmedReportIds(@Param("userId") Long userId,
                                      @Param("reportIds") List<Long> reportIds);

}
