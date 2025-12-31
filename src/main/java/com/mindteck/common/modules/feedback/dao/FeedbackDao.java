package com.mindteck.common.modules.feedback.dao;

import com.mindteck.common.models.DocumentFeedback;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackDao {

    DocumentFeedback getByFormUniqueId(Long uniqueId);

    DocumentFeedback save(DocumentFeedback documentFeedback);

//    GetDocumentFeedbackResponseModel getFeedbackDetails(Long uniqueId);
}
