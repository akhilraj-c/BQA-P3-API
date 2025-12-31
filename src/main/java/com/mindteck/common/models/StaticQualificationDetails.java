package com.mindteck.common.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_static_qualification_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaticQualificationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "qualification_title")
    private String qualificationTitle;

    @Column(name = "institution_name")
    private String institutionName;

    @Column(name = "status")
    private String status;

    @Column(name = "nqf_level")
    private String nqf_level;

    @Column(name = "nqf_credit")
    private String nqfCredit;


    @Column(name = "sector")
    private String sector;

    @Column(name = "operation_status")
    private String operationStatus;

    @Column(name = "process_count")
    private String processCount;

    @Column(name = "condition_fulfilled")
    private String conditionFulfilled;
}
