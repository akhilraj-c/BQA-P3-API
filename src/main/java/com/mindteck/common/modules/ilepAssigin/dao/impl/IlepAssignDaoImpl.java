package com.mindteck.common.modules.ilepAssigin.dao.impl;

import com.mindteck.common.models.Ilep;
import com.mindteck.common.repository.IlepRepository;
import com.mindteck.common.modules.ilepAssigin.dao.IlepAssignDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IlepAssignDaoImpl implements IlepAssignDao {

    @Autowired
    IlepRepository ilepRepository;
    @Override
    public List<Ilep> findByFormUniqueIdAndIsHistory(Long formUniqueId, Integer isHistory) {
        return ilepRepository.findByFormUniqueIdAndIsHistory(formUniqueId,isHistory );
    }

    @Override
    public Ilep save(Ilep ilep) {
        return ilepRepository.save(ilep);
    }

    @Override
    public List<Ilep> saveAll(List<Ilep> ilepList) {
        return ilepRepository.saveAll(ilepList);
    }

    @Override
    public Ilep findByFormUniqueIdAndUserId(Long formUniqueId, Long userId) {
        return ilepRepository.findByFormUniqueIdAndIlepMemberId(formUniqueId , userId);
    }

    @Override
    public void delete(Ilep ilep) {
      ilepRepository.delete(ilep);
    }

    @Override
    public List<Ilep> findByFormUniqueIdAndGrandAccess(Long formUniqueId, Integer grandAccess) {
        return ilepRepository.findByFormUniqueIdAndGrandAccess(formUniqueId,grandAccess);
    }

}
