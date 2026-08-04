package com.fixcity.fixcity.report.controller;

import com.fixcity.fixcity.report.service.ReportService;
import com.fixcity.fixcity.report.request.ReportCreateRequest;
import com.fixcity.fixcity.report.response.ReportResponse;
import com.fixcity.fixcity.report.request.ReportUpdateRequest;
import com.fixcity.fixcity.user.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    @PostMapping(path = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportResponse> createReport(@RequestPart("data") ReportCreateRequest data,
                                                       @RequestPart("file") MultipartFile file,
                                                       @AuthenticationPrincipal UserPrincipal principal){
        ReportResponse report = reportService.createReport(principal.id(), data, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    @GetMapping
    public ResponseEntity<List<ReportResponse>> getAllReports(@AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal != null ? principal.id() : null;
        List<ReportResponse> reports = reportService.getAllReports(userId);
        return ResponseEntity.status(HttpStatus.OK).body(reports);
    }

    @GetMapping("/municipal-admin")
    public ResponseEntity<List<ReportResponse>> reportsForMunicipality(@AuthenticationPrincipal UserPrincipal principal) {
        List<ReportResponse> reports = reportService.getReportsForMunicipality(principal.id());
        return ResponseEntity.status(HttpStatus.OK).body(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> getReportById(@PathVariable Long id,
                                                        @AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal != null ? principal.id() : null;
        ReportResponse report = reportService.getReportById(id, userId);
        return ResponseEntity.status(HttpStatus.OK).body(report);
    }

    @GetMapping("/by-username")
    public ResponseEntity<List<ReportResponse>> reportsByUsername(@RequestParam String username,
                                                                  @AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal != null ? principal.id() : null;
        List<ReportResponse> reports = reportService.findReportsByUsername(username, userId);
        return ResponseEntity.status(HttpStatus.OK).body(reports);
    }

    @GetMapping("/my-reports")
    public ResponseEntity<List<ReportResponse>> getMyReports(@AuthenticationPrincipal UserPrincipal principal) {
        Long id = principal.id();
        List<ReportResponse> myReports = reportService.getMyReports(id);

        return ResponseEntity.status(HttpStatus.OK).body(myReports);
    }

    @PatchMapping(
            path = "/update/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ReportResponse> updateReport(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestPart("data") ReportUpdateRequest req,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        Long userId = principal.id();
        ReportResponse report = reportService.updateReport(id, userId, req, file);

        return ResponseEntity.ok(report);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id,
                                             @AuthenticationPrincipal UserPrincipal principal) {
        reportService.deleteReport(id, principal.id());
        return ResponseEntity.noContent().build();
    }

}
