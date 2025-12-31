package com.mindteck.common.modules.standards.dao;

import com.mindteck.common.modules.standards.model.StandardWorkFlow;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandardsWorkFlowDao {

    List<StandardWorkFlow> getStandards(Long uniqueId, Integer isCf);

}
