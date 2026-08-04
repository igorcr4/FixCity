package com.fixcity.fixcity.municipalityrequest.service;

import com.fixcity.fixcity.municipalityrequest.mapper.MunicipalityRequestMapper;
import com.fixcity.fixcity.municipalityrequest.model.MunicipalityRequest;
import com.fixcity.fixcity.municipalityrequest.onboarding.MunicipalityRequestSubmission;
import com.fixcity.fixcity.municipalityrequest.repository.RequestRepository;
import com.fixcity.fixcity.municipalityrequest.response.RequestResponse;
import com.fixcity.fixcity.municipalityrequest.status.RequestStatus;
import com.fixcity.fixcity.user.admin.service.AdminService;
import com.fixcity.fixcity.user.model.User;
import com.fixcity.fixcity.user.service.UserService;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository repository;
    private final UserService userService;
    private final MunicipalityRequestMapper mapper;
    private final AdminService adminService;

    public RequestResponse createRequest(Long userId, MunicipalityRequestSubmission submission) {

        boolean hasRequest = repository.existsByUser_IdAndStatus(userId, RequestStatus.PENDING);

        if(hasRequest) {
            throw new InvalidRequestStateException();//exceptie personalizata
        }

        User user = userService.findById(userId);

        MunicipalityRequest municipalityRequest = new MunicipalityRequest();
        municipalityRequest.setUser(user);
        municipalityRequest.setStatus(RequestStatus.PENDING);
        municipalityRequest.setInstitutionName(submission.institutionName());
        municipalityRequest.setEmployeePosition(submission.employeePosition());
        municipalityRequest.setJustification(submission.justification());

        repository.save(municipalityRequest);

        return mapper.toResponse(municipalityRequest);

    }

    @Transactional
    public void approveRequest(Long requestId, String country, String state, String city) {
        MunicipalityRequest request = repository.findById(requestId).orElseThrow();//exceptie personalizata

        if(!request.getStatus().equals(RequestStatus.PENDING)) {
            throw new IllegalArgumentException();//exceptie personalizata
        }
        Long userId = request.getUser().getId();

        adminService.promoteToMunicipalAdmin(userId, country, state, city);
        request.setStatus(RequestStatus.APPROVED);
        request.setReviewedAt(LocalDateTime.now());
    }

    @Transactional
    public void rejectRequest(Long requestId) {
        MunicipalityRequest request = repository.findById(requestId).orElseThrow();//exceptie personalizata

        if(!request.getStatus().equals(RequestStatus.PENDING)) {
            throw new InvalidRequestStateException();//exceptie personalizata
        }

        request.setStatus(RequestStatus.REJECTED);
        request.setReviewedAt(LocalDateTime.now());
    }

    public List<RequestResponse> getPendingRequests() {
        return repository.findByStatus(RequestStatus.PENDING).stream()
                .map(mapper::toResponse)
                .toList();
    }
}
