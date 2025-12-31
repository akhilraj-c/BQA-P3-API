package com.mindteck.common.modules.program_structure.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@NoArgsConstructor
public class SavePsChecklistRequest {

    @NotNull(message = "form unique ID cannot be null")
    private Long formUniqueId;

    @NotNull(message = "sl no cannot be null")
    private Integer slNo;

    @NotEmpty(message = "Criteria list cannot be empty")
    private List<CriterionEntry> criteria;

    // Getters and Setters

    @Data
    @NoArgsConstructor
    public static class CriterionEntry {
        @NotNull(message = "Criterion cannot be null")
        private Integer criterion;

        @NotEmpty(message = "Sub-criteria list cannot be empty")
        private List<SubCriterionEntry> subCriteria;

        // Getters and Setters

        @Data
        @NoArgsConstructor
        public static class SubCriterionEntry {
            @NotNull(message = "Sub-criterion cannot be null")
            private String subCriterion;

            @NotNull(message = "Status cannot be null")
            private Integer status; // 1 for Yes, 0 for No

            // Getters and Setters
        }
    }
}
