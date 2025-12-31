package com.mindteck.common.modules.standards.dao;

import com.mindteck.common.modules.standards.StandardWorkFlowManagerRepository;
import com.mindteck.common.modules.standards.model.StandardWorkFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StandardsWorkFlowDaoImpl implements StandardsWorkFlowDao {

    @Autowired
    private StandardWorkFlowManagerRepository standardWorkFlowRepo;
    @Override
    public List<StandardWorkFlow> getStandards(Long uniqueId, Integer isCf) {
        return standardWorkFlowRepo.getStandards(uniqueId, isCf);
    }
}
