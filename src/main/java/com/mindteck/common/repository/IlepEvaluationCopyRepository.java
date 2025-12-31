package com.mindteck.common.repository;

import com.mindteck.common.models.IlepEvaluationReportCopy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IlepEvaluationCopyRepository extends JpaRepository<IlepEvaluationReportCopy,Long> {

    IlepEvaluationReportCopy getByFormUniqueId(Long formUniqueId);
}