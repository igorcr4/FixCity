package com.fixcity.fixcity.confirmation.repository;

import com.fixcity.fixcity.confirmation.ReportCountProjection;
import com.fixcity.fixcity.confirmation.model.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
    Optional<Confirmation> findByReportIdAndUserId(Long reportId, Long userId);

    int countByReportId(Long reportId);

    @Query("SELECT c.report.id AS reportId, COUNT(c) AS cnt " +
            "FROM Confirmation c WHERE c.report.id IN :reportIds " +
            "GROUP BY c.report.id")
    List<ReportCountProjection> countByReportIds(@Param("reportIds") List<Long> reportIds);

    @Query("SELECT c.report.id FROM Confirmation c " +
            "WHERE c.user.id = :userId AND c.report.id IN :reportIds")
    List<Long> findConfirmedReportIds(@Param("userId") Long userId,
                                      @Param("reportIds") List<Long> reportIds);

}
