package com.mindteck.common.repository;

import com.mindteck.common.models.FormApplicationManager;
import com.mindteck.common.models.ILEPEvaluationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILEPEvaluationFormRepository extends JpaRepository<ILEPEvaluationForm, Long> {
    ILEPEvaluationForm getByFormUniqueId(Long formUniqueId);

}
