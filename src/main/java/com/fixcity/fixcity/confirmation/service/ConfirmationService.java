package com.fixcity.fixcity.confirmation.service;

import com.fixcity.fixcity.confirmation.model.Confirmation;
import com.fixcity.fixcity.confirmation.repository.ConfirmationRepository;
import com.fixcity.fixcity.confirmation.response.ConfirmationResponse;
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
public class ConfirmationService {
    private final ConfirmationRepository repository;
    private final ReportService reportService;
    private final UserService userService;

    @Transactional
    public ConfirmationResponse toggleConfirmation(Long reportId, Long userId) {
        Report report = reportService.findReportById(reportId);

        if(report.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException();//exceptie personalizata
        }

        if(report.getStatus() == Status.RESOLVED) {
            throw new IllegalArgumentException();//exceptie personalizata
        }

        User user = userService.getReference(userId);

        Optional<Confirmation> existing = repository.findByReportIdAndUserId(reportId, userId);

        boolean confirmed;

        if(existing.isPresent()) {
            repository.delete(existing.get());
            confirmed = false;
        } else {
            Confirmation confirmation = new Confirmation();
            confirmation.setReport(report);
            confirmation.setUser(user);
            repository.save(confirmation);
            confirmed = true;
        }

        int count = repository.countByReportId(reportId);

        return new ConfirmationResponse(confirmed, count);
    }

}
