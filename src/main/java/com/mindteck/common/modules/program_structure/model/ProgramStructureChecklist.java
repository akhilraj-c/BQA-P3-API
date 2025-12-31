package com.mindteck.common.modules.program_structure.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tbl_program_structure_checklist")
@Getter
@Setter
public class ProgramStructureChecklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "program_structure_id", nullable = false)
//    private ProgramStructure programStructure;
    @Column(name = "form_unique_id", nullable = false)
    private Long formUniqueId;

    @Column(name = "sl_no", nullable = false)
    private Integer slNo;

    @Column(name = "criterion", nullable = false)
    private Integer criterion;

    @Column(name = "sub_criterion", nullable = false)
    private String subCriterion;

    @Column(name = "status", nullable = false)
    private Integer status; // 1 for Yes, 0 for No
}