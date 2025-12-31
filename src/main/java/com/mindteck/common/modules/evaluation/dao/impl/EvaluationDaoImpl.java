package com.mindteck.common.modules.evaluation.dao.impl;

import com.mindteck.common.models.ConflictForm;
import com.mindteck.common.models.IlepPanel;
import com.mindteck.common.models.Meeting;
import com.mindteck.common.modules.evaluation.dao.EvaluationDao;
import com.mindteck.common.modules.evaluation.rest.GetFormIlepMemberResponseModel;
import com.mindteck.common.repository.ConflictFormRepository;
import com.mindteck.common.repository.IlepPanelRepository;
import com.mindteck.common.repository.MeetingRepository;
import com.mindteck.models_cas.User;
import com.mindteck.repository_cas.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class EvaluationDaoImpl implements EvaluationDao {
    @Autowired
    ConflictFormRepository conflictFormRepository;

    @Autowired
    IlepPanelRepository ilepPanelRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ConflictForm getByFormUniqueId(Long formUniqueId, Integer isHistory) {
        return conflictFormRepository.findByFormUniqueIdAndIsHistory(formUniqueId, isHistory);
    }

    @Override
    public ConflictForm save(ConflictForm conflictForm) {
        return conflictFormRepository.save(conflictForm);
    }

    @Override
    public List<IlepPanel> getByFormUniqueIdAndPanelId(Long formUniqueId, Long panelId) {
        return ilepPanelRepository.getByFormUniqueIdAndPanelId(formUniqueId, panelId);
    }

    @Override
    public IlepPanel save(IlepPanel ilepPanel) {
        return ilepPanelRepository.save(ilepPanel);
    }

    @Override
    public Meeting getByMeetingId(Long meetingId) {
        return meetingRepository.getById(meetingId);
    }

    @Override
    public Meeting save(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    @Override
    public ConflictForm getByFormUniqueId(Long formUniqueId) {
        return conflictFormRepository.getByFormUniqueId(formUniqueId);
    }

    @Override
    public void delete(IlepPanel ilepPanel) {
        ilepPanelRepository.delete(ilepPanel);
    }

    @Override
    public List<IlepPanel> getILEPByFormUniqueId(Long formUniqueId) {
        return ilepPanelRepository.getByFormUniqueId(formUniqueId);
    }

    @Override
    public List<IlepPanel> saveILEPPanel(List<IlepPanel> ilepPanelList) {
        return ilepPanelRepository.saveAll(ilepPanelList);
    }

    @Override
    public List<GetFormIlepMemberResponseModel> getFormIlepMember(Long formUniqueId) {
//        return ilepPanelRepository.getFormIlepMembers(formUniqueId);
        List<IlepPanel> ilepPanels = ilepPanelRepository.findByFormUniqueId(formUniqueId);

        if (ilepPanels.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> ilepMemberIds = ilepPanels.stream()
                .map(IlepPanel::getIlepMemberId)
                .collect(Collectors.toList());

        List<User> users = userRepository.findByUserIds(ilepMemberIds);

        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getUserId, user -> user));

        return ilepPanels.stream()
                .map(panel -> new GetFormIlepMemberResponseModel(
                        panel.getFormUniqueId(),
                        panel.getPanelId(),
                        panel.getIlepMemberId(),
                        userMap.getOrDefault(panel.getIlepMemberId(), new User()).getUsername(),
                        panel.getIsHead(),
                        panel.getPanelStatus()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Meeting getMeetingByFormUniqueId(Long formUniqueId) {
        return meetingRepository.getByFormUniqueId(formUniqueId);
    }

    @Override
    public Long countByFormUniqueIdAndIsDfoApproved(Long formUniqueId, Integer panelStatus) {
        return ilepPanelRepository.countByFormUniqueIdAndIsDfoApproved(formUniqueId, panelStatus);
    }

    @Override
    public IlepPanel getIlepMemberId(Long ilepMemberId, Long panelId) {
        return ilepPanelRepository.getByIlepMemberIdAndPanelId(ilepMemberId, panelId);
    }

    @Override
    public IlepPanel getByIlepMemberIdAndFormUniqueId(Long userId, Long formUniqueId) {
        return ilepPanelRepository.getByIlepMemberIdAndFormUniqueId(userId,formUniqueId);
    }

    @Override
    public IlepPanel getByIlepMemberAndFormUniqueIdAndGrandAccess(Long userId, Long formUniqueId, Integer grandAccess) {
        return ilepPanelRepository.getByIlepMemberIdAndFormUniqueIdAndGrandAccess(userId,formUniqueId,grandAccess);
    }


}
