package com.fixcity.fixcity.municipalityrequest.repository;

import com.fixcity.fixcity.municipalityrequest.status.RequestStatus;
import com.fixcity.fixcity.municipalityrequest.model.MunicipalityRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<MunicipalityRequest, Long> {

    List<MunicipalityRequest> findByStatus(RequestStatus status);

    boolean existsByUser_IdAndStatus(Long userId, RequestStatus status);

}
