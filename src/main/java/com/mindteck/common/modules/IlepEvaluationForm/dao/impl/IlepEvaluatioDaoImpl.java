package com.mindteck.common.modules.IlepEvaluationForm.dao.impl;

import com.mindteck.common.models.*;
import com.mindteck.common.repository.*;
import com.mindteck.common.modules.IlepEvaluationForm.dao.IlepEvaluationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IlepEvaluatioDaoImpl implements IlepEvaluationDao {

    @Autowired
    IlepEvaluationCopyRepository ilepEvaluationCopyRepository;

    @Autowired
    ILEPEvaluationFormRepository ilepEvaluationFormRepository;

    @Autowired
    SiteVisitRepository siteVisitRepository;

    @Autowired
    MeetingRepository meetingRepository;
    @Autowired
    SiteVisitDateChangeRepository siteVisitDateChangeRepository;
    @Autowired
    GDQDocumentRepository gdqDocumentRepository;

    @Override
    public ILEPEvaluationForm save(ILEPEvaluationForm form) {
        return ilepEvaluationFormRepository.save(form);
    }

    @Override
    public ILEPEvaluationForm getByFormUniqueId(Long formUniqueId) {
        return ilepEvaluationFormRepository.getByFormUniqueId(formUniqueId);
    }

    @Override
    public SiteVisit getSiteVisitByFormUniqueId(Long formUniqueId) {
        return siteVisitRepository.getByFormUniqueId(formUniqueId);
    }

    @Override
    public SiteVisit saveSiteVisit(SiteVisit siteVisit) {
        return siteVisitRepository.save(siteVisit);
    }

    @Override
    public Meeting getMeeting(Long formUniqueId) {
        return meetingRepository.getByFormUniqueId(formUniqueId);
    }

    @Override
    public SiteVisitDateChange getVisitDateChangeByFormUniqueId(Long formUniqueId) {
        return siteVisitDateChangeRepository.findByFormUniqueId(formUniqueId);
    }

    @Override
    public SiteVisitDateChange saveSiteVisitDateChange(SiteVisitDateChange siteVisitDateChange) {
        return siteVisitDateChangeRepository.save(siteVisitDateChange);
    }

    @Override
    public GDQDocument getGDQDocumentByFormUniqueId(Long formUniqueId) {
        return gdqDocumentRepository.findOneByFormUniqueId(formUniqueId);
    }

    @Override
    public GDQDocument saveGDQDocument(GDQDocument document) {
        return gdqDocumentRepository.save(document);
    }

    @Override
    public IlepEvaluationReportCopy getEvalCopyByFormUniqueId(Long formUniqueId) {
        return ilepEvaluationCopyRepository.getByFormUniqueId(formUniqueId);
    }

    @Override
    public void save(IlepEvaluationReportCopy ilepEvaluationReportCopy) {
        ilepEvaluationCopyRepository.save(ilepEvaluationReportCopy);
    }

}
