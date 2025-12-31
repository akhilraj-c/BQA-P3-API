package com.mindteck.common.modules.program_structure.service.impl;

import com.mindteck.common.constants.Enum.*;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.*;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.program_structure.model.*;
import com.mindteck.common.modules.program_structure.service.ProgramStructureChecklistRepository;
import com.mindteck.common.repository.InstitutionFormRepository;
import com.mindteck.common.modules.program_structure.ProgramStructureRepository;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.program_structure.service.ProgramStructureService;
import com.mindteck.common.modules.standards.model.WorkFlowFileDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static com.mindteck.common.modules.program_structure.model.ProgramStructureFileType.COURSE_SPECIFICATION;
import static com.mindteck.common.modules.program_structure.model.ProgramStructureFileType.MAPPING_SCORE_CARD;

@Service
@Slf4j
public class ProgramStructureServiceImpl implements ProgramStructureService {

    @Autowired
    ProgramStructureRepository programStructureRepository;

    @Autowired
    InstitutionFormRepository institutionFormRepository;

    @Autowired
    private ProgramStructureChecklistRepository psChecklistRepository;


    @Override
    public PsChecklistResponse getChecklist(Long formUniqueId, Integer slNo) {
        if (formUniqueId == null) {
            throw new IllegalArgumentException("Program structure ID cannot be null");
        }
        if (slNo == null) {
            throw new IllegalArgumentException("Program structure ID cannot be null");
        }
        InstituteForm instForm =institutionFormRepository.getByFormUniqueId(formUniqueId);

        if (instForm==null) {
            throw new IllegalArgumentException("Form Unique Id :" + formUniqueId + " does not exist");
        }

        List<ProgramStructureChecklist> checklists = psChecklistRepository.getChecklistByFormUniqueIdAndSlNo(formUniqueId,slNo);

        PsChecklistResponseModel psChecklistResponseModel = new PsChecklistResponseModel();
        psChecklistResponseModel.setFormUniqueId(formUniqueId);
        psChecklistResponseModel.setSlNo(slNo);
        psChecklistResponseModel.setCriteria(
                checklists.stream()
                        .collect(Collectors.groupingBy(ProgramStructureChecklist::getCriterion))
                        .entrySet().stream()
                        .map(entry -> {
                            PsChecklistResponseModel.CriterionEntry criterionEntry = new PsChecklistResponseModel.CriterionEntry();
                            criterionEntry.setCriterion(entry.getKey());
                            criterionEntry.setSubCriteria(
                                    entry.getValue().stream()
                                            .map(checklist -> {
                                                PsChecklistResponseModel.CriterionEntry.SubCriterionEntry subCriterionEntry = new PsChecklistResponseModel.CriterionEntry.SubCriterionEntry();
                                                subCriterionEntry.setSubCriterion(checklist.getSubCriterion());
                                                subCriterionEntry.setStatus(checklist.getStatus());
                                                return subCriterionEntry;
                                            })
                                            .collect(Collectors.toList())
                            );
                            return criterionEntry;
                        })
                        .collect(Collectors.toList())
        );
        PsChecklistResponse response= new PsChecklistResponse();
        response.setData(psChecklistResponseModel);
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        response.setStatus(status);
        return response;
    }

    @Override
    @Transactional
    public SavePsChecklistResponseModel savePsChecklist(@Valid SavePsChecklistRequest request) {
        if (request.getFormUniqueId() == null) {
            throw new IllegalArgumentException("Form unique ID cannot be null");
        }

        if (request.getSlNo() == null) {
            throw new IllegalArgumentException("Sl no cannot be null");
        }
        if (request.getCriteria() == null || request.getCriteria().isEmpty()) {
            throw new IllegalArgumentException("Criteria list cannot be empty");
        }
        InstituteForm instForm = institutionFormRepository.getByFormUniqueId(request.getFormUniqueId());
        if (instForm==null) {
            throw new IllegalArgumentException("Form Unique Id with ID " + request.getFormUniqueId() + " does not exist");
        }
        List<ProgramStructureChecklist> checklists = new ArrayList<>();
        for (SavePsChecklistRequest.CriterionEntry criterionEntry : request.getCriteria()) {
            for (SavePsChecklistRequest.CriterionEntry.SubCriterionEntry subCriterionEntry : criterionEntry.getSubCriteria()) {
                ProgramStructureChecklist checklist = new ProgramStructureChecklist();
                checklist.setFormUniqueId(request.getFormUniqueId());
                checklist.setSlNo(request.getSlNo());
                checklist.setCriterion(criterionEntry.getCriterion());
                checklist.setSubCriterion(subCriterionEntry.getSubCriterion());
                checklist.setStatus(subCriterionEntry.getStatus());

                checklists.add(checklist);
            }
        }

        SavePsChecklistResponseModel responseModel =  new SavePsChecklistResponseModel();
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        responseModel.setStatus(status);
        SavePsChecklistResponse savePsChecklistResponse= new SavePsChecklistResponse();
        savePsChecklistResponse.setMessage("Verification Checklist saved successfully");
        responseModel.setData(savePsChecklistResponse);
        psChecklistRepository.deleteByFormUniqueIdIdAndSlNo(request.getFormUniqueId(),request.getSlNo());
        psChecklistRepository.saveAll(checklists);
        return responseModel;
    }


    @Override
    public GetProgramStructureResponse getProgramStructures(Long uniqueId) {
        InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(uniqueId);
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", uniqueId);
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        List<ProgramStructure> finalOut = new ArrayList<>();
        try {
            List<ProgramStructure> dataFromDB = programStructureRepository.getProgramStructures(uniqueId);
            finalOut = dataFromDB.stream()
                    .distinct()
                    .collect(Collectors.toList());

            if (!dataFromDB.isEmpty() && !finalOut.isEmpty()) {
                for (ProgramStructure ps : finalOut) {
                    ArrayList<WorkFlowFileDetails> mappingScoreCards = new ArrayList<>();
                    ArrayList<WorkFlowFileDetails> courseSpecs = new ArrayList<>();
                    for (ProgramStructure programStructure : dataFromDB) {
                        if (programStructure.equals(ps)) {
                            WorkFlowFileDetails fileDetails = new WorkFlowFileDetails();
                            fileDetails.setDescription(programStructure.getDescription());
                            fileDetails.setFile(programStructure.getFile());
                            fileDetails.setSubVersion(programStructure.getSubVersion());
                            fileDetails.setAmFileStatus(programStructure.getAmFileStatus());
                            fileDetails.setAmFileComment(programStructure.getAmFileComment());
                            fileDetails.setLatestAmFileCheck(programStructure.getLatestAmCheck());
                            if (programStructure.getFileType() == MAPPING_SCORE_CARD.ordinal()) {
                                mappingScoreCards.add(fileDetails);
                            } else {
                                courseSpecs.add(fileDetails);
                            }
                        }
                    }
                    mappingScoreCards.sort((o1, o2) -> {
                        if (o1.getSubVersion() == o2.getSubVersion())
                            return 0;
                        return o1.getSubVersion() < o2.getSubVersion() ? -1 : 1;
                    });
                    courseSpecs.sort((o1, o2) -> {
                        if (o1.getSubVersion() == o2.getSubVersion())
                            return 0;
                        return o1.getSubVersion() < o2.getSubVersion() ? -1 : 1;
                    });
                    ps.setFileDetails2(courseSpecs);
                    ps.setFileDetails(mappingScoreCards);
                }
            }

        } catch (Exception e) {

        }
        finalOut.sort((o1, o2) -> {
            if (o1.getSlNo() == o2.getSlNo())
                return 0;
            return o1.getSlNo() < o2.getSlNo() ? -1 : 1;
        });
        return getResponse(uniqueId, finalOut);
    }


    @Override
    @Transactional
    public SaveProgramStructureResponse saveProgramStructure(SaveProgramStructureRequest request) {
        InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        //     programStructureRepository.delete(request.getFormUniqueId());
        ProgramStructureResult result = saveProgramStructures(request.getFormUniqueId(), request.getProgramStructureList(), request.getDeleteAll());
        return getSaveResponse(request.getFormUniqueId(), result.saveCount(), result.rowCount());
    }

    @Override
    public ProgramStructureResult saveProgramStructures(Long formUniqueId, List<ProgramStructure> programStructureList, int deleteAll) {
        int saveCount = 0;
        int rowCount = 0;
        if(deleteAll == 1){
            programStructureRepository.delete(formUniqueId);
        }
        if (!Objects.isNull(programStructureList) && !programStructureList.isEmpty()) {
            for (ProgramStructure data : programStructureList) {
                if (Objects.isNull(data.getVersion())) {
                    LOGGER.error("Program structure version not valid:{}", data.getVersion());
                    throw new ServiceException(ErrorCode.PROGRAM_STRUCTURE_VERSION_NOT_VALID);
                }
                programStructureRepository.delete(formUniqueId, data.getUnitCode(), data.getVersion());
                data.setFormUniqueId(formUniqueId);
                rowCount = insertIndividualFiles(rowCount, data, data.getFileDetails(), MAPPING_SCORE_CARD.ordinal());
                rowCount = insertIndividualFiles(rowCount, data, data.getFileDetails2(), COURSE_SPECIFICATION.ordinal());
                saveCount++;
            }
        }
        ProgramStructureResult result = new ProgramStructureResult(saveCount, rowCount);
        return result;
    }

    public record ProgramStructureResult(int saveCount, int rowCount) { }

    private int insertIndividualFiles(int rowCount, ProgramStructure data, List<WorkFlowFileDetails> files, Integer fileType) {
        if (!Objects.isNull(files) && !files.isEmpty()) {
            for (WorkFlowFileDetails fileDetails : files) {
                try {
                    ProgramStructure cloneProgramStructure = (ProgramStructure) data.clone();
                    cloneProgramStructure.setFile(fileDetails.getFile());
                    cloneProgramStructure.setSubVersion(fileDetails.getSubVersion());
                    cloneProgramStructure.setDescription(fileDetails.getDescription());
                    cloneProgramStructure.setAmFileStatus(fileDetails.getAmFileStatus());
                    cloneProgramStructure.setAmFileComment(fileDetails.getAmFileComment());
                    cloneProgramStructure.setLatestAmCheck(fileDetails.getLatestAmFileCheck());
                    cloneProgramStructure.setFileType(fileType);
                    programStructureRepository.save(cloneProgramStructure);
                    rowCount++;
                } catch (CloneNotSupportedException exception) {
                    exception.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        return rowCount;
    }

    public GetProgramStructureResponse getResponse(Long formUniqueId, List<ProgramStructure> data) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return GetProgramStructureResponse.builder().status(status)
                .programStructureList(new ArrayList(data))
                .formUniqueId(formUniqueId)
                .build();
    }

    public SaveProgramStructureResponse getSaveResponse(Long formUniqueId, int saved, int rows) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return SaveProgramStructureResponse.builder().status(status)
                .saved(saved)
                .rowCount(rows)
                .formUniqueId(formUniqueId)
                .build();
    }




}
