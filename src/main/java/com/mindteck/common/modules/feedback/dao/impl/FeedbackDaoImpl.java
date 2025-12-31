package com.mindteck.common.modules.feedback.dao.impl;

import com.mindteck.common.models.DocumentFeedback;
import com.mindteck.common.repository.DocumentFeedbackRepository;
import com.mindteck.common.modules.feedback.dao.FeedbackDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class FeedbackDaoImpl implements FeedbackDao {

    @Autowired
    private DocumentFeedbackRepository documentFeedbackRepository;

    @Override
    public DocumentFeedback getByFormUniqueId(Long uniqueId) {
        return documentFeedbackRepository.getByFormUniqueId(uniqueId);
    }

    @Override
    public DocumentFeedback save(DocumentFeedback documentFeedback) {
        return documentFeedbackRepository.save(documentFeedback);
    }

    /*@Override
    public GetDocumentFeedbackResponseModel getFeedbackDetails(Long uniqueId){
        return documentFeedbackRepository.getFeedbackDetails(uniqueId);
    }*/
}
