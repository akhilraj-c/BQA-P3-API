package com.mindteck.common.dao;

import com.mindteck.common.models.Log;
import com.mindteck.common.models.rest.GetLogResponseModel;
import com.mindteck.common.models.rest.PagedData;

public interface LogDao {

    PagedData<GetLogResponseModel> getLog(Long formUniqueId ,Integer page ,Integer limit);

    Log save(Log log);
}
