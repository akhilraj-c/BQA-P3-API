package com.mindteck.common.dao.daoImpl;

import com.mindteck.common.dao.LogDao;
import com.mindteck.common.models.Log;
import com.mindteck.common.models.rest.GetLogResponseModel;
import com.mindteck.common.models.rest.PagedData;
import com.mindteck.common.repository.LogRepository;
import com.mindteck.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class LogDaoImpl implements LogDao {
    @Autowired
    private LogRepository logRepository;
    @Override
    public PagedData<GetLogResponseModel> getLog(Long formUniqueId ,Integer page ,Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit , Sort.by("lastUpdatedTime").ascending());
        Page<GetLogResponseModel> responseModelPage = logRepository.getLogByFormUniqueId(formUniqueId,pageable);
        return PageUtils.setPageResponse(responseModelPage);
    }

    @Override
    public Log save(Log log) {
        return logRepository.save(log);
    }
}
