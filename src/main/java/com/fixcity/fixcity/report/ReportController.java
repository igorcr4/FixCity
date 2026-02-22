package com.fixcity.fixcity.report;

import com.fixcity.fixcity.report.dto.ReportCreateRequest;
import com.fixcity.fixcity.report.dto.ReportResponse;
import com.fixcity.fixcity.report.dto.ReportUpdateReq;
import com.fixcity.fixcity.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping(path = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportResponse> createReport(@RequestPart("data") ReportCreateRequest data,
                                                       @RequestPart("file") MultipartFile file) {
        ReportResponse report = reportService.createReport(data, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    @GetMapping("/get")
    public ResponseEntity<List<ReportResponse>> getAllReports() {
        List<ReportResponse> reports = reportService.getAllReports();
        return ResponseEntity.status(HttpStatus.OK).body(reports);
    }

    @GetMapping("/by-username")
    public ResponseEntity<List<ReportResponse>> reportsByUsername(@RequestParam String username) {
        List<ReportResponse> reports = reportService.findReportsByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(reports);
    }

    @GetMapping("/my-reports")
    public ResponseEntity<List<ReportResponse>> getMyReports(@AuthenticationPrincipal UserPrincipal principal) {
        Long id = principal.id();
        List<ReportResponse> myReports = reportService.getMyReports(id);

        return ResponseEntity.status(HttpStatus.OK).body(myReports);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ReportResponse> updateReport(@PathVariable Long id,
                                                       @AuthenticationPrincipal UserPrincipal principal,
                                                       @RequestBody ReportUpdateReq req) {
        Long userId = principal.id();
        ReportResponse report = reportService.updateReport(id, userId, req);

        return ResponseEntity.status(HttpStatus.OK).body(report);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
        reportService.deleteReport(id, principal.id());
        return ResponseEntity.noContent().build();
    }

}
