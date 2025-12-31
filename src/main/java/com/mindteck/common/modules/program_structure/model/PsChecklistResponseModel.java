package com.mindteck.common.modules.program_structure.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PsChecklistResponseModel {
    private Long formUniqueId;
    private Integer slNo;
    private List<CriterionEntry> criteria;

    @Data
    public static class CriterionEntry {
        private Integer criterion;
        private List<SubCriterionEntry> subCriteria;

        @Data
        public static class SubCriterionEntry {
            private String subCriterion;
            private Integer status; // 1 for Yes, 0 for No
        }
    }
}
