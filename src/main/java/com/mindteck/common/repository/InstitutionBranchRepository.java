package com.mindteck.common.repository;

import com.mindteck.common.models.InstitutionBranch;
import com.mindteck.common.modules.form.rest.GetBranchesResponseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionBranchRepository extends JpaRepository<InstitutionBranch , Long> {

    InstitutionBranch findByFormUniqueIdAndBuildingAndRoadAndBlock(Long formUniqueId ,String building , String road , String block);

    @Query(value = "select new com.mindteck.common.modules.form.rest.GetBranchesResponseModel" +
            " ( " +
            " branch.building, " +
            " branch.road, " +
            " branch.block " +
            " ) " +
            " from InstitutionBranch branch where branch.formUniqueId = ?1")
    List<GetBranchesResponseModel> getInstitutionBranches(Long formUniqueId);
}
