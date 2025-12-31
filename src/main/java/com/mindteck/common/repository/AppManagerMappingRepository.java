package com.mindteck.common.repository;

import com.mindteck.common.models.AppManagerMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppManagerMappingRepository extends JpaRepository<AppManagerMapping, Long> {

    AppManagerMapping getByFormUniqueIdAndUserId(Long formUniqueId, Long userId);

    AppManagerMapping getByFormUniqueId(Long formUniqueId);
}
