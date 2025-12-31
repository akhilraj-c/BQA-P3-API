package com.mindteck.common.modules.revalidation;

import com.mindteck.common.modules.revalidation.model.MonitoringChangeForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoringChangeFormRepository  extends JpaRepository<MonitoringChangeForm, Long> {
    List<MonitoringChangeForm> findByFormUniqueId(Long formUniqueId);

}
