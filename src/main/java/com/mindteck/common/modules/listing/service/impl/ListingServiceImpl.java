package com.mindteck.common.modules.listing.service.impl;

import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.constants.Enum.StatusEnum;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.InstituteForm;
import com.mindteck.common.modules.listing.service.ListingService;
import com.mindteck.models_cas.ListedInstitute;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.repository_cas.ListingRepository;
import com.mindteck.common.modules.user.dao.UserDao;
import com.mindteck.common.modules.user.model.rest.GetInstituteListingDetailsResponse;
import com.mindteck.common.modules.user.model.rest.RegistrationRequest;
import com.mindteck.common.modules.user.model.rest.qp.ForeignQualification;
import com.mindteck.common.modules.user.model.rest.qp.NationalQualification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ListingServiceImpl implements ListingService {


    @Autowired
    ListingRepository listingRepository;

    @Autowired
    UserDao userDao;


    @Override
    public GetInstituteListingDetailsResponse getInstituteListingDetails(String email) {
        ListedInstitute listedInstitute = listingRepository.getInstitute(email);
        if (listedInstitute == null) {
            throw new ServiceException(ErrorCode.INSTITUTE_NOT_LISTED, " Email : " + email);
        }
        List<InstituteForm> instituteForms = userDao.getFormsByMailId(email);

        List<InstituteForm> out = instituteForms.stream().filter(instituteForm ->
                !Objects.isNull(instituteForm.getIsDraft()) && instituteForm.getIsDraft() == 1
        ).collect(Collectors.toList());

        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return GetInstituteListingDetailsResponse.builder().status(status)
                .data(listedInstitute)
//                .draftDetails(getDrafts(out))
                .build();
    }

//    private RegistrationRequest getDrafts(List<InstituteForm> instituteForms) {
//        if (instituteForms != null && !instituteForms.isEmpty()) {
//            RegistrationRequest request = new RegistrationRequest();
//            ArrayList<ForeignQualification> fq = new ArrayList<>();
//            ArrayList<NationalQualification> nq = new ArrayList<>();
//            InstituteForm instituteForm = instituteForms.get(0);
//            request.setInstitutionName(instituteForm.getInstitutionName());
//            request.setEmailId(instituteForm.getContactPersonEmail());
//            request.setInstAppLicNo(instituteForm.getInstAppLicNo());
//            request.setLicenseType(instituteForm.getLicenseType());
//            request.setRegulatoryOthersData(instituteForm.getRegulatoryOthersData());
//        //    request.setExpiryDateNotApplicable(instituteForm.getExpir());
//            request.setApprovalDocFile(instituteForm.getApprovalDocFile());
//            request.setIssueDate(instituteForm.getIssueDate());
//            request.setExpiryDate(instituteForm.getExpDate());
//            request.setIsBqaReviewed(instituteForm.getIsBqaReviewed());
//            request.setBqaReviewIssueDate(instituteForm.getReviewIssueDate());
//           // request.setBqaReviewResult(instituteForm.getRes());
//            request.setContactName(instituteForm.getContactPersonName());
//            request.setBqaReviewIssueDate(instituteForm.getReviewIssueDate());
//            request.setPositionTitle(instituteForm.getContactPersonTitle());
//            request.setContactNo(instituteForm.getContactPersonNumber());
//            request.setListingId(instituteForm.getListingId());
//            for (InstituteForm form : instituteForms) {
//                if(form.getQualificationType()==1){ // for national qualification
//                    NationalQualification title = new NationalQualification();
//                    title.setQualificationTitle(form.getQualificationTitle());
//                    title.setQualificationSize(form.getQualificationSize());
//                    try {
//                        Long plannedSubDate = Long.parseLong(form.getPlannedSubDate());
//                        title.setPlannedSubmissionDate(plannedSubDate);
//                    } catch (Exception ignore){
//
//                    }
//                    nq.add(title);
//
//                }else if (form.getQualificationType()==2){ //for foreign qualification
//                    ForeignQualification title = new ForeignQualification();
//                    title.setQualificationTitle(form.getQualificationTitle());
//                    title.setAwardingBody(form.getAwardingBody());
//                    title.setIncludedInOther(form.getIncludedInOther());
//                    title.setQualificationSize(form.getQualificationSize());
//                    try {
//                        Long plannedSubDate = Long.parseLong(form.getPlannedSubDate());
//                        title.setPlannedSubmissionDate(plannedSubDate);
//                    } catch (Exception ignore){
//
//                    }
//                    fq.add(title);
//                }
//
//            }
//            request.nationalQualifications = nq;
//            request.foreignQualifications = fq;
//            return request;
//        } else {
//            return null;
//        }
//    }

}
