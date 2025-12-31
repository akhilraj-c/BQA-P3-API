package com.mindteck.common.modules.user.model.rest;

import com.mindteck.common.modules.user.model.rest.qp.ForeignQualification;
import com.mindteck.common.modules.user.model.rest.qp.NationalQualification;
import com.mindteck.common.modules.user.model.rest.qp.QualificationTitle;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    @ApiModelProperty(example = "1", required = true, value = "1 (Awarding body) 0r 2 (sole provider)", dataType = "Integer", position = 4)
    private Integer qualificationType;

    @ApiModelProperty(example = "Test name", required = true, value = "Applicant Organization Name", dataType = "String", position = 1)
    private String applicantOrganizationName;

    @ApiModelProperty(value = "List of qualifications", required = true, position = 2)
    private List<Qualification> qualifications;

    @ApiModelProperty(example = "no", value = "Qualification included in another framework", dataType = "String", position = 3)
    private String qualificationIncludedOtherFramework;

//    @ApiModelProperty(value = "Contact number ", required = true, position = 4)
//    private String contactNumber;

    @ApiModelProperty(example = "contact@test.com", dataType = "String", position = 5)
    private String contactEmail;

    @ApiModelProperty(example = "contactPosition", dataType = "String", position = 6)
    private String contactPosition;

    @ApiModelProperty(example = "contactName", dataType = "String", position = 7)
    private String contactName;

    @ApiModelProperty(example = "contactName", dataType = "String", position = 8)
    private String contactNumber;


    @SuperBuilder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Qualification {

        @ApiModelProperty(example = "111", required = true, value = "Qualification title", dataType = "String")
        private String qualificationTitle;

        @ApiModelProperty(example = "111", value = "awarding body", dataType = "String")
        private String awardingBody;

        @ApiModelProperty(value = "Providers offering the qualification")
        private List<String> providers;

        @ApiModelProperty(example = "qwe", value = "Level and credit details", dataType = "String")
        private String levelAndCredit;

        @ApiModelProperty(example = "55", value = "Number of units/courses/modules", dataType = "String")
        private String numberOfUnitsCoursesModules;

        @ApiModelProperty(example = "17656066569", value = "Expected submission date in epoch", dataType = "Long")
        private Long expectedSubmissionDate;

    }


}
