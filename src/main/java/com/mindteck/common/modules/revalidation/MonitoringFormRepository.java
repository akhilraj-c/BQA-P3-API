package com.mindteck.common.modules.revalidation;

import com.mindteck.common.modules.revalidation.model.MonitoringForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringFormRepository  extends JpaRepository<MonitoringForm, Long> {
    MonitoringForm getByFormUniqueId(Long formUniqueId);
}
