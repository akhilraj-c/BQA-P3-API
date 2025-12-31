package com.mindteck.repository_cas;

import com.mindteck.common.modules.user.model.rest.GetInstitutesHavingQualificationResponseModel;
import com.mindteck.models_cas.ListedInstitute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<ListedInstitute, Long> {

    @Query(value = "SELECT * FROM tbl_listed_institutes WHERE email =:emailId", nativeQuery = true)
    ListedInstitute getInstitute(String emailId);

    @Query("SELECT DISTINCT new com.mindteck.common.modules.user.model.rest.GetInstitutesHavingQualificationResponseModel(inst.instituteNameEnglish, inst.email, inst.userId) " +
            "FROM ListedInstitute inst")
    List<GetInstitutesHavingQualificationResponseModel> findDistinctInstitutionDetails();

    @Query("SELECT l.instituteNameEnglish FROM ListedInstitute l WHERE l.userId = :userId")
    String findInstitutionNameByUserId(@Param("userId") Long userId);

    ListedInstitute getByUserId(Long userId);
    ListedInstitute getById(Long userId);

}
