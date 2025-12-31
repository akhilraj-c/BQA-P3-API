ALTER TABLE tbl_mail_template
 ADD COLUMN institute_cc INTEGER ,
 ADD COLUMN dfo_admin_cc INTEGER ,
 ADD COLUMN am_cc INTEGER ,
 ADD COLUMN dfo_member_cc INTEGER ,
 ADD COLUMN chief_cc INTEGER ,
 ADD COLUMN director_cc INTEGER ,
 ADD COLUMN ilep_cc INTEGER,
 ADD COLUMN institution_head_cc INTEGER NOT NULL;



UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=1, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='ILEP_SUBMIT_FINAL_SUMMARY';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='QAC_APPROVE_REPORT';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='QAC_UPDATED';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='AM_CREATE_SITE_VISIT';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='SITE_VISIT_COMPLETED';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='AM_GRANT_ACCESS_EVALUATION_PANEL';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='FACTUAL_ACCURACY_DEFFERED';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='USER_REGISTRATION';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='AM_ADMINISTRATIVE_CHECK';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='DFO_ASSIGN_AM';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='INSTITUTE_SIGN_NO_CONFLICT';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='DFOA_APPROVE_ILEP_PANEL';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='FIRST_MEETING_OUTCOMES';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='FIRST_EVALUATON_MEETING';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='SECOND_EVALUATON_MEETING';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='ILEP_PANEL_CREATE';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='DFOA_APPROVE_ILEP_PANEL';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='GRANT_ACCESS';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='QAC_FEEDBACK_SUBMITTED';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='NAC_SHARED';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='ON_MCO_UPLOAD';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='ON_NAC_APPROVAL';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='QAC_APPROVE_REPORT';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=1, am_cc=0, dfo_member_cc=0, chief_cc=1, director_cc=1, ilep_cc=0
     WHERE template_code='INSTITUTE_LISTING_CONDITION_FULFILMENT_REPORT_FACTUAL_LISTED';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=1, am_cc=0, dfo_member_cc=0, chief_cc=1, director_cc=1, ilep_cc=0
     WHERE template_code='INSTITUTE_LISTING_CONDITION_FULFILMENT_REPORT_FACTUAL_NOT_LISTED';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=1, am_cc=0, dfo_member_cc=0, chief_cc=1, director_cc=1, ilep_cc=0
     WHERE template_code='FACTUAL_ACCURACY_NOT_LISTED';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=1, am_cc=0, dfo_member_cc=0, chief_cc=1, director_cc=1, ilep_cc=0
     WHERE template_code='FACTUAL_ACCURACY_LISTED';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='ON_SHARED_CABINET';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='ON_FINAL_STATUS';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='ON_APPEAL_CREATE';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='INSTITUTION_REJECTED';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='INSTITUTE_APPLICATION_SCHEDULED';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='ON_APPEAL_APPROVED';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='DUE_DATE_WARNING';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='APPLICATION_TERMINATED';

UPDATE tbl_mail_template SET institute_cc=0, dfo_admin_cc=0, am_cc=0, dfo_member_cc=0, chief_cc=0, director_cc=0, ilep_cc=0
     WHERE template_code='INSTITUTE_SIGN_NON_CONFLICT';


UPDATE tbl_mail_template SET template_code='AM_ADMINISTRATIVE_CHECK_NOT_COMPLETED'
    WHERE template_code='AM_ADMINISTRATIVE_CHECK';



INSERT INTO tbl_mail_template(template_name, template_code, template_description, display_sequence, is_active)
    VALUES
        ('Admin check ok mail', 'AM_ADMINISTRATIVE_CHECK_COMPLETED', 'Mail to institution user to inform application is comleted', 41, 1),
        ('Admin check set deadline', 'CONDITION_FULFILMENT_ILEP_DEADLINE', 'AM should set a deadline for ILEP submission. An email should be sent to the ILEP with all the detail', 41, 1);

UPDATE tbl_mail_template SET template_subject='Institutional Listing – Acknowledgment', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
Dear {userName},<br><br>
                                                                    This message is to acknowledge that your application titled {instituteName} is received and will be forwarded to the evaluation stage. You will be contacted shortly to provide you with information related to the evaluation Panel. The application’s main contact is detailed below: <br>
                                                                    Application ID: {applicationId} <br>
                                                                    Application Manager Name:  {managerName}<br>
                                                                    Application Manager Contact Number:  {managerNumber}<br>
                                                                    Application Manager Email: {managerMail}<br>
                                                                     If any clarification is required, please do not hesitate to contact your application manager.  <br><br>
                                                                     Thank you for your cooperation.

                                                                    <br><br>
                                                                    DFO Team
                                                                                         </div>'
                  WHERE template_code='AM_ADMINISTRATIVE_CHECK_COMPLETED';



 UPDATE tbl_mail_template SET template_subject='  Institutional Listing – Report Factual Accuracy', template_body= '<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
                                                                                                                     Dear {userName},<br><br>

                                                                                                                    Trust this email finds you well.<br><br>
                                                                                                                    Thank you for submitting the Institutional Listing Application for {instituteName}.<br><br>
                                                                                                                    Please access your dashboard to view the Evaluation Report Draft along with the Arabic
                                                                                                                    and English Profiles.<br><br>
                                                                                                                    Kindly note that the report and profiles are drafts, confidential, provisional, and for
                                                                                                                    factual accuracy purposes only. I would appreciate it if you could check them for factual
                                                                                                                    accuracy and return them back to the General Directorate of National Qualifications
                                                                                                                    Framework & National Examinations with any written comment by {proposedDate}. If the General Directorate does not receive any reply by the aforementioned
                                                                                                                    date, the documents will be considered as there are no errors of facts.<br><br>
                                                                                                                    If you have any queries, please do not hesitate to contact 17562461 or GDQ@bqa.gov.bh

                                                                                                                                                                                                                                                                <br><br>
                                                                                                                                                                                                                                                                DFO Team
                                                                                                                                                                                                                                                                                     </div>'
    WHERE template_code = 'FACTUAL_ACCURACY_LISTED';

    UPDATE tbl_mail_template SET  template_body= '<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
                                                                                                                         Dear {userName},<br><br>
                                                                                                                        This email is to inform you that we have received the Condition Fulfilment for the
                                                                                                                        Institutional Listing Application titled {instituteName}. Please access your dashboard to
                                                                                                                        view the documents. Upon your kind perusal of the evidence, you are kindly requested to
                                                                                                                        draft the Condition Fulfillment Report and submit it by {proposedDate}.<br><br>
                                                                                                                        For more information, please contact the {managerName} through {managerMail} or {managerNumber}. <br><br>
                                                                                                                        Thank you.<br><br>
                                                                                                                       <b> DFO Team<b>
                                                                                                                                                                                                                                                                    <br><br>
                                                                                                                                                                                                                                                                    DFO Team
                                                                                                                                                                                                                                                                                         </div>'
        WHERE template_code = 'CONDITION_FULFILMENT_ILEP_DEADLINE';




    ALTER TABLE tbl_institute_form
    ADD COLUMN terminated_mail_sent INTEGER NOT NULL DEFAULT 0 AFTER Rejected_by_user_type,
    ADD COLUMN remainder_mail_sent INTEGER NOT NULL DEFAULT 0 AFTER Rejected_by_user_type;
