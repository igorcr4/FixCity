package com.fixcity.fixcity.report.service;

import com.fixcity.fixcity.confirmation.ReportCountProjection;
import com.fixcity.fixcity.confirmation.repository.ConfirmationRepository;
import com.fixcity.fixcity.media.ImageUploadService;
import com.fixcity.fixcity.municipality.model.Municipality;
import com.fixcity.fixcity.municipality.service.MunicipalityService;
import com.fixcity.fixcity.report.Status;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository repository;
    private final ImageUploadService imageUploadService;
    private final ReportMapper reportMapper;
    private final UserService userService;
    private final MunicipalityService municipalityService;
    private final ConfirmationRepository confirmationRepository;

    public ReportResponse createReport(Long userId, ReportCreateRequest request, MultipartFile file) {

        User user = userService.findById(userId);

        Municipality municipality = municipalityService.findOrCreateMunicipality(
                request.countryIso2(),
                request.stateIso2(),
                request.cityName(),
                request.stateName(),
                request.countryName()
        );

        String imageUrl = imageUploadService.uploadImage(file);

        Report report = reportMapper.toEntity(request);
        report.setUser(user);
        report.setImageUrl(imageUrl);
        report.setMunicipality(municipality);

        repository.save(report);
        return toEnrichedResponses(List.of(report), userId).getFirst();
    }

    @Transactional(readOnly = true)
    public List<ReportResponse> getAllReports(Long currentUserId) {
        return toEnrichedResponses(repository.findAll(), currentUserId);
    }

    @Transactional(readOnly = true)
    public List<ReportResponse> findReportsByUsername(String username, Long currentUserId) {
        User user = userService.findByUsername(username);

        return toEnrichedResponses(new ArrayList<>(user.getReports()), currentUserId);
    }

    @Transactional(readOnly = true)
    public Report findReportById(Long reportId) {
        return repository.findById(reportId).orElseThrow(ReportNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public ReportResponse getReportById(Long reportId, Long currentUserId) {
        Report report = repository.findById(reportId).orElseThrow(() -> new RuntimeException("Nu a fost găsit"));//exceptie personalizata
        return toEnrichedResponses(List.of(report), currentUserId).get(0);
    }

    @Transactional(readOnly = true)
    public List<ReportResponse> getMyReports(Long userId) {
        User user = userService.findById(userId);

        return toEnrichedResponses(new ArrayList<>(user.getReports()), userId);
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

        if (req.status() != null && isMunicipalAdminForThisReport) {
            Status previousStatus = report.getStatus();
            report.setStatus(req.status());

            if (previousStatus != Status.RESOLVED && req.status() == Status.RESOLVED) {
                report.setResolvedAt(LocalDateTime.now());
            }
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

        if (file != null && !file.isEmpty() && isMunicipalAdminForThisReport) {
            String afterImageUrl = imageUploadService.uploadImage(file);
            report.setAfterImageUrl(afterImageUrl);

        } else if (file != null && !file.isEmpty()){
            String imageUrl = imageUploadService.uploadImage(file);
            report.setImageUrl(imageUrl);
        }

        report.setUpdatedAt(LocalDateTime.now());

        return toEnrichedResponses(List.of(report), userId).getFirst();
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

        return toEnrichedResponses(new ArrayList<>(municipality.getReports()), userId);
    }

    private Map<Long, Long> getConfirmationCounts(List<Long> reportIds) {
        if (reportIds.isEmpty()) return Map.of();
        return confirmationRepository.countByReportIds(reportIds).stream()
                .collect(Collectors.toMap(
                        ReportCountProjection::getReportId,
                        ReportCountProjection::getCnt));
    }

    private Set<Long> getConfirmedReportIds(Long userId, List<Long> reportIds) {
        if (userId == null || reportIds.isEmpty()) return Set.of();
        return new HashSet<>(
                confirmationRepository.findConfirmedReportIds(userId, reportIds));
    }

    private List<ReportResponse> toEnrichedResponses(List<Report> reports, Long currentUserId) {
        List<Long> ids = reports.stream().map(Report::getId).toList();

        Map<Long, Long> counts = getConfirmationCounts(ids);
        Set<Long> confirmed = getConfirmedReportIds(currentUserId, ids);

        return reports.stream()
                .map(report -> {
                    ReportResponse base = reportMapper.toResponse(report);
                    long count = counts.getOrDefault(report.getId(), 0L);
                    boolean isConfirmed = confirmed.contains(report.getId());
                    return base.withConfirmations(count, isConfirmed);
                })
                .toList();
    }

}
