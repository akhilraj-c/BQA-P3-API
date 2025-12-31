package com.mindteck.common.utils;

import com.mindteck.common.models.rest.PageDetails;
import com.mindteck.common.models.rest.PagedData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PageUtils {

    public static <T> PagedData<T> setPageResponse(Page<T> pageInfo) {
        long remainCount = pageInfo.getTotalElements() - ((long) (pageInfo.getNumber() + 1) * pageInfo.getSize());
        if (remainCount < 0) {
            remainCount = 0L;
        }
        PagedData<T> pagedData = new PagedData<>();
        pagedData.setList(pageInfo.getContent());
        PageDetails pageDetails = new PageDetails();
        pageDetails.setPage(pageInfo.getNumber() + 1);
        pageDetails.setPageSize(pageInfo.getSize());
        pageDetails.setPageCount(pageInfo.getTotalPages());
        pageDetails.setTotalElements(pageInfo.getTotalElements());
        pageDetails.setRemainingElements(remainCount);
        pagedData.setPageDetails(pageDetails);
        return pagedData;
    }
}
