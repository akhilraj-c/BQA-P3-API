package com.mindteck.common.modules.evaluation.dao;

import com.mindteck.common.models.ConflictForm;
import com.mindteck.common.models.IlepPanel;
import com.mindteck.common.models.Meeting;
import com.mindteck.common.modules.evaluation.rest.GetFormIlepMemberResponseModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationDao {

    ConflictForm getByFormUniqueId(Long formUniqueId, Integer isHistory);

    ConflictForm save(ConflictForm conflinullctForm);

    List<IlepPanel> getByFormUniqueIdAndPanelId(Long formUniqueId, Long panelId);

    IlepPanel save(IlepPanel ilepPanel);

    Meeting getByMeetingId(Long meetingId);

    Meeting save(Meeting meeting);

    ConflictForm getByFormUniqueId(Long formUniqueId);

    void delete(IlepPanel ilepPanel);

    List<IlepPanel> getILEPByFormUniqueId(Long formUniqueId);

    List<IlepPanel> saveILEPPanel(List<IlepPanel> ilepPanelList);

    List<GetFormIlepMemberResponseModel> getFormIlepMember(Long formUniqueId);

    Meeting getMeetingByFormUniqueId(Long formUniqueId);

    Long countByFormUniqueIdAndIsDfoApproved(Long formUniqueId, Integer panelStatus);

    IlepPanel getIlepMemberId(Long ilepMemberId, Long panelId);

    IlepPanel getByIlepMemberIdAndFormUniqueId(Long userId , Long formUniqueId);

    IlepPanel getByIlepMemberAndFormUniqueIdAndGrandAccess(Long userId , Long formUniqueId , Integer grandAccess);

}
