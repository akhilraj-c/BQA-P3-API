package com.mindteck.common.modules.user.model.rest.formdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_qualification_provider")
public class QualificationProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "qualification_profile_id",
            nullable = false
    )
    private QualificationProfileData qualificationProfile;

    private String providerName;
    private String providerWebsite;
    private String providerContactPerson;
    private String providerEmail;
    private String providerAddress;
}
