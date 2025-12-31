package com.mindteck.common.modules.IlepEvaluationForm.dao;

import com.mindteck.common.models.*;
import org.springframework.stereotype.Repository;

@Repository
public interface IlepEvaluationDao {

    ILEPEvaluationForm save(ILEPEvaluationForm form);

    ILEPEvaluationForm getByFormUniqueId(Long formUniqueId);

    SiteVisit getSiteVisitByFormUniqueId(Long formUniqueId);

    SiteVisit saveSiteVisit(SiteVisit siteVisit);

    Meeting getMeeting(Long formUniqueId);

    SiteVisitDateChange getVisitDateChangeByFormUniqueId(Long formUniqueId);

    SiteVisitDateChange saveSiteVisitDateChange(SiteVisitDateChange siteVisitDateChange);

    GDQDocument getGDQDocumentByFormUniqueId(Long formUniqueId);

    GDQDocument saveGDQDocument(GDQDocument document);

    IlepEvaluationReportCopy getEvalCopyByFormUniqueId(Long formUniqueId);

    void save(IlepEvaluationReportCopy ilepEvaluationReportCopy);
}
