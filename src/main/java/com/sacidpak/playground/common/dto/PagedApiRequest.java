package com.sacidpak.playground.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PagedApiRequest {
    private int pageNo = 0;
    private int pageSize = 10;
    private boolean sortData;
    private String sortBy;
    private Sort.Direction sortDir;
}
