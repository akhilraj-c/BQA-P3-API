package com.mindteck.common.modules.evaluation.rest;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class GetFQDetailsResponseModel {
    private String applicantOrganizationName;
    private List<Qualification> qualifications;
    private String qualificationIncludedOtherFramework;
    private String contactName;
    private String contactPosition;
    private String contactNumber;
    private String contactEmail;
    private Integer qualificationType;

    @Data
    @Builder
    public static class Qualification {
        private String qualificationTitle;
        private String awardingBody;
        private String providers;
        private String levelAndCredit;
        private String numberOfUnitsCoursesModules;
        private Long expectedSubmissionDate;

    }
}
