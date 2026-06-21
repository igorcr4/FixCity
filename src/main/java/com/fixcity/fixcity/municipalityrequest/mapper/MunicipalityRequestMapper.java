package com.fixcity.fixcity.municipalityrequest.mapper;

import com.fixcity.fixcity.municipalityrequest.model.MunicipalityRequest;
import com.fixcity.fixcity.municipalityrequest.response.RequestResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MunicipalityRequestMapper {

    RequestResponse toResponse(MunicipalityRequest request);
}
