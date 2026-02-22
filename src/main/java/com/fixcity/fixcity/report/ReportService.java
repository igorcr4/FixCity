package com.fixcity.fixcity.report;

import com.fixcity.fixcity.media.ImageUploadService;
import com.fixcity.fixcity.report.dto.ReportCreateRequest;
import com.fixcity.fixcity.report.dto.ReportResponse;
import com.fixcity.fixcity.report.dto.ReportUpdateReq;
import com.fixcity.fixcity.report.exception.ModifyReportException;
import com.fixcity.fixcity.report.exception.ReportNotFoundException;
import com.fixcity.fixcity.user.User;
import com.fixcity.fixcity.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final ImageUploadService imageUploadService;
    private final ReportMapper reportMapper;
    private final UserService userService;

    public ReportResponse createReport(ReportCreateRequest req, MultipartFile file) {

        User user = userService.findById(req.userId());

        String imageUrl = imageUploadService.uploadImage(file);

        Report report = reportMapper.toEntity(req);
        report.setUser(user);
        report.setImageUrl(imageUrl);

        reportRepository.save(report);
        return reportMapper.toResponse(report);
    }

    @Transactional
    public List<ReportResponse> getAllReports() {
        return reportRepository.findAll().stream().map(
                reportMapper::toResponse
        ).toList();
    }

    @Transactional
    public List<ReportResponse> findReportsByUsername(String username) {
        User user = userService.findByUsername(username);

        return user.getReports().stream().map(
                reportMapper::toResponse
        ).toList();
    }

    @Transactional
    public List<ReportResponse> getMyReports(Long userId) {
        User user = userService.findById(userId);

        return user.getReports().stream().map(
                reportMapper::toResponse
        ).toList();
    }

    @Transactional
    public ReportResponse updateReport(Long reportId, Long userId, ReportUpdateReq req) {
        Report report = reportRepository.findById(reportId).orElseThrow(ReportNotFoundException::new);

        if(!report.getUser().getId().equals(userId)) {
            throw new ModifyReportException();
        }

        if(req.title() != null) {
            report.setTitle(req.title());
        }

        if(req.imageUrl() != null) {
            report.setImageUrl(req.imageUrl());
        }

        if(req.description() != null) {
            report.setDescription(req.description());
        }

        if(req.status() != null) {
            report.setStatus(req.status());
        }

        report.setUpdatedAt(LocalDateTime.now());

        return reportMapper.toResponse(report);
    }

    @Transactional
    public void deleteReport(Long reportId, Long userId) {
        Report report = reportRepository.findById(reportId).orElseThrow(ReportNotFoundException::new);

        if(!report.getUser().getId().equals(userId)){
            throw new ModifyReportException();
        }

        reportRepository.delete(report);
    }
}
