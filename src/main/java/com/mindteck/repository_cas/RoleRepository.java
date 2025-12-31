package com.mindteck.repository_cas;

import com.mindteck.common.modules.user.model.rest.GetRoleResponseModel;
import com.mindteck.models_cas.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByUserTypeAndSubType(int userType, int subType);

    @Query("SELECT " +
            "new com.mindteck.common.modules.user.model.rest.GetRoleResponseModel(r.id,r.userType,r.subType,r.name)" +
            " from Role r WHERE r.active=1")
    List<GetRoleResponseModel> getActiveRoles();
}
