package com.mindteck.common.modules.user.dao;

import com.mindteck.common.models.MailTemplate;
import com.mindteck.common.models.rest.PagedData;
import com.mindteck.common.modules.user.model.rest.GetMailTemplateListResponseModel;

public interface MailTemplateDao {

    MailTemplate getByTemplateCode(String templateCode);

    MailTemplate save(MailTemplate mailTemplate);

    MailTemplate getByTemplateId(Long mailId);

    PagedData<GetMailTemplateListResponseModel> getMailTemplateList(Integer page, Integer limit);
}
