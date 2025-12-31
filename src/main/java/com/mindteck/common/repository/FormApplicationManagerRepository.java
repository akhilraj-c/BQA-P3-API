package com.mindteck.common.repository;

import com.mindteck.common.models.FormApplicationManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormApplicationManagerRepository extends JpaRepository<FormApplicationManager, Long> {

    FormApplicationManager getByFormUniqueId(Long formUniqueId);
}
