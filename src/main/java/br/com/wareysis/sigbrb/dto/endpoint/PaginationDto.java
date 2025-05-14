package br.com.wareysis.sigbrb.dto.endpoint;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record PaginationDto(
        Integer page,
        Integer size,
        String sortBy,
        String direction
) implements Serializable {

    public PaginationDto {

        if (page == null || page < 0) {
            page = 0;
        }
        if (size == null || size <= 0) {
            size = 10;
        }
    }

    public Pageable toPageable() {

        if (sortBy != null && !sortBy.isBlank()) {

            Sort sort = "desc".equalsIgnoreCase(direction)
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();

            return PageRequest.of(page, size, sort);
        }

        return PageRequest.of(page, size);
    }
}

