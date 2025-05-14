package br.com.wareysis.sigbrb.dto.endpoint;

import java.util.List;

import org.springframework.data.domain.Page;

public record PagedResponse<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        int currentPage,
        int pageSize,
        boolean last,
        boolean first
) {

    public PagedResponse(Page<T> page) {

        this(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.isLast(),
                page.isFirst()
        );
    }
}

