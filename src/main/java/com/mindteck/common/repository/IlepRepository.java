package com.mindteck.common.repository;

import com.mindteck.common.models.Ilep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IlepRepository extends JpaRepository<Ilep,Long> {
    List<Ilep> findByFormUniqueIdAndIsHistory(Long formUniqueId, Integer isHistory);

    Ilep findByFormUniqueIdAndIlepMemberId(Long formUniqueId, Long userId);

    List<Ilep> findByFormUniqueIdAndGrandAccess(Long formUniqueId , Integer grandAccess);
}
