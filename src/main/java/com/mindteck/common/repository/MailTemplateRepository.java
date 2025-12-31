package com.mindteck.common.repository;

import com.mindteck.common.models.MailTemplate;
import com.mindteck.common.modules.user.model.rest.GetMailTemplateListResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long> {

    MailTemplate getByTemplateCodeAndIsActive(String templateCode, Integer isActive);

    MailTemplate getById(Long templateId);


    @Query(
            value = "SELECT new com.mindteck.common.modules.user.model.rest.GetMailTemplateListResponseModel" +
                    " ( " +
                    " m.id, " +
                    " m.templateName, " +
                    " m.templateDescription, " +
                    " m.templateBody," +
                    " m.templateSubject," +
                    " m.templateCode" +
                    " ) " +
                    " FROM MailTemplate m WHERE m.isActive=1 ORDER BY m.displaySequence ASC",
            countQuery = "SELECT COUNT(*) FROM MailTemplate m"
    )
    Page<GetMailTemplateListResponseModel> getTemplateList(Pageable pageable);
}
