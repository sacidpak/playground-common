package com.sacidpak.playground.common.util;

import com.sacidpak.playground.common.dto.PagedApiRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {

    public static Pageable pageRequestToPageable(PagedApiRequest apiRequest) {
        return PageRequest.of(apiRequest.getPageNo(), apiRequest.getPageSize(), apiSortToDataSort(apiRequest));
    }

    private static Sort apiSortToDataSort(PagedApiRequest apiRequest) {
        if (apiRequest.isSortData()) {
            return Sort.by(Sort.Direction.valueOf(apiRequest.getSortDir().name()),
                    apiRequest.getSortBy());
        } else {
            return defaultDataSort();
        }
    }

    public static Sort defaultDataSort() {
        return Sort.by(Sort.Direction.DESC, "id");
    }
}
