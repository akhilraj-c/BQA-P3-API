package com.mindteck.common.repository;

import com.mindteck.common.models.DueCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DueCountRepository  extends JpaRepository<DueCount , Long> {

    DueCount findByFormUniqueIdAndStatus(Long formUniqueId , Integer status);
}
