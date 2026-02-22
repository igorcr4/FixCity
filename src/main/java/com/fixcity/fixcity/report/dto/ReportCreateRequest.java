package com.fixcity.fixcity.report.dto;

public record ReportCreateRequest(String title,
                                  String description,
                                  Double latitude,
                                  Double longitude,
                                  Long userId) {}
