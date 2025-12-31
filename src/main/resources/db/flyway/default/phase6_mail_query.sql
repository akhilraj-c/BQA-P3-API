
ALTER TABLE tbl_mail_template
ADD COLUMN display_sequence SMALLINT NULL AFTER template_body,
ADD COLUMN is_active SMALLINT DEFAULT 1 NOT NULL AFTER display_sequence;

TRUNCATE TABLE tbl_mail_template;

INSERT INTO tbl_mail_template(template_name, template_code, template_description, display_sequence)
    VALUES
        ('User registration mail', 'USER_REGISTRATION', 'Mail with license details after application submission to institution user', 1),
        ('Institution approval mail', 'INSTITUTION_APPROVED', 'To send mail to institution with details on approving the rquest', 2),
        ('Institution application rejected', 'INSTITUTION_REJECTED', 'To send mail to institution for rejection of application', 3),
        ('Institution application terminated', 'INSTITUTION_TERMINATED', 'To send mail to institution on termination of application', 4),
        ('On Dfo Assign AM Mail', 'DFO_ASSIGN_AM', 'To get details of APPLICATION MANAGER with contact details', 5),
        ('On administrative check by application manager', 'AM_ADMINISTRATIVE_CHECK',
                   'On administrative check by application manager to institute to complete the incomplete section as additional task with a deadline', 6),
        ('On Application Manager create ILEP Panel', 'ILEP_PANEL_CREATE', 'On Application Manager create ILEP Panel to DFO director to approve the ILEP panel', 7),
        ('On DFO Director approves the ILEP panel', 'DFOA_APPROVE_ILEP_PANEL', 'On DFO Director approves the ILEP panel to INSTITUTE user to sign no conflict form', 8),
        ('On Institution sign sign non conflict mail', 'INSTITUTE_SIGN_NON_CONFLICT', 'On institute  sign no conflict form agianist ILEP', 9),
        ('On Application manager not approving conflict', 'AM_NOT_APPROVE_CONFLICT',
                   'On Application manager not approving the conflict form against ILEP panel by institute to ILEP Panel to sign no conflict with institution details', 10),
        ('On ILEP members signing the no conflict form', 'ILEP_SIGN_NON_CONFLICT',
                   'On ILEP members signing the no conflict form to Application manager to schedule meeting with GDQ/DFO director', 11),
        ('On Grant access', 'GRANT_ACCESS', 'On institute  sign no conflict form agianist ILEP', 12),
        ('On Application manager create site visit', 'AM_CREATE_SITE_VISIT',
                  'On Application manager creating site visit with given options to INSTITUTE user to request for date extension or accept the date', 13),
        ('On site visit completed', 'SITE_VISIT_COMPLETED', 'On site visit completed', 14),
        ('On Institute signs no confidentiality', 'INSTITUE_SIGN_NON_CONFIDENTIALITY', 'On Institute signs no confidentiality agreement to all ILEP members to update event documnet forms ', 15),
        ('On ILEP submit final summary', 'ILEP_SUBMIT_FINAL_SUMMARY', 'On ILEP member update outcome summary with final jufgement to Application manager to update site visit required status', 16),
        ('On Application manage approves ILEP report', 'AM_APPROVE_ILEP_REPORT', 'On Application manage approves the report updated by ILEP Panel DFO Director fo review and approval', 17),
        ('On QAC feedback submitted', 'QAC_FEEDBACK_SUBMITTED', 'On qac feedback submitted send mail to DFO director', 18),
        ('On QAC updated', 'QAC_UPDATED', 'On qac updated', 19),
        ('On NAC shared', 'NAC_SHARED', 'On NAC shared', 20),
        ('On QAC approving the report', 'QAC_APPROVE_REPORT', 'On QAC approving the report to Application manager/DFO to send document for factual check |', 21),
        ('On Factual accuracy started', 'FACTUAL_ACCURACY_STARTED', 'On factual accuracy started', 22),
        ('On defered', 'DEFERED', 'On Defered staus', 23),
        ('On appeal create', 'ON_APPEAL_CREATE', 'On Appeal create', 24),
        ('On appeal approved', 'ON_APPEAL_APPROVED', 'On Appeal approved', 25),
        (' ON NAC approval', 'ON_NAC_APPROVAL', 'On NAC approval to MCO', 26),
        ('On MCO upload ', 'ON_MCO_UPLOAD', 'On MCO upload to DFo chief', 27),
        ('When shared to Cabinet', 'ON_SHARED_CABINET', 'When shared to cabinet send mail  to DFO admin', 28),
        ('ON final status', 'ON_FINAL_STATUS', 'On Final status to institute user', 29),
        ('Overdue email', 'DUE_DATE_WARNING', 'To send mail to warn regarding due dates', 30),
        ('Reminder email ', 'REMAINDER_MAIL', 'To send remainder mail', 31),
        ('Forgot Password mail', 'FORGOT_PASSWORD', 'forgot password mail with details', 32);



UPDATE tbl_mail_template
    SET template_body = 'Dear {userName},<br><br>
                                                                 Thank you for registering for {licenseName}, Kindly note that BQA is still reviewing your registration form and will schedule you accordingly for the next process
'
        WHERE template_code='USER_REGISTRATION';

UPDATE tbl_mail_template
    SET template_body = 'Dear {contactName},<br><br>
                                                                 Your clientRegistration has been scheduled to be submitted by {submissionDate} . You can start filling in the clientRegistration form through the following link. <br><br>
                                                                 Please use the following login details to login to the clientRegistration form.  <br>
                                                                 <p>Username: {userName}</p>
                                                                 <p>Password: {password}</p>

'
        WHERE template_code='INSTITUTION_APPROVED';

UPDATE tbl_mail_template
    SET template_body = '
Your clientRegistration is <a style="color: red;">Rejected</a>
'
        WHERE template_code='INSTITUTION_REJECTED';

UPDATE tbl_mail_template
    SET template_body = 'Dear {userName},<br><br>
                                             Your application is ready for administrative check <br><br>
                                             Application ID: {applicationId}
                                             <br>
                                             Application Manager Name: {managerName}
                                             <br>
                                             Application Manager Contact Number: {managerContactNumber}
                                             <br>
                                             Application Manager Email: {managerEmail}
                                             <br>
                                             Application Date of Received: {Time}
                                             <br>'
        WHERE template_code='DFO_ASSIGN_AM';


UPDATE tbl_mail_template
    SET template_body = 'Dear {userName},<br><br>
                                                                 Your application is assigned for additional information required, please complete the incomplete sections<br><br>
                                                                 Application ID: {applicationId}
                                                                 <br>
                                                                 Last date to submit:
                                                                 <br>                       <br>'
        WHERE template_code='AM_ADMINISTRATIVE_CHECK';

UPDATE tbl_mail_template
    SET template_body = 'Dear {userName},<br><br>'
        WHERE template_code='ILEP_PANEL_CREATE';

UPDATE tbl_mail_template
    SET template_body = 'Dear {userName},<br><br>
                                                                 One application is assigned to you and your panel by Application Manager and DFO , Please review and do action [Conflict or No-conflict]]<br><br>
                                                                 Application ID: {applicationId}
                                                                 <br>
                                                                 <br>                       <br>

'
        WHERE template_code='DFOA_APPROVE_ILEP_PANEL';

UPDATE tbl_mail_template
    SET template_body = '	Dear {userName},<br><br>
                                                                 Your application is assigned for Site visit status by Application manager, Please accept the date or if required request for a date extension.<br><br>
                                                                 Application ID: {applicationId}
                                                                 <br>
                                                                 <br>                       <br>
'
        WHERE template_code='AM_CREATE_SITE_VISIT';

UPDATE tbl_mail_template
    SET template_body = 'Dear {userName},<br><br>
                                                                 One application is completed the Non-conflict for both parties, please complete the following actions as per the covinence<br><br>
                                                                 Application ID:  {applicationId}
                                                                 <br>
                                                                 1. Schedule meeting with GDQ/DFO Director

                                                                 <br>                       <br>
                                                                 2. Upload MOM after the meeting

                                                                 <br>                       <br>
                                                                 3. Set site visit required status and assign the date'
        WHERE template_code='ILEP_SUBMIT_FINAL_SUMMARY';

UPDATE tbl_mail_template
    SET template_body = 'Dear {userName},<br><br>
                                                 You recently requested to reset the password for your account. Click the button below to proceed. If you did not request a password reset, please ignore this email<br><br>
                                                 <a  href="{resetLink}" target="_blank"> {resetLink}</a>
'
        WHERE template_code='FORGOT_PASSWORD';


UPDATE tbl_mail_template
    SET template_body = 'Dear {userName},<br><br>
                                                                 One application is ILEP conflict signed by both parties, please schedule the meeting with GDQ/DFO Director<br><br>
                                                                 Application ID:  {applicationId}
                                                                 <br>
                                                                 <br>                       <br>
'
        WHERE template_code='ILEP_SIGN_NON_CONFLICT';



UPDATE tbl_mail_template
    SET template_body = 'Dear {userName},<br><br>
                                                                 One application is assigned to you and your panel by Application Manager and DFO , Please review and do action [Conflict or No-conflict]]<br><br>
                                                                 Application ID: {applicationId}
                                                                 <br>
                                                                 <br>                       <br>'
        WHERE template_code='AM_NOT_APPROVE_CONFLICT';


UPDATE tbl_mail_template
           SET is_active=0
           WHERE template_code='ILEP_SIGN_NON_CONFLICT';


UPDATE tbl_mail_template
           SET is_active=0
           WHERE template_code='ILEP_SIGN_NON_CONFLICT';


UPDATE tbl_mail_template
           SET is_active=0
           WHERE template_code='AM_NOT_APPROVE_CONFLICT';





--           new


UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                         	Application -{applicationId}  is assigned to you, please complete your action

                         Thanks,
                         Team BQA'
        WHERE template_code='INSTITUTE_SIGN_NON_CONFLICT';

UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                         	Application -{applicationId} is assigned to you and given access, please complete your Evaluation

                         Thanks,
                         Team BQA'
        WHERE template_code='GRANT_ACCESS';


UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                         	Application -{applicationId} is completed the site visit, please submit your evaluation.

                         Thanks,
                         Team BQA'
        WHERE template_code='SITE_VISIT_COMPLETED';


UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                         	Application -{applicationId} is completed the evaluation, please submit your action.

                         Thanks,
                         Team BQA'
        WHERE template_code='AM_APPROVE_ILEP_REPORT';


UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                         	Application -{applicationId} is completed the QAC feedback, please submit your action.

                         Thanks,
                         Team BQA'
        WHERE template_code='QAC_FEEDBACK_SUBMITTED';


UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                         	Application -{applicationId} is completed the QAC feedback updated, please submit your action.

                         Thanks,
                         Team BQA'
        WHERE template_code='QAC_UPDATED';


UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                         	Application -{applicationId} is shared to the NAC, please submit your action.

                         Thanks,
                         Team BQA'
        WHERE template_code='NAC_SHARED';

UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                         	Application -{applicationId} is approved by QAC, please submit your action.

                         Thanks,
                         Team BQA'
        WHERE template_code='QAC_APPROVE_REPORT';

UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                         	Application -{applicationId} is started the factual accuracy, please submit your action.

                         Thanks,
                         Team BQA'
        WHERE template_code='FACTUAL_ACCURACY_STARTED';

UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                         	Application -{applicationId} is in defered status, please submit your action.

                         Thanks,
                         Team BQA'
        WHERE template_code='DEFERED';

UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                         	Application -{applicationId} is requested for appeal, please submit your action.

                         Thanks,
                         Team BQA'
        WHERE template_code='ON_APPEAL_CREATE';

UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                         	Application -{applicationId} appeal request approved , please submit your action.

                         Thanks,
                         Team BQA'
        WHERE template_code='ON_APPEAL_APPROVED';

UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                         	Application -{applicationId} is approved by the NAC, please submit your action.

                         Thanks,
                         Team BQA'
        WHERE template_code='ON_NAC_APPROVAL';

UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                         	Application -{applicationId} MCO report updated, please submit your action.

                         Thanks,
                         Team BQA'
        WHERE template_code='ON_MCO_UPLOAD';

UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                         	Application -{applicationId} is shared to the cabinet, please submit your action.

                         Thanks,
                         Team BQA'
        WHERE template_code='ON_SHARED_CABINET';

UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},
                         
                         	Application -{applicationId} is now overdue, please submit your action asap.
                         	
                         Thanks,
                         Team BQA'
        WHERE template_code='DUE_DATE_WARNING';

UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                         	Application -{applicationId} is Listed.

                         Thanks,
                         Team BQA'
        WHERE template_code='ON_FINAL_STATUS';

UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                                                  	ILEP Panel is  created for Application -{applicationId}, please approve the panel.

                                                  Thanks,
                                                  Team BQA'
        WHERE template_code='ILEP_PANEL_CREATE';


INSERT INTO tbl_mail_template(template_name, template_code, template_description, display_sequence)
    VALUES
        ('Application terminated mail', 'APPLICATION_TERMINATED', 'Mail to institution user to inform application is terminated', 33);



UPDATE tbl_mail_template
    SET template_body = 'Hi {userName},

                                                  	your application is  terminated Application -{applicationId}.

                                                  Thanks,
                                                  Team BQA'
        WHERE template_code='APPLICATION_TERMINATED';