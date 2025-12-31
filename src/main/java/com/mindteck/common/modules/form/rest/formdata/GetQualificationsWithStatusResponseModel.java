package com.mindteck.common.modules.form.rest.formdata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetQualificationsWithStatusResponseModel {
    private String qualificationTitle;
    private String institutionName;
    private Integer status;
    @JsonIgnore
    private Integer isRevalidation;

    public GetQualificationsWithStatusResponseModel(String institutionName,String qualificationTitle, Integer status,Integer isRevalidation) {

        this.institutionName = institutionName;
        this.status = formatStatus(status);
        this.isRevalidation = isRevalidation;

        // Add "(Revalidation)" if isRevalidation == 1
        if (isRevalidation != null && isRevalidation == 1) {
            this.qualificationTitle = qualificationTitle + " (Re-validation)";
        } else {
            this.qualificationTitle = qualificationTitle;
        }

    }

    private Integer formatStatus(Integer status) {
        if (status == null) return null;

        return switch (status) {
            case 1 -> 1; // registered
            case 3 -> 2;  // rejected
            case 206 -> 4; // placed
            case 216 -> 5; // archived
            case 220 -> 6; // closed
            case 223 -> 7; // withdrawn
            default -> 3; // in progress
        };
    }
}
