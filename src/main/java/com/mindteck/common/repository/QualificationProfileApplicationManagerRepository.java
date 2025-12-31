package com.mindteck.common.repository;

import com.mindteck.common.modules.user.model.rest.formdata.QualificationProfileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QualificationProfileApplicationManagerRepository extends JpaRepository<QualificationProfileData, Long> {

    @Query(value = "SELECT * " +
            "FROM tbl_qualification_profile WHERE form_unique_id =:formUniqueId", nativeQuery = true)
    QualificationProfileData getQualificationProfileDataByFormUniqueId(Long formUniqueId);

    @Query(value = "DELETE FROM tbl_qualification_profile WHERE form_unique_id =:formUniqueId", nativeQuery = true)
    void deleteQualificationProfileData(Long formUniqueId);

    @Query(value = "UPDATE tbl_qualification_profile SET overall_status = ?2 WHERE form_unique_id = ?1", nativeQuery = true)
    @Modifying
    void updateOverallStatus(Long formUniqueId, Integer overallStatus);

}
