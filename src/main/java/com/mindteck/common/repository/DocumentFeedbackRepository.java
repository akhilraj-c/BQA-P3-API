package com.mindteck.common.repository;

import com.mindteck.common.models.DocumentFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentFeedbackRepository extends JpaRepository<DocumentFeedback, Long> {

    DocumentFeedback getByFormUniqueId(Long uniqueId);

   /* @Query( value = "SELECT new com.mindteck.common.modules.feedback.models.GetDocumentFeedbackResponseModel" +
            "(" +
            "doc.qacFeedbackDescription qacFeedback, doc.qacFeedbackFile qacFeedbackFile, doc.institutionComment institutionComment, " +
            "doc.institutionAdditionalInfoStatus qacAdditionalInfoStatus, doc.submittedReportFile submittedReportFile, doc.nacComment nacComment," +
            "doc.nacAdditionalInfoStatus nacAdditionInfoStatus, doc.serialNo serialNo, doc.mcuScannedFile mcuScannedFile, doc.mcuFileComment mcoFileComment," +
            "doc.nacFeedbackDescription nacDescription, doc.nacFeedbackFile nacFeedBackFile, doc.dfoCommentMCO dfoCommentMCO, doc.dfoFileMco dfoFileMco, " +
            "doc.dfoSignedFile dfoSignedFile, doc.dfoSignedStatus dfoSignedStatus, doc.dfoSharedCabinet dfoSharedCabinetStatus, " +
            "doc.dfoCabinetApproved dfoCabinetApprovedStatus, inst.status applicationStatus, ilep.dfoChiefEvaluationComment," +
            " ilep.dfoChiefPlainComment, ilep.gdqAcEvaluationComment,  ilep.gdqAcPlainComment, " +
            "ilep.qacEvaluationComment, ilep.qacPlainComment, ilep.institutionCommentBackFile," +
            " ilep.institutionCommentBack" +
            ") FROM DocumentFeedback doc" +
            " LEFT JOIN ILEPEvaluationForm ilep ON ilep.form_unique_id=doc.form_unique_id " +
            " LEFT JOIN InstituteForm inst ON inst.form_unique_id=doc.form_unique_id" +
            " WHERE ilep.form_unique_id=:formUniqueId")
    GetDocumentFeedbackResponseModel getFeedbackDetails(Long formUniqueId);*/
}
