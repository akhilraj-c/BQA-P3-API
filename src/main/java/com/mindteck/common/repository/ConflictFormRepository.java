package com.mindteck.common.repository;

import com.mindteck.common.models.ConflictForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConflictFormRepository extends JpaRepository<ConflictForm, Long> {

    ConflictForm findByFormUniqueIdAndIsHistory(Long formUniqueId, Integer isHistory);

    ConflictForm getByFormUniqueId(Long formUniqueId);
}
