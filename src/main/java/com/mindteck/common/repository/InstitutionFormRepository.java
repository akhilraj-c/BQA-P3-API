package com.mindteck.common.repository;

import com.mindteck.common.models.InstituteForm;
import com.mindteck.common.modules.form.rest.GetFormListResponseModel;
import com.mindteck.common.modules.form.rest.formdata.GetQualificationsWithStatusResponseModel;
import com.mindteck.common.modules.user.model.rest.GetInstitutesHavingQualificationResponseModel;
import com.mindteck.common.modules.user.model.rest.InstituteFormInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstitutionFormRepository extends JpaRepository<InstituteForm, Long> {
    InstituteForm getByFormUniqueId(Long formUniqueId);

    InstituteForm getByFormUniqueIdAndAssignedAppManager(Long formUniqueId, Long userId);

    Optional<InstituteForm> findByQpId(String qpId);

    List<InstituteForm> findByQpIdLike(String qpIdPattern);

    InstituteForm getByContactPersonEmail(String contactPersonEmail);
    List<InstituteForm> getFormsByContactPersonEmail(String contactPersonEmail);

    InstituteForm findOneByInstAppLicNoAndPlannedSubDate(String licence, String submissionDate);

    @Query(
            value = "SELECT DISTINCT inst " +
                    "FROM InstituteForm inst " +
                    "WHERE inst.institutionId = ?1 AND inst.status < ?2"
    )
    List<InstituteForm> getAllQualificationForArchival(Long userId, Integer archivalStatus);



    @Query(value = "SELECT form_unique_id formUniqueId, institute_name institutionName, inst_app_lic_no licenceNo, approval_doc_path licenseDocUrl, issue_date issueDate," +
            " exp_date expiryDate, is_bqa_reviewed bqaReviewed, review_issue_date reviewDate, review_jud_result reviewResult," +
            "  is_offering_nan_loc_course_qa nationalCourseOffered," +
            " offering_description courseOfferedDescription, planned_sub_date submissionDate, poc_name contactName," +
            " poc_email emailId, poc_cn_number contactNo, poc_title pocTitle, status status, license_type licenseType," +
            " regulatory_other_data regulatoryOthersData," +
            " licenced_by_other_data licencedByOthersData," +
            " institution_type_other_data institutionTypeOtherData," +
            " listingId listingId," +
            " field_other_data fieldOtherData " +
            "FROM tbl_institute_form WHERE form_unique_id= :uniqueId", nativeQuery = true)
    InstituteFormInterface getInstituteFormDetails(Long uniqueId);

    //BQA_ADMIN
    @Query(
            value = "SELECT new com.mindteck.common.modules.form.rest.GetFormListResponseModel" +
                    " ( " +
                    " inst.formUniqueId, " +
                    " inst.formId, " +
                    " inst.status, " +
                    " inst.subStatus, " +
                    " inst.applicantOrganizationName, " +
                    " inst.currentSubmitDate," +
                    " inst.instAppLicNo," +
                    " inst.assignedAppManager," +
                    " inst.plannedSubDate," +
                    " inst.isDateExtensionRequested," +
                    " inst.dateExtensionStatus," +
                    " inst.dateExtensionReason," +
                    " inst.requestedExtensionDate," +
                    " inst.completedStatus," +
                    " inst.institutionAppealExpiry," +
                    " inst.currentStatusDueDate," +
                    " inst.lastUpdatedTime," +
                    " inst.isBqaReviewed," +
                    " inst.reviewJudResult,"+
                    " inst.qualificationTitle,"+
                    " inst.noOfModules,"+
                    " inst.listingId,"+
                    " inst.formParentUniqueId,"+
                    " inst.isRevalidation,"+
                    " inst.rejectionReason,"+
                    " inst.rejectionDate,"+
                    " inst.isPaid,"+
                    "inst.contactPersonName," +
                    "inst.contactPersonEmail" +
                    " ) " +
                    " FROM InstituteForm inst WHERE inst.isDraft = 0" +
                    "AND ( :searchValue IS NULL OR :searchValue = '' OR " +
                    "      (LOWER(REPLACE(inst.institutionName, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')) " +
                    "       OR LOWER(REPLACE(inst.qualificationTitle, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%'))) )",
            countQuery = "SELECT COUNT(*) FROM InstituteForm inst WHERE inst.isDraft = 0" +
                    "AND ( :searchValue IS NULL OR :searchValue = '' OR " +
                    "      (LOWER(REPLACE(inst.institutionName, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')) " +
                    "       OR LOWER(REPLACE(inst.qualificationTitle, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%'))) )"
    )
    Page<GetFormListResponseModel> getFormList(String searchValue,Pageable pageable);

    //Application Manger
    @Query(
            value = "SELECT new com.mindteck.common.modules.form.rest.GetFormListResponseModel" +
                    " ( " +
                    " inst.formUniqueId, " +
                    " inst.formId, " +
                    " inst.status, " +
                    " inst.subStatus, " +
                    " inst.institutionName, " +
                    " inst.currentSubmitDate," +
                    " inst.instAppLicNo," +
                    " inst.assignedAppManager," +
                    " inst.plannedSubDate," +
                    " inst.isDateExtensionRequested," +
                    " inst.dateExtensionStatus," +
                    " inst.dateExtensionReason," +
                    " inst.requestedExtensionDate," +
                    " inst.completedStatus," +
                    " inst.institutionAppealExpiry," +
                    " inst.currentStatusDueDate," +
                    " inst.lastUpdatedTime," +
                    " inst.isBqaReviewed," +
                    " inst.reviewJudResult,"+
                    " inst.qualificationTitle,"+
                    " inst.noOfModules,"+
                    " inst.listingId,"+
                    " inst.formParentUniqueId,"+
                    " inst.isRevalidation,"+
                    " inst.rejectionReason,"+
                    " inst.rejectionDate,"+
                    " inst.isPaid,"+
                    "inst.contactPersonName," +
                    "inst.contactPersonEmail" +
                    " ) " +
                    " FROM InstituteForm inst WHERE inst.isDraft = 0 AND inst.assignedAppManager = :appMngrId AND inst.plannedSubDate LIKE CONCAT('%', :plannedSubmissionDate, '%') " +
                    "AND ( :searchValue IS NULL OR :searchValue = '' OR " +
                    "      LOWER(REPLACE(inst.institutionName, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')) " +
                    "   OR LOWER(REPLACE(inst.qualificationTitle, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')) )",
            countQuery = "SELECT COUNT(*) FROM InstituteForm inst WHERE  inst.isDraft = 0 AND inst.assignedAppManager = :appMngrId AND inst.plannedSubDate LIKE CONCAT('%', :plannedSubmissionDate, '%') " +
                    "AND ( :searchValue IS NULL OR :searchValue = '' OR " +
                    "      LOWER(REPLACE(inst.institutionName, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')) " +
                    "   OR LOWER(REPLACE(inst.qualificationTitle, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')) )"
    )
    Page<GetFormListResponseModel> getFormList(String searchValue,Long appMngrId, String plannedSubmissionDate, Pageable pageable);

    //Institution
    @Query(
            value = "SELECT DISTINCT new com.mindteck.common.modules.form.rest.GetFormListResponseModel(" +
                    " inst.formUniqueId, " +
                    " inst.formId, " +
                    " inst.status, " +
                    " inst.subStatus, " +
                    " inst.applicantOrganizationName, " +
                    " inst.currentSubmitDate, " +
                    " inst.instAppLicNo, " +
                    " inst.assignedAppManager, " +
                    " inst.plannedSubDate, " +
                    " inst.isDateExtensionRequested, " +
                    " inst.dateExtensionStatus, " +
                    " inst.dateExtensionReason, " +
                    " inst.requestedExtensionDate, " +
                    " inst.completedStatus, " +
                    " inst.institutionAppealExpiry, " +
                    " inst.currentStatusDueDate, " +
                    " inst.lastUpdatedTime, " +
                    " inst.isBqaReviewed, " +
                    " inst.reviewJudResult, " +
                    " inst.qualificationTitle, " +
                    " inst.noOfModules, " +
                    " inst.listingId, " +
                    " inst.formParentUniqueId, " +
                    " inst.isRevalidation, " +
                    " inst.rejectionReason, " +
                    " inst.rejectionDate, " +
                    " inst.isPaid, " +
                    " inst.contactPersonName, " +
                    " inst.contactPersonEmail) " +
                    "FROM InstituteForm inst " +
                    "WHERE inst.isDraft = 0 " +
                    "AND inst.institutionId = :institutionId " +
                    "AND inst.status >= :status " +
                    "AND inst.plannedSubDate LIKE CONCAT('%', :plannedSubmissionDate, '%') " +
                    "AND ( :searchValue IS NULL OR :searchValue = '' OR " +
                    "      LOWER(REPLACE(inst.institutionName, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')) " +
                    "   OR LOWER(REPLACE(inst.qualificationTitle, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')) )",

            countQuery = "SELECT COUNT(*) FROM InstituteForm inst " +
                    "WHERE inst.isDraft = 0 " +
                    "AND inst.institutionId = :institutionId " +
                    "AND inst.status >= :status " +
                    "AND inst.plannedSubDate LIKE CONCAT('%', :plannedSubmissionDate, '%') " +
                    "AND ( :searchValue IS NULL OR :searchValue = '' OR " +
                    "      LOWER(REPLACE(inst.institutionName, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')) " +
                    "   OR LOWER(REPLACE(inst.qualificationTitle, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')) )"
    )
    Page<GetFormListResponseModel> getFormList(String searchValue,Long institutionId, Integer status, String plannedSubmissionDate, Pageable pageable);

    @Query(
            value = "SELECT DISTINCT new com.mindteck.common.modules.form.rest.GetFormListResponseModel(" +
                    "inst.formUniqueId, inst.formId, inst.status, inst.subStatus, inst.institutionName, " +
                    "inst.currentSubmitDate, inst.instAppLicNo, inst.assignedAppManager, inst.plannedSubDate, " +
                    "inst.isDateExtensionRequested, inst.dateExtensionStatus, inst.dateExtensionReason, " +
                    "inst.requestedExtensionDate, inst.completedStatus, inst.institutionAppealExpiry, " +
                    "inst.currentStatusDueDate, inst.lastUpdatedTime, inst.isBqaReviewed, inst.reviewJudResult, " +
                    "inst.qualificationTitle, inst.noOfModules, inst.listingId, inst.formParentUniqueId, " +
                    "inst.isRevalidation, inst.rejectionReason, inst.rejectionDate, inst.isPaid, " +
                    "inst.contactPersonName, inst.contactPersonEmail) " +
                    "FROM InstituteForm inst " +
                    "WHERE inst.isDraft = 0 " +
                    "AND inst.formUniqueId = :formUniqueId " +
                    "AND inst.status >= :status " +
                    "AND inst.plannedSubDate LIKE CONCAT('%', :plannedSubmissionDate, '%') " +
                    "AND (:searchValue IS NULL OR :searchValue = '' OR " +
                    "     LOWER(REPLACE(inst.institutionName, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')) " +
                    "  OR LOWER(REPLACE(inst.qualificationTitle, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%'))) ",

            countQuery = "SELECT COUNT(inst) FROM InstituteForm inst " +
                    "WHERE inst.isDraft = 0 " +
                    "AND inst.formUniqueId = :formUniqueId " +
                    "AND inst.status >= :status " +
                    "AND inst.plannedSubDate LIKE CONCAT('%', :plannedSubmissionDate, '%') " +
                    "AND (:searchValue IS NULL OR :searchValue = '' OR " +
                    "     LOWER(REPLACE(inst.institutionName, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')) " +
                    "  OR LOWER(REPLACE(inst.qualificationTitle, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')))"
    )
    Page<GetFormListResponseModel> getFormListByUniqueId(String searchValue,Long formUniqueId, Integer status, String plannedSubmissionDate, Pageable pageable);


    @Query(
            value = "SELECT DISTINCT new com.mindteck.common.modules.form.rest.GetFormListResponseModel(" +
                    "inst.formUniqueId, inst.formId, inst.status, inst.subStatus, inst.institutionName, " +
                    "inst.currentSubmitDate, inst.instAppLicNo, inst.assignedAppManager, inst.plannedSubDate, " +
                    "inst.completedStatus, inst.institutionAppealExpiry, inst.currentStatusDueDate, inst.lastUpdatedTime, " +
                    "inst.isBqaReviewed, inst.reviewJudResult, inst.qualificationTitle, inst.noOfModules, inst.listingId, " +
                    "inst.formParentUniqueId, inst.isRevalidation) " +
                    "FROM InstituteForm inst " +
                    "LEFT JOIN IlepPanel ilep ON inst.formUniqueId = ilep.formUniqueId " +
                    "LEFT JOIN Ilep ilp ON ilp.formUniqueId = inst.formUniqueId " +
                    "WHERE (inst.status IN (26, 30, 31, 32, 33) OR inst.status >= :status) " +
                    "AND (ilep.ilepMemberId = :userId OR ilp.ilepMemberId = :userId) " +
                    "AND inst.plannedSubDate LIKE CONCAT('%', :plannedSubmissionDate, '%') " +
                    "AND (:searchValue IS NULL OR :searchValue = '' OR " +
                    "     LOWER(REPLACE(inst.institutionName, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')) " +
                    "  OR LOWER(REPLACE(inst.qualificationTitle, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')))",

            countQuery = "SELECT COUNT(inst) FROM InstituteForm inst " +
                    "LEFT JOIN IlepPanel ilep ON inst.formUniqueId = ilep.formUniqueId " +
                    "LEFT JOIN Ilep ilp ON ilp.formUniqueId = inst.formUniqueId " +
                    "WHERE (inst.status IN (26, 30, 31, 32, 33) OR inst.status >= :status) " +
                    "AND (ilep.ilepMemberId = :userId OR ilp.ilepMemberId = :userId) " +
                    "AND inst.plannedSubDate LIKE CONCAT('%', :plannedSubmissionDate, '%') " +
                    "AND (:searchValue IS NULL OR :searchValue = '' OR " +
                    "     LOWER(REPLACE(inst.institutionName, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')) " +
                    "  OR LOWER(REPLACE(inst.qualificationTitle, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(TRIM(:searchValue), ' ', ''), '%')))"
    )
    Page<GetFormListResponseModel> getFormList(String searchValue,Integer status, String plannedSubmissionDate, Long userId, Pageable pageable);

    List<InstituteForm> getByContactPersonEmailAndInstAppLicNo(String contactPersonEmail, String instAppLicNo);

    List<InstituteForm> getByPlannedSubDateAndInstAppLicNo(String plannedSubDate, String instAppLicNo);

    InstituteForm findOneByContactPersonEmailAndPlannedSubDate(String contactPersonEmail, String plannedSubDate);

    @Query(value = "DELETE FROM tbl_institute_form WHERE poc_email=:email and is_draft=1", nativeQuery = true)
    @Modifying
    void deleteDraftForm(String email);

    @Query("SELECT new com.mindteck.common.modules.form.rest.formdata.GetQualificationsWithStatusResponseModel( " +
            "i.institutionName, i.qualificationTitle , i.status , i.isRevalidation " +
            ") " +
            "FROM InstituteForm i ORDER BY i.qualificationTitle ASC")
    List<GetQualificationsWithStatusResponseModel> getQualificationsWithStatusForBQA();

}
