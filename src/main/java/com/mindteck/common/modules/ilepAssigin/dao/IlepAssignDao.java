package com.mindteck.common.modules.ilepAssigin.dao;

import com.mindteck.common.models.Ilep;

import java.util.List;


public interface IlepAssignDao {

    List<Ilep> findByFormUniqueIdAndIsHistory(Long formUniqueId, Integer isHistory);

    Ilep save(Ilep ilep);

    List<Ilep> saveAll(List<Ilep> ilepList);

    Ilep findByFormUniqueIdAndUserId(Long formUniqueId , Long userId);


    void delete(Ilep ilep);

    List<Ilep> findByFormUniqueIdAndGrandAccess(Long formUniqueId , Integer grandAccess);
}
