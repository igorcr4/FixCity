package com.fixcity.fixcity.reportconfirmation.service;

import com.fixcity.fixcity.reportconfirmation.exception.CannotConfirmOwnReportException;
import com.fixcity.fixcity.reportconfirmation.exception.ReportAlreadyResolvedException;
import com.fixcity.fixcity.reportconfirmation.model.ReportConfirmation;
import com.fixcity.fixcity.reportconfirmation.repository.ReportConfirmationRepository;
import com.fixcity.fixcity.reportconfirmation.response.ReportConfirmationResponse;
import com.fixcity.fixcity.report.Status;
import com.fixcity.fixcity.report.model.Report;
import com.fixcity.fixcity.report.service.ReportService;
import com.fixcity.fixcity.user.model.User;
import com.fixcity.fixcity.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportConfirmationService {
    private final ReportConfirmationRepository repository;
    private final ReportService reportService;
    private final UserService userService;

    @Transactional
    public ReportConfirmationResponse toggleConfirmation(Long reportId, Long userId) {
        Report report = reportService.findReportById(reportId);

        if(report.getUser().getId().equals(userId)) {
            throw new CannotConfirmOwnReportException();
        }

        if(report.getStatus() == Status.RESOLVED) {
            throw new ReportAlreadyResolvedException();
        }

        User user = userService.getReference(userId);

        Optional<ReportConfirmation> existing = repository.findByReportIdAndUserId(reportId, userId);

        boolean confirmed;

        if(existing.isPresent()) {
            repository.delete(existing.get());
            confirmed = false;
        } else {
            ReportConfirmation confirmation = new ReportConfirmation();
            confirmation.setReport(report);
            confirmation.setUser(user);
            repository.save(confirmation);
            confirmed = true;
        }

        int count = repository.countByReportId(reportId);

        return new ReportConfirmationResponse(confirmed, count);
    }

}
