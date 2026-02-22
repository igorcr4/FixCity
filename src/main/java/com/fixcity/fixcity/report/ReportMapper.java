package com.fixcity.fixcity.report;

import com.fixcity.fixcity.report.dto.ReportCreateRequest;
import com.fixcity.fixcity.report.dto.ReportResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Report toEntity(ReportCreateRequest req);

    @Mapping(target = "status", expression = "java(report.getStatus().name())")
    @Mapping(target = "userId", source = "user.id")
    ReportResponse toResponse(Report report);
}
