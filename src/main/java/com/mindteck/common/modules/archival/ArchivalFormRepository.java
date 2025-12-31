package com.mindteck.common.modules.archival;

import com.mindteck.common.modules.archival.model.ArchivalForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivalFormRepository extends JpaRepository<ArchivalForm, Long> {
    ArchivalForm getById(Long id);
    ArchivalForm getByFormUniqueId(Long formUniqueId);
}
