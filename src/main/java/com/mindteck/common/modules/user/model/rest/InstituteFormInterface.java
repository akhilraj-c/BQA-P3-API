package com.mindteck.common.modules.user.model.rest;

public interface InstituteFormInterface {

    Long getFormUniqueId();

    String getInstitutionName();

    String getLicenceNo();

    String getLicenseDocUrl();

    Long getIssueDate();

    Long getExpiryDate();

    Integer getBqaReviewed();

    Long getReviewDate();

    String getReviewResult();

    Integer getNationalCourseOffered();

    String getCourseOfferedDescription();

    String getSubmissionDate();

    String getContactName();

    String getEmailId();

    String getContactNo();

    String getPocTitle();

    Integer getStatus();

    Integer getLicenseType();

    String getRegulatoryOthersData();

    String getLicencedByOthersData();

    String getInstitutionTypeOtherData();
    String getFieldOtherData();


}
