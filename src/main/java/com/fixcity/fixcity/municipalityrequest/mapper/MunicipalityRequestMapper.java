package com.fixcity.fixcity.municipalityrequest.mapper;

import com.fixcity.fixcity.municipalityrequest.model.MunicipalityRequest;
import com.fixcity.fixcity.municipalityrequest.response.RequestResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MunicipalityRequestMapper {

    @Mapping(target = "username", source = "user.username")
    RequestResponse toResponse(MunicipalityRequest request);
}
