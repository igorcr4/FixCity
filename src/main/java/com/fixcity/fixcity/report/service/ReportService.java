package com.fixcity.fixcity.report.service;

import com.fixcity.fixcity.media.ImageUploadService;
import com.fixcity.fixcity.municipality.model.Municipality;
import com.fixcity.fixcity.municipality.service.MunicipalityService;
import com.fixcity.fixcity.report.model.Report;
import com.fixcity.fixcity.report.repository.ReportRepository;
import com.fixcity.fixcity.report.request.ReportCreateRequest;
import com.fixcity.fixcity.report.response.ReportResponse;
import com.fixcity.fixcity.report.request.ReportUpdateRequest;
import com.fixcity.fixcity.report.exception.ModifyReportException;
import com.fixcity.fixcity.report.exception.ReportNotFoundException;
import com.fixcity.fixcity.report.mapper.ReportMapper;
import com.fixcity.fixcity.user.role.Role;
import com.fixcity.fixcity.user.model.User;
import com.fixcity.fixcity.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository repository;
    private final ImageUploadService imageUploadService;
    private final ReportMapper reportMapper;
    private final UserService userService;
    private final MunicipalityService municipalityService;

    public ReportResponse createReport(Long userId, ReportCreateRequest request, MultipartFile file) {

        User user = userService.findById(userId);

        Municipality municipality = municipalityService.findOrCreateMunicipality(
                request.country(),
                request.state(),
                request.city()
        );

        String imageUrl = imageUploadService.uploadImage(file);

        Report report = reportMapper.toEntity(request);
        report.setUser(user);
        report.setImageUrl(imageUrl);
        report.setMunicipality(municipality);

        repository.save(report);
        return reportMapper.toResponse(report);
    }

    @Transactional
    public List<ReportResponse> getAllReports() {
        return repository.findAll().stream().map(
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
    public Report findReportById(Long reportId) {
        return repository.findById(reportId).orElseThrow(ReportNotFoundException::new);
    }

    @Transactional
    public ReportResponse getReportById(Long reportId) {
        Report report = repository.findById(reportId).orElseThrow(() -> new RuntimeException("Nu a fost găsit"));//exceptie personalizata
        return reportMapper.toResponse(report);
    }

    @Transactional
    public List<ReportResponse> getMyReports(Long userId) {
        User user = userService.findById(userId);

        return user.getReports().stream().map(
                reportMapper::toResponse
        ).toList();
    }

    @Transactional
    public ReportResponse updateReport(Long reportId, Long userId, ReportUpdateRequest req, MultipartFile file) {
        Report report = repository.findById(reportId).orElseThrow(ReportNotFoundException::new);

        User user = userService.findById(userId);

        boolean isOwner = report.getUser().getId().equals(userId);

        boolean isMunicipalAdminForThisReport =
                user.getRole().equals(Role.ROLE_MUNICIPAL_ADMIN)
                        && user.getMunicipality() != null
                        && report.getMunicipality() != null
                        && user.getMunicipality().getId().equals(report.getMunicipality().getId());

        if (!isOwner && !isMunicipalAdminForThisReport) {
            throw new ModifyReportException();
        }

        if(req.title() != null && isOwner) {
            report.setTitle(req.title());
        }

        if(req.description() != null && isOwner) {
            report.setDescription(req.description());
        }

        if(req.status() != null) {
            report.setStatus(req.status());
        }

        if (req.address() != null) {
            report.setAddress(req.address());
        }

        if(req.latitude() != null) {
            report.setLatitude(req.latitude());
        }

        if(req.longitude() != null) {
            report.setLongitude(req.longitude());
        }

        if(req.category() != null) {
            report.setCategory(req.category());
        }

        if (file != null && !file.isEmpty()) {
            String imageUrl = imageUploadService.uploadImage(file);
            report.setImageUrl(imageUrl);
        }

        report.setUpdatedAt(LocalDateTime.now());

        return reportMapper.toResponse(report);
    }

    @Transactional
    public void deleteReport(Long reportId, Long userId) {
        Report report = repository.findById(reportId).orElseThrow(ReportNotFoundException::new);

        if(!report.getUser().getId().equals(userId)){
            throw new ModifyReportException();
        }

        repository.delete(report);
    }

    @Transactional(readOnly = true)
    public List<ReportResponse> getReportsForMunicipality(Long userId) {
        User user = userService.findById(userId);

        if (user.getRole() != Role.ROLE_MUNICIPAL_ADMIN) {
            throw new AccessDeniedException("User-ul nu are drepturi de admin!");
        }

        if (user.getMunicipality() == null) {
            throw new IllegalStateException("User-ul nu are o primarie asociata!");
        }

        Long municipalityId = user.getMunicipality().getId();
        Municipality municipality = municipalityService.findById(municipalityId);

        return municipality.getReports()
                .stream()
                .map(reportMapper::toResponse)
                .toList();
    }
}
