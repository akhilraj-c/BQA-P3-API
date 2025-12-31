package com.mindteck.common.utils;

import com.mindteck.common.constants.Enum.BooleanEnum;
import com.mindteck.common.constants.Enum.UserSubType;
import com.mindteck.common.constants.Enum.UserType;
import com.mindteck.common.models.*;
import com.mindteck.common.repository.*;
import com.mindteck.models_cas.User;
import com.mindteck.repository_cas.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CommonUtils {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstitutionFormRepository institutionFormRepository;

    @Autowired
    private QualificationProfileApplicationManagerRepository qpApplicationManagerRepository;

    @Autowired
    IlepPanelRepository ilepPanelRepository;

    @Autowired
    IlepRepository ilepRepository;

    @Autowired
    FormApplicationManagerRepository appliationRepository;

    public User getUserDetails(Integer userType, Integer userSubType, Long formUniqueId) {
        User user = null;
        switch (userType) {
            case 1 -> {
                LOGGER.info("INSTITUTION");
                LOGGER.info("send remainder to the institution");
                InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(formUniqueId);
                String email = instituteForm.getContactPersonEmail();
                user = userRepository.getByEmailId(email);
                //TODO get institution user details
            }
            case 3 -> {
                LOGGER.info("DFO_ADMIN, APPLICATION_MANAGER, DFO_MEMBER");
                if (UserSubType.MANAGER.getCode().equals(userSubType)) {
                    InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(formUniqueId);
                    Long applicationManagerUserId = instituteForm.getAssignedAppManager();
                    user = userRepository.getByUserId(applicationManagerUserId);
                } else if (UserSubType.ADMIN.getCode().equals(userSubType)) {
                    List<User> users = userRepository.getByUserTypeAndSubType(userType, userSubType);
                    if (!users.isEmpty()) {
                        user = users.get(0);
                    }
                }
            }
            case 7 -> {
                LOGGER.info("ILEP_MEMBER");
                try {
                    IlepPanel ilepPanel = ilepPanelRepository.getByFormUniqueIdAndIsHead(formUniqueId, 1);
                    Long ilepPanelHeadUserId = ilepPanel.getIlepMemberId();
                    user = userRepository.getByUserId(ilepPanelHeadUserId);
                } catch (Exception e) {
                    break;
                }
            }
            default -> {
                LOGGER.info("other users");
                List<User> users = userRepository.getByUserType(userType);
                if (!users.isEmpty()) {
                    user = users.get(0);
                }
            }
        }
        return user;

    }

    public List<String> getMailCcMemebrs(MailTemplate mailTemplate, InstituteForm instituteForm) {
        List<String> ccMemebrs = new ArrayList<>();

        try {
            if (mailTemplate.getInstituteCc() != null) {
                if (mailTemplate.getInstituteCc().equals(BooleanEnum.TRUE.getCode())) {
                    ccMemebrs.add(instituteForm.getContactPersonEmail());
                }
            }

            if (mailTemplate.getDfoAdminCc() != null) {
                if (mailTemplate.getDfoAdminCc().equals(BooleanEnum.TRUE.getCode())) {
                    List<User> dfoAdmns = userRepository.getByUserTypeAndSubType(UserType.DFO_ADMIN.getCode(), UserSubType.ADMIN.getCode());
                    if (!dfoAdmns.isEmpty()) {
                        dfoAdmns.forEach(dfoAdmin -> {
                            if(dfoAdmin.getActive()==1){
                                ccMemebrs.add(dfoAdmin.getEmailId());
                            }
                        });
                    }
                }
            }

            if (mailTemplate.getAmCc() != null) {
                if (mailTemplate.getAmCc().equals(BooleanEnum.TRUE.getCode())) {
                    User amDetails = userRepository.getByUserId(instituteForm.getAssignedAppManager());
                    if(amDetails!=null && amDetails.getActive()==1) {
                        ccMemebrs.add(amDetails.getEmailId());
                    }
                }
            }

            if (mailTemplate.getDfoMemberCc() != null) {
                if (mailTemplate.getDfoMemberCc().equals(BooleanEnum.TRUE.getCode())) {
                    List<User> dfoMembers = userRepository.getByUserTypeAndSubType(UserType.DFO_MEMBER.getCode(), UserSubType.USER.getCode());
                    if (!dfoMembers.isEmpty()) {
                        dfoMembers.forEach(dfoMember -> {
                            if(dfoMember.getActive()==1){
                                ccMemebrs.add(dfoMember.getEmailId());
                            }
                        });
                    }
                }
            }

            if (mailTemplate.getIlepCc() != null) {
                if (mailTemplate.getIlepCc().equals(BooleanEnum.TRUE.getCode())) {
                    List<IlepPanel> ilepList = ilepPanelRepository.getByFormUniqueId(instituteForm.getFormUniqueId());
                    if (!ilepList.isEmpty()) {
                        ilepList.forEach(ilep -> {
                            User ilepMember = userRepository.getByUserId(ilep.getIlepMemberId());
                            if(ilepMember !=null && ilepMember.getActive()==1) {
                                ccMemebrs.add(ilepMember.getEmailId());
                            }
                        });
                    }
                }

            }

            if (mailTemplate.getChiefCc() != null) {
                if (mailTemplate.getChiefCc().equals(BooleanEnum.TRUE.getCode())) {
                    List<User> chiefMembers = userRepository.getByUserTypeAndSubType(UserType.CHIEF.getCode(), UserSubType.ADMIN.getCode());
                    if (!chiefMembers.isEmpty()) {
                        chiefMembers.forEach(cief -> {
                            if(cief.getActive()==1){
                                ccMemebrs.add(cief.getEmailId());
                            }
                        });
                    }
                }
            }

            if (mailTemplate.getDirectorCc() != null) {
                if (mailTemplate.getDirectorCc().equals(BooleanEnum.TRUE.getCode())) {
                    List<User> directors = userRepository.getByUserTypeAndSubType(UserType.DIRECTOR.getCode(), UserSubType.ADMIN.getCode());
                    if (!directors.isEmpty()) {
                        directors.forEach(director -> {
                            if(director.getActive()==1){
                                ccMemebrs.add(director.getEmailId());
                            }
                        });
                    }
                }
            }

            if (mailTemplate.getHeadOfInstitutionCc() != null) {
                if (mailTemplate.getHeadOfInstitutionCc().equals(BooleanEnum.TRUE.getCode())) {
                    FormApplicationManager amDetails = appliationRepository.getByFormUniqueId(instituteForm.getFormUniqueId());
                    if (amDetails!=null) {
                        ccMemebrs.add(amDetails.getHeadOfInstitutionEmailId());
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error(ex.getMessage());
        }
        return ccMemebrs;
    }
}
