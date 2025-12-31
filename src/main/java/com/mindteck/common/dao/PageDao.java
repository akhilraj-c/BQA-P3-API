package com.mindteck.common.dao;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PageDao {

    <V> Page<V> getPageFromNativeQuery(String listQueryStr, String countQueryStr, int page, int pageSize, Class<V> viewClass,
                                       List<String> resultFieldNames, Map<String, Object> filterMap);
}