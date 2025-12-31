package com.mindteck.common.modules.standards;

import com.mindteck.common.modules.standards.model.StandardWorkFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandardWorkFlowManagerRepository extends JpaRepository<StandardWorkFlow, Long> {

    @Query(value = "SELECT * " +
            "FROM tbl_std_work_flow WHERE form_unique_id =:formUniqueId AND is_cf=:isCf", nativeQuery = true)
   List<StandardWorkFlow> getStandards(Long formUniqueId, Integer isCf);

    @Query(value = "SELECT * " +
            "FROM tbl_std_work_flow WHERE form_unique_id =:formUniqueId and " +
            "parent_tag=:parentTag and " +
            "tag=:tag and " +
            "version=:version and " +
            "sub_version=:subVersion", nativeQuery = true)
    StandardWorkFlow getStandard(Long formUniqueId,
                                 String parentTag,
                                 String tag,
                                 Integer version,
                                 Integer subVersion);

    @Query(value = "SELECT * " +
            "FROM tbl_std_work_flow WHERE form_unique_id =:formUniqueId and " +
            "parent_tag=:parentTag and " +
            "tag=:tag", nativeQuery = true)
    List<StandardWorkFlow> getStandard(Long formUniqueId,
                               String parentTag,
                               String tag);

    @Query(value = "DELETE " +
            "FROM tbl_std_work_flow WHERE form_unique_id =:formUniqueId and " +
            "parent_tag=:parentTag and " +
            "tag=:tag and " +
            "version=:version and " +
            "sub_version=:subVersion", nativeQuery = true)
    @Modifying
    void deleteStandardIfExist(Long formUniqueId,
                                 String parentTag,
                                 String tag,
                                 Integer version,
                                 Integer subVersion);

 @Query(value = "DELETE " +
         "FROM tbl_std_work_flow WHERE form_unique_id =:formUniqueId and " +
         "parent_tag=:parentTag and " +
         "tag=:tag and " +
         "version=:version", nativeQuery = true)
 @Modifying
 void deleteStandardIfExist(Long formUniqueId,
                            String parentTag,
                            String tag,
                            Integer version);

    @Query(value = "DELETE " +
            "FROM tbl_std_work_flow WHERE form_unique_id =:formUniqueId and " +
            "parent_tag=:parentTag and " +
            "tag=:tag and " +
            "version=:version and " +
            "is_cf=:isCf", nativeQuery = true)
    @Modifying
    void deleteStandardIfExistWithCfFlag(Long formUniqueId,
                               String parentTag,
                               String tag,
                               Integer version,
                               Integer isCf);


    @Query(value = "DELETE " +
            "FROM tbl_std_work_flow WHERE form_unique_id =:formUniqueId", nativeQuery = true)
    @Modifying
    void deleteAllStandards(Long formUniqueId);

    @Query(value = "DELETE " +
            "FROM tbl_std_work_flow WHERE form_unique_id =:formUniqueId and " +
            "parent_tag=:parentTag and " +
            "tag=:tag", nativeQuery = true)
    @Modifying
    void deleteStandardIfExist(Long formUniqueId,
                               String parentTag,
                               String tag);

}
