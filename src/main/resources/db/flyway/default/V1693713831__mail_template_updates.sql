ALTER TABLE tbl_mail_template ADD COLUMN template_subject TEXT DEFAULT NULL AFTER is_active;

INSERT INTO tbl_mail_template(template_name, template_code, template_description, display_sequence)
    VALUES
        ('FACTUAL ACCURACY not LISTEDmail', 'FACTUAL_ACCURACY_NOT_LISTED', 'Mail to institution user to inform application is terminated', 41),
        ('FACTUAL ACCURACY LISTED mail', 'FACTUAL_ACCURACY_LISTED', 'Mail to institution user to inform application is terminated', 40),
        ('INSTITUTE SIGN NO CONFLICT mail', 'INSTITUTE_SIGN_NO_CONFLICT', 'Mail to ilep when institute sign no colfict', 42),
        ('INSTITUTE applicaton scheduled mail', 'INSTITUTE_APPLICATION_SCHEDULED', 'The message sent to the Institution after their application was scheduled', 42),
        ('Institutional Listing – Acknowledgment mail', 'INSTITUTE_ACKNOWLEDGEMENT_OK', ' After reviewing the Administrative Cheek submitted by the Institutions. If the admin check is Ok. The following message would be sent to the Institution', 42),
        ('Institutional Listing – Rejected  mail', 'INSTITUTE_ACKNOWLEDGEMENT_NOT_OK', ' If the admin check is not ok. The following message would be sent to the Institution.', 42),
        ('Institutional Listing –Visit Required Documents ', 'VISIT_REQUIRED_DOCUMENTS', 'Once the AM uploaded the visit requirements the following message should be sent to  the Institution', 42),
        ('Institutional Listing – Deferred for Condition Fulfilment mail', 'FACTUAL_ACCURACY_DEFFERED', '8.If the report judgment was Deferred for Condition Fulfilment, the following message  would be sent to the Institution', 40),
        ('Institutional Listing – Condition Fulfilment Report Factual Accuracy mail', 'INSTITUTE_LISTING_CONDITION_FULFILMENT_REPORT_FACTUAL_LISTED', 'if the report judgment was Listed, the following message would be sent to the  Institution', 42),
        ('Institutional Listing – Condition Fulfilment Report Factual Accuracy mail', 'INSTITUTE_LISTING_CONDITION_FULFILMENT_REPORT_FACTUAL_NOT_LISTED', ' If the report judgment was Not Listed, the following message would be sent to the  Institution', 42),
        ('Institutional Listing – Listed mail', 'INSTITUTE_LISTING_AFTER_CABINET_APPROVE', 'The application status appears to the Institution changed to “Listed”. The following  message would be sent to the institution', 42),
        ('Institutional Listing Application – Access Granted mail', 'AM_GRANT_ACCESS_EVALUATION_PANEL', 'Once the access is granted by the AM the application status will changed to ‘EvaluationAccess Granted', 42),
        ('Institutional Listing Application – First Evaluation Meeting mail', 'FIRST_EVALUATON_MEETING', 'Once the first meeting is set in the system the application status would be changed to  ‘Evaluation (First Meeting)’. the Panel should receive the following email', 42),
        ('Institutional Listing Application – First Meeting Outcomes mail', 'FIRST_MEETING_OUTCOMES', 'The application status appears for the ILEP should be changed ‘Evaluation (Report Draft)  The following message should be sent to the ILEP', 42),
        ('Institutional Listing Application – Second Evaluation Meeting  mail', 'SECOND_EVALUATON_MEETING', 'Once the second meeting is scheduled by the AM, the following message would be sent to the  institution', 42),
        ('Institutional Listing Application –Visit Extra Evidence mail', 'SITE_VISIT_EXTRA_EVIDENCE', 'AM should grant the ILEP the access to the extra Evidence and the following message   should be sent to the ILEP', 41),
        ('Institutional Listing Application – Post Visit mail', 'POST_VISIT_OUTCOME', 'The following message will be sent to the ILEP', 40),
        ('Institutional Listing Application – Condition Fulfilment mail', 'CONDITION_FULFILMENT_DEADLINE', 'The ILEP Should receive the following message', 40),
        ('Institutional Listing Application – Condition Fulfilment mail', 'REPORT_PANEL_CONFIRMATION_CHIEF', 'The ILEP Should receive the following message', 40),
        ('Institutional Listing – Confidentiality Agreement mail', 'SITE_VISIT_PREPRATION', 'Once the date is confirmed, the current system flow is not clear please consider the ', 41);

UPDATE tbl_mail_template SET template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">Dear {userName},<br><br>                                                                    Thank you for completing your registration for the Institutional Listing Process. Your registration form is under review by the Directorate of Framework Operations (DFO) Team, and you will be contacted shortly to provide you with information regarding the next step.<br><br>DFO Team<br><br><br></div>',
template_subject='Institutional Listing Registration'
    WHERE template_code='USER_REGISTRATION';



UPDATE tbl_mail_template SET template_subject='Institutional Listing Application – Not Scheduled', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">Dear {userName},<br><br>
                                                                    Thank you for registering your Institution for the Institutional Listing Process!<br><br>
                                                                    Kindly note that your application was not scheduled for the upcoming academic year. <br><br>
                                                                    For more information, please do not hesitate to contact 17562461 or GDQ@bqa.gov.bh.
                                                                    Thank you! <br><br>  DFO Team  </div>'
             WHERE template_code='INSTITUTION_REJECTED';



UPDATE tbl_mail_template SET template_subject='Institutional Listing – Administrative Check', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
Dear {userName},<br><br>
                                                                    Thank you for submitting your Institutional Listing Application for the
                                                                    Administrative Check Process!<br><br>
                                                                    The Directorate of National Framework Operations (DFO) of the Education &
                                                                    Training Quality Authority (BQA) conducts the Administrative Check processes to
                                                                    ensure the completeness and clarity of the application before acknowledging its
                                                                    receipt. The application’s main contact is detailed below:
                                                                    <br><br>
                                                                    Application Manager Name: {managerName}
                                                                    <p style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:left;color:#003366;">Application Manager Contact Number: {managerContactNumber}</p>
                                                                    Application Manager Email: {managerEmail}
                                                                    <br><br>
                                                                    You will be receiving the Administrative Check Outcomes shortly. Kindly note that
                                                                    for any further information or updates regarding your application, you may kindly
                                                                    contact your application manager.
                                                                    <br><br>
                                                                    DFO Team
                                                                                         </div>'
              WHERE template_code='DFO_ASSIGN_AM';


UPDATE tbl_mail_template SET template_subject='Institutional Listing – Administrative Check Outcome', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
Dear {userName},<br><br>
                                                                    This is to inform you that the DFO Team has conducted an Administrative Check for the
                                                                    submitted Institutional Listing Application for {instituteName}. The purpose of this
                                                                    process is to ensure the clarity and completeness of your application before
                                                                    acknowledging its receipt. <br><br>
                                                                    Your application form needs to be updated with more information for some indicators.
                                                                    Thus, complete the incomplete sections and attach the required evidence, whenever
                                                                     applicable. The deadline for submitting the Administrative Check requirement is {proposedDate}.
                                                                    <br><br>
                                                                    If any clarification is required, please do not hesitate to contact your application manager. Thank you for your cooperation.

                                                                    <br><br>
                                                                    DFO Team
                                                                                         </div>'
                  WHERE template_code='AM_ADMINISTRATIVE_CHECK';


UPDATE tbl_mail_template SET template_subject='Institutional Listing – Panel Conflict of Interest Declaration', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
Dear {userName},<br><br>
                                                                                         Hope this message finds you well!<br><br>
                                                                                         We are pleased to let you know that your application titled {instituteName} is now
                                                                                         in the evaluation stage. However, before taking this application any further, we need
                                                                                         your declaration of any conflict of interest regarding the proposed Institutional
                                                                                         Listing Evaluation Panel members. <br><br>
                                                                                         Please refer to your dashboard and review the BIOs of the nominated Panel - It would
                                                                                         be highly appreciated if you could declare any conflict of interest within two working
                                                                                         days.<br><br>
                                                                                         Your prompt response is highly appreciated, as it will expedite the evaluation process.
                                                                                         Thank you very much!

                                                                                         <br><br>
                                                                                         DFO Team
                                                                                                              </div>'
    WHERE template_code='DFOA_APPROVE_ILEP_PANEL';



UPDATE tbl_mail_template SET template_subject=' Institutional Listing – Report Factual Accuracy', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
Dear {userName},<br><br>
                                                                                                                                         Trust this email finds you well. <br><br>
                                                                                                                                         Thank you for submitting the Institutional Listing Application for {instituteName}.<br><br>
                                                                                                                                         We would like to advise you that the Overall Judgment of the Evaluation Panel for the
                                                                                                                                         aforementioned Institution is: “Not Listed”. Please access your dashboard to view the
                                                                                                                                         Evaluation Report Draft for more details regarding the above judgment. Kindly note
                                                                                                                                         that the report is confidential, provisional and for factual accuracy purposes only.<br><br>
                                                                                                                                         It would be appreciated if you could check the report for factual accuracy and return it
                                                                                                                                         back to the General Directorate of National Qualifications Framework & National
                                                                                                                                         Examinations with any written comment by {proposedDate}. If the
                                                                                                                                         General Directorate does not receive any reply by then, the document will be considered
                                                                                                                                         as there are no errors of facts.<br><br>
                                                                                                                                         Please note that a full Institutional Listing Application re-submission will be required
                                                                                                                                         but this cannot be submitted earlier than six months of the above-mentioned date. The
                                                                                                                                         resubmitted application will be processed as per the GDQ plans and schedule.<br><br>
                                                                                                                                         Kindly note that if you wish to appeal against the judgment, you need to submit a formal
                                                                                                                                         letter clearly stating the case and grounds for appeal along with the appeal form and
                                                                                                                                         supported by relevant evidence, addressed to the General Director within five working
                                                                                                                                         days of receiving this letter.<br><br>
                                                                                                                                         For more details, refer to BQA’s website, www.bqa.gov.bh.
                                                                                                                                         <br><br>
                                                                                                                                         DFO Team
                                                                                                                                                              </div>'
        WHERE template_code='FACTUAL_ACCURACY_NOT_LISTED';



 UPDATE tbl_mail_template SET template_subject='  Institutional Listing – Report Factual Accuracy', template_body= '<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
                                                                                                                     Dear {userName},<br><br>

                                                                                                                    This message is to inform you that a new Institutional Listing Application titled {instituteName}  was assigned to you. Please check your dashboard to fill out the Conflict-of-Interest
                                                                                                                    Declaration Form.  <br><br>

                                                                                                                    For more information, please contact the {amName} through {amMail} or {amPhone}.<br><br> Thank you!

                                                                                                                                                                                                                                                                <br><br>
                                                                                                                                                                                                                                                                DFO Team
                                                                                                                                                                                                                                                                                     </div>'
    WHERE template_code = 'FACTUAL_ACCURACY_LISTED';


UPDATE tbl_mail_template SET template_subject='Institutional Listing Application - Assigned ', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
                                                                                                               Dear {userName},<br><br>

                                                                                                              This message is to inform you that a new Institutional Listing Application titled  {instituteName}  was assigned to you. Please check your dashboard to fill out the Conflict-of-Interest
                                                                                                              Declaration Form.
                                                                                                              For more information, please contact the {amName} through {amMail} or {amPhone}.
                                                                                                              Thank you!
                                                                                                              DFO Team

                                                                                                                                                                                                                                                          <br><br>
                                                                                                                                                                                                                                                          DFO Team
                                                                                                                                                                                                                                                                               </div>
'
    WHERE template_code='INSTITUTE_SIGN_NO_CONFLICT';


UPDATE tbl_mail_template SET template_subject='Institutional Listing Application Submission Information', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">Dear {userName},<br><br>
                                                                                                                                             Thank you for registering your Institution for the Institutional Listing Process!<br><br>
                                                                                                                                               The Directorate of National Framework Operations (DFO) of the Education & Training
                                                                                                                                               Quality Authority (BQA) would like to inform you that your Institution is scheduled to
                                                                                                                                               submit the Institutional Listing Application for the Institution titled {instituteName}
                                                                                                                                               by {submissionDate}.<br><br>
                                                                                                                                               To facilitate this process and provide further assistance, BQA developed an online
                                                                                                                                               capacity-building workshop available on the BQA website through this <a href="http://bqa-material.s3-website.me-south-1.amazonaws.com/">link</a>. You can start
                                                                                                                                               filling in the Institutional Listing Application Form through the following link:<br><br>
                                                                                                                                               {applicationUrl}<br><br>
                                                                                                                                               <b>Please use the following login details:</b><br>
                                                                                                                                               Username: {userEmail}<br>
                                                                                                                                               Password: {password}<br><br>
                                                                                                                                               Please arrange to change the password. The NQF Handbook for Institutions is available
                                                                                                                                               on the BQA website through this link, and the Institutional Listing Standards are available
                                                                                                                                               through this link.<br><br>
                                                                                                                                               At any moment, you can visit our website, www.bqa.gov.bh, for all documents. If you
                                                                                                                                               have any queries, please do not hesitate to contact us by email at GDQ@bqa.gov.bh
                                                                                                                                                 <br><br>
                                                                                                                                                 DFO Team
                                                                                                                                                                      </div>'
    WHERE template_code='INSTITUTE_APPLICATION_SCHEDULED';




UPDATE tbl_mail_template SET template_subject='Institutional Listing – Acknowledgment', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">Dear {userName},<br><br>
                                                                                                                               This message is to acknowledge that your application titled {instituteName} is
                                                                                                                               received and will be forwarded to the evaluation stage. You will be contacted shortly
                                                                                                                               to provide you with information related to the evaluation Panel. The application’s
                                                                                                                               main contact is detailed below:
                                                                                                                               <br><br>
                                                                                                                               Application ID: {applicationId}<br>
                                                                                                                               Application Manager Name: {managerName}<br>
                                                                                                                               Application Manager Contact Number:  {managerNumber}<br>
                                                                                                                               Application Manager Email:  {managerMail}<br><br>
                                                                                                                               If any clarification is required, please do not hesitate to contact your application
                                                                                                                               manager.
                                                                                                                               Thank you for your cooperation.
                                                                                                                               <br><br>
                                                                                                                               If any clarification is required, please do not hesitate to contact your application manager. Thank you for your cooperation.

                                                                                                                               <br><br>
                                                                                                                               DFO Team
                                                                                                                                                    </div>'
    WHERE template_code='INSTITUTE_ACKNOWLEDGEMENT_OK';



UPDATE tbl_mail_template SET template_subject = 'Institutional Listing – Rejected', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">Dear {userName},<br><br>
                                                                                                                           This message is to acknowledge that your application titled {instituteName} does not
                                                                                                                           pass the administrative check stage and, thus, was rejected. <br><br>
                                                                                                                           For more information, please do not hesitate to contact 17562461 or GDQ@bqa.gov.bh
                                                                                                                           <br><br>
                                                                                                                           DFO Team
                                                                                                                                                </div>'
    WHERE template_code='INSTITUTE_ACKNOWLEDGEMENT_NOT_OK';



UPDATE tbl_mail_template SET template_subject=' Institutional Listing – Confidentiality Agreement', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">Dear {userName},<br><br>
                                                                                                                                           This email is to inform you that the visit for the application titled {instituteName}  is
                                                                                                                                           confirmed to be held on {dueDate}. Please access your dashboard to sign the Visit
                                                                                                                                           Confidentiality Agreement and find the visit documents and information. <br><br>
                                                                                                                                           For more information, please contact the {managerName} through {managerMail} or {managerNumber}. <br><br>
                                                                                                                                           Thank you.

                                                                                                                                           <br><br>
                                                                                                                                           DFO Team
                                                                                                                                                                </div>'
    WHERE template_code='SITE_VISIT_PREPRATION';



UPDATE tbl_mail_template SET template_subject='Institutional Listing –Visit Required Documents', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">Dear {userName},<br><br>
                                                                                                                                        This message is to inform you that the Visit Agenda, the List of Evidence Required for the
                                                                                                                                        Virtual Visit and Virtual Visit Guidelines are now available on the system. <br><br>
                                                                                                                                        You are kindly requested to submit a filled-out copy of the agenda and the required
                                                                                                                                        evidence by {dueDate}. <br><br>
                                                                                                                                        Invitations to the virtual visit will be sent to your team after receiving the agenda.
                                                                                                                                        For more information, please contact the {managerName} through {managerMail} or {managerNumber}. <br><br>
                                                                                                                                        Thank you

                                                                                                                                        <br><br>
                                                                                                                                        DFO Team
                                                                                                                                                             </div>'
    WHERE template_code = 'VISIT_REQUIRED_DOCUMENTS';


UPDATE tbl_mail_template SET template_subject= 'Institutional Listing – Deferred for Condition Fulfilment', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">Dear {userName},<br><br>
                                                                                                                                                   Trust this email finds you well.  <br><br>
                                                                                                                                                   Thank you for submitting the Institutional Listing Application for {instituteName} . <br><br>
                                                                                                                                                   We would like to advise you that the Overall Judgement of the Evaluation Panel for the
                                                                                                                                                   aforementioned application is: ‘Deferred for Condition Fulfilment’. Please access your
                                                                                                                                                   dashboard to view the Evaluation Report Draft. Kindly note that the condition(s) stated
                                                                                                                                                   in the report need to be addressed within  {dueDate}
                                                                                                                                                   months from the date of receiving the report prior to any further processing. Please refer
                                                                                                                                                   to the report for factual accuracy and for more details regarding the condition(s). <br><br>
                                                                                                                                                   Please note that failing to address the condition within the specified timeframe would
                                                                                                                                                   result in changing the Overall Judgement to ‘Not Listed’, and a full Institutional Listing
                                                                                                                                                   Application re-submission will be required. Kindly note that the report is confidential,
                                                                                                                                                   provisional and for internal purposes only. <br><br>
                                                                                                                                                   Please respond by providing all necessary evidence detailing how the condition(s) are
                                                                                                                                                   addressed as part of the Condition Fulfilment Form.
                                                                                                                                                   If you have any queries, please do not hesitate to contact 17562461 or GDQ@bqa.gov.bh.
                                                                                                                                                   <br><br>
                                                                                                                                                   DFO Team
                                                                                                                                                                        </div>'
  WHERE template_code = 'FACTUAL_ACCURACY_DEFFERED';


UPDATE tbl_mail_template SET template_subject = 'Institutional Listing – Condition Fulfilment Report Factual Accuracy', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">Dear {userName},<br><br>
                                                                                                                                                               Trust this email finds you well!<br><br>
                                                                                                                                                               Thank you for submitting the condition fulfilment form for Institutional Listing
                                                                                                                                                               Application for {instituteName} .<br><br>
                                                                                                                                                               Please access your dashboard to view the Condition Fulfilment Report Draft along with
                                                                                                                                                               the Arabic and English Profiles. <br><br>
                                                                                                                                                               Kindly note that the report and profiles are drafts, confidential, provisional, and for
                                                                                                                                                               factual accuracy purposes only. I would appreciate it if you could check them for factual
                                                                                                                                                               accuracy and return them back to the General Directorate of National Qualifications
                                                                                                                                                               Framework & National Examinations with any written comment by {dueDate}. If the General Directorate does not receive any reply by the
                                                                                                                                                               aforementioned date, the documents will be considered as there are no errors of facts. <br><br>
                                                                                                                                                               If you have any queries, please do not hesitate to contact 17562461 or GDQ@bqa.gov.bh.
                                                                                                                                                               <br><br>
                                                                                                                                                               DFO Team
                                                                                                                                                                                    </div>'
 WHERE template_code = 'INSTITUTE_LISTING_CONDITION_FULFILMENT_REPORT_FACTUAL_LISTED';



UPDATE tbl_mail_template SET template_subject = 'Institutional Listing – Condition Fulfilment Report Factual Accuracy',template_body = '<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">Dear {userName},<br><br>
                                                                         Trust this email finds you well. <br><br>
                                                                         Thank you for submitting the condition fulfilment form for Institutional Listing
                                                                         Application for {instituteName}.<br><br>
                                                                         We would like to advise you that the Overall Judgment of the Evaluation Panel for the
                                                                         aforementioned Institution is: “Not Listed”. Please access your dashboard to view the
                                                                         Evaluation Report Draft for more details regarding the above judgment. Kindly note
                                                                         that the report is confidential, provisional and for factual accuracy purposes only.
                                                                         It would be appreciated if you could check the report for factual accuracy and return it
                                                                         back to the General Directorate of National Qualifications Framework & National
                                                                         Examinations with any written comment by {dueDate}. If the
                                                                         General Directorate does not receive any reply by then, the document will be considered
                                                                         as there are no errors of facts.<br><br>
                                                                         Please note that a full Institutional Listing Application re-submission will be required,
                                                                         but this cannot be submitted earlier than six months of the above-mentioned date. The
                                                                         resubmitted application will be processed as per the GDQ plans and schedule.<br><br>
                                                                         Kindly note that if you wish to appeal against the judgment, you need to submit a formal
                                                                         letter clearly stating the case and grounds for appeal along with the appeal form and
                                                                         supported by relevant evidence, addressed to the General Director within five working
                                                                         days of receiving this letter.<br><br>
                                                                         For more details, refer to BQA’s website, www.bqa.gov.bh.
                                                                         <br><br>
                                                                         DFO Team
                                                                                              </div>'
 WHERE template_code = 'INSTITUTE_LISTING_CONDITION_FULFILMENT_REPORT_FACTUAL_NOT_LISTED';




 UPDATE tbl_mail_template SET template_subject = 'Institutional Listing – Listed', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">Dear {userName},<br><br>
                                                                                                                          Congratulations! <br><br>
                                                                                                                          This is to inform you that your Institution titled {instituteName} is officially listed on
                                                                                                                          the NQF Register. You can view the Institution details and profiles through the following
                                                                                                                          link:<br><br>
                                                                                                                          Listed Institutions List (bqa.gov.bh)<br><br>
                                                                                                                          Thank you!
                                                                                                                          <br><br>
                                                                                                                          DFO Team
                                                                                                                                               </div>'
    WHERE template_code='INSTITUTE_LISTING_AFTER_CABINET_APPROVE';




UPDATE tbl_mail_template SET template_subject = 'Institutional Listing Application – Access Granted', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
                                                                                                                      Dear {userName},<br><br>

                                                                                                                     This message is to inform you that you grated the access to the Institutional Listing
                                                                                                                     Application titled {instituteName}. You can now view the application form and evidence.
                                                                                                                     For more information, please contact the {managerName} through {managerMail} or {managerNumber}.<br><br> Thank you!

                                                                                                                                                                                                                                                                 <br><br>
                                                                                                                                                                                                                                                                 DFO Team
                                                                                                                                                                                                                                                                                      </div>'
    WHERE template_code='AM_GRANT_ACCESS_EVALUATION_PANEL';



UPDATE tbl_mail_template SET template_subject='Institutional Listing Application – First Evaluation Meeting', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
                                                                                                                              Dear {userName},<br><br>

                                                                                                                             This message is to inform you that the first evaluation meeting is scheduled to be held on  {meetingDate} at {meetingTime}. Please refer to your dashboard to view the application. The
                                                                                                                             objectives of the meeting are:  <br><br>
                                                                                                                             <ul>
                                                                                                                             <li> Recap training for the validator/ validation panel members</li>
                                                                                                                             <li> Set the time for the next meeting referring to the specified time frame based on the
                                                                                                                             qualification size and propose a suitable date for the validation event based on processing </li>
                                                                                                                             </ul>
                                                                                                                             timeframe.
                                                                                                                             For more information, please contact the {managerName} through {managerMail} or {managerNumber} .<br><br> Thank you!

                                                                                                                                                                                                                                                                         <br><br>
                                                                                                                                                                                                                                                                         DFO Team
                                                                                                                                                                                                                                                                                              </div>'
	WHERE template_code='FIRST_EVALUATON_MEETING';

UPDATE tbl_mail_template SET template_subject='Institutional Listing Application – First Meeting Outcomes', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
                                                                                                                            Dear {userName},<br><br>

                                                                                                                           Thank you for attending the First Evaluation Meeting!
                                                                                                                           This message is to inform you that the first meeting documents and outcomes are now
                                                                                                                           available on the system. Please refer to your dashboard to view documents. You are kindly
                                                                                                                           required to submit the following, before {deadLine} :
                                                                                                                            <br><br>
                                                                                                                                <ul>
                                                                                                                            <li> The Evaluation Report through the system. </li>
                                                                                                                           <li>  Upload the Extra Evidence and Question From to the system. </li>                                     </ul>
                                                                                                                           For more information, please contact the {managerName} through {managerMail} or {managerNumber}.<br><br> Thank you!

                                                                                                                                                                                                                                                                       <br><br>
                                                                                                                                                                                                                                                                       DFO Team
                                                                                                                                                                                                                                                                                            </div>
'
	WHERE template_code='FIRST_MEETING_OUTCOMES';



UPDATE tbl_mail_template SET template_subject='Institutional Listing Application – Second Evaluation Meeting', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
                                                                                                                               Dear {userName},<br><br>

                                                                                                                              This message is to inform you that the second evaluation meeting is scheduled to be held on {meetingDate} at {meetingTime}.
                                                                                                                              For more information, please contact the {managerName} through {managerMail} or {managerNumber}.<br><br> Thank you!

                                                                                                                                                                                                                                                                          <br><br>
                                                                                                                                                                                                                                                                          DFO Team
                                                                                                                                                                                                                                                                                               </div>
'
	WHERE template_code='SECOND_EVALUATON_MEETING';




UPDATE tbl_mail_template SET template_subject='Institutional Listing Application –Visit', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
                                                                                                          Dear {userName},<br><br>

                                                                                                         The email is to inform you that the visit for the application titled {instituteName} is confirmed
                                                                                                         to be held on {visitDate} . Please access your dashboard to find the visit documents and
                                                                                                         information.
                                                                                                         For more information, please contact the {managerName} through {managerMail} or {managerNumber}.<br><br> Thank you!

                                                                                                                                                                                                                                                     <br><br>
                                                                                                                                                                                                                                                     DFO Team
                                                                                                                                                                                                                                                                          </div>'
	WHERE template_code='AM_CREATE_SITE_VISIT';





UPDATE tbl_mail_template SET template_subject='Institutional Listing Application –Visit Extra Evidence', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
                                                                                                                         Dear {userName},<br><br>

                                                                                                                        This message is to inform you that the institution has uploaded the extra evidence required for
                                                                                                                        the visit. Please access your dashboard to review the evidence and fill in the evidence findings
                                                                                                                        form and submit it back to the system maximum by  {frstDate}.
                                                                                                                        For more information, please contact the {managerName} through {managerMail} or {managerNumber}.<br><br> Thank you!

                                                                                                                                                                                                                                                                    <br><br>
                                                                                                                                                                                                                                                                    DFO Team
                                                                                                                                                                                                                                                                                         </div>'
	WHERE template_code='SITE_VISIT_EXTRA_EVIDENCE';

UPDATE tbl_mail_template SET template_subject='Institutional Listing Application – Post Visit', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
                                                                                                                Dear {userName},<br><br>

                                                                                                               Many thanks for taking part and contributing to {instituteName}  Institutional Listing
                                                                                                               Visit.
                                                                                                               You are kindly requested to incorporate the conditions and suggestions covered in the
                                                                                                               meeting protocol and spoken feedback and visit findings into the report and send the final
                                                                                                               version by {frstDate}.
                                                                                                               Also, it would be greatly appreciated if you upload the filled Evidence Form and List of
                                                                                                               Questions at your convenience.
                                                                                                               For more information, please contact the {managerName} through {managerMail} or {managerNumber}.<br><br> Thank you!

                                                                                                                                                                                                                                                           <br><br>
                                                                                                                                                                                                                                                           DFO Team
                                                                                                                                                                                                                                                                                </div>
'
	WHERE template_code='POST_VISIT_OUTCOME';




UPDATE tbl_mail_template SET template_subject='Institutional Listing Application – Condition Fulfilment', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
                                                                                                                          Dear {userName},<br><br>

                                                                                                                         Hope this email finds you well!
                                                                                                                         We would like to thank you on behalf of myself and the BQA team for your hard work.
                                                                                                                         Your contribution and hard work during the past few months are very much appreciated.
                                                                                                                         Please access your dashboard, where you can find the final evaluation report for your
                                                                                                                         kind perusal and confirmation. The edited version is based on your esteemed
                                                                                                                         contribution, comments, findings of the visit and the NQF IL standards. I look forward to
                                                                                                                         receiving your confirmation by {frstDate}.
                                                                                                                         For more information, please contact the {managerName} through {managerMail} or {managerNumber}.<br><br> Thank you!

                                                                                                                                                                                                                                                                     <br><br>
                                                                                                                                                                                                                                                                     DFO Team
                                                                                                                                                                                                                                                                                          </div>'
	WHERE template_code='REPORT_PANEL_CONFIRMATION_CHIEF';


UPDATE tbl_mail_template SET template_subject='Institutional Listing Application – Condition Fulfilment', template_body='<div style="font-family:Poppins, Helvetica, Arial, sans-serif;font-size:20px;font-weight:300;line-height:30px;text-align:justify;color:#003366;">
                                                                                                                          Dear {userName},<br><br>

                                                                                                                         This email is to inform you that we have received the Condition Fulfilment for the
                                                                                                                         Institutional Listing Application titled {instituteName}. Please access your dashboard to
                                                                                                                         view the documents. Upon your kind perusal of the evidence, you are kindly requested to
                                                                                                                         draft the Condition Fulfillment Report and submit it by {proposedDate}.

                                                                                                                         For more information, please contact the {managerName} through {managerMail} or {managerNumber}.<br><br> Thank you!

                                                                                                                                                                                                                                                                     <br><br>
                                                                                                                                                                                                                                                                     DFO Team
                                                                                                                                                                                                                                                                                          </div>
'
	WHERE template_code='CONDITION_FULFILMENT_DEADLINE';
