package com.mindteck.common.modules.user.dao.impl;

import com.mindteck.common.models.MailTemplate;
import com.mindteck.common.models.rest.PagedData;
import com.mindteck.common.modules.user.model.rest.GetMailTemplateListResponseModel;
import com.mindteck.common.repository.MailTemplateRepository;
import com.mindteck.common.utils.PageUtils;
import com.mindteck.common.modules.user.dao.MailTemplateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class MailTemplateDaoImpl implements MailTemplateDao {

    @Autowired
    private MailTemplateRepository mailTemplateRepository;


    @Override
    public MailTemplate getByTemplateCode(String templateCode) {
        return mailTemplateRepository.getByTemplateCodeAndIsActive(templateCode, 1);
    }

    @Override
    public MailTemplate save(MailTemplate mailTemplate) {
        return mailTemplateRepository.save(mailTemplate);
    }

    @Override
    public MailTemplate getByTemplateId(Long templateId) {
        return mailTemplateRepository.getById(templateId);
    }

    @Override
    public PagedData<GetMailTemplateListResponseModel> getMailTemplateList(Integer page, Integer limit) {

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<GetMailTemplateListResponseModel> templateList = mailTemplateRepository.getTemplateList(pageable);
        return PageUtils.setPageResponse(templateList);
    }


}
