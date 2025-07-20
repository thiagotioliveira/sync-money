package dev.thiagooliveira.syncmoney.application.support.page.domain.dto;

import java.util.List;

public record Page<T>(
    List<T> content,
    int pageNumber,
    int pageSize,
    long totalElements,
    int totalPages,
    boolean first,
    boolean last) {}
