package com.mindteck.common.repository;

import com.mindteck.common.models.IlepPanel;
import com.mindteck.common.modules.evaluation.rest.GetFormIlepMemberResponseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IlepPanelRepository extends JpaRepository<IlepPanel, Long> {

    @Query("SELECT ilp FROM IlepPanel ilp WHERE ilp.formUniqueId = :formUniqueId")
    List<IlepPanel> findByFormUniqueId(Long formUniqueId);

    List<IlepPanel> getByFormUniqueIdAndPanelId(Long formUniqueId, Long panelId);

    List<IlepPanel> getByFormUniqueId(Long formUniqueId);

//    @Query("select new com.mindteck.common.modules.evaluation.rest.GetFormIlepMemberResponseModel" +
//            "(" +
//            " ilp.formUniqueId," +
//            " ilp.panelId, " +
//            " ilp.ilepMemberId, " +
//            " usr.username, " +
//            " ilp.isHead, " +
//            " ilp.panelStatus " +
//            ") " +
//            " from IlepPanel ilp" +
//            " join User usr on usr.userId = ilp.ilepMemberId " +
//            " where ilp.formUniqueId = :formUniqueId")
//    List<GetFormIlepMemberResponseModel> getFormIlepMembers(Long formUniqueId);

    Long countByFormUniqueIdAndIsDfoApproved(Long formUniqueId, Integer panelStatus);

    IlepPanel getByIlepMemberIdAndPanelId(Long ilepMemberId, Long panelId);

    IlepPanel getByIlepMemberIdAndFormUniqueId(Long userId, Long formUniqueId);

    IlepPanel getByIlepMemberIdAndFormUniqueIdAndGrandAccess(Long userId, Long formUniqueId , Integer grandAccess);

    IlepPanel getByIlepMemberIdAndIsHead(Long ilepMemberId, Integer isHead);

    IlepPanel getByFormUniqueIdAndIsHead(Long formUniqueId, Integer isHead);
}
