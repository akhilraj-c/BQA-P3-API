package com.mindteck.common.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_application_status")
@NoArgsConstructor
@AllArgsConstructor
public class AppStatus extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id", nullable = false)
    private Long statusId;

    @Column(name = "status_number")
    private Long statusNumber;

    @Column(name = "english_text")
    private String englishText;

    @Column(name = "arab_text")
    private String arabText;
}
