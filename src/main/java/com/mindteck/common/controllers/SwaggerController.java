package com.mindteck.common.controllers;


import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.constants.Enum.DocType;
import com.mindteck.common.models.rest.DocDownloadRequest;
import com.mindteck.common.service.SwaggerUtilityService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class SwaggerController {
	
	private static final Map<String, String> apiSpecCredentialsMap = new HashMap<>();

	@Value("${server.basePath}")
	private String basePath;

	@Value("${server.servlet.contextPath}")
	private String contextPath;

	@Autowired
	SwaggerUtilityService swaggerUtilityService;
	
	static {
		apiSpecCredentialsMap.put("developer", "qwe@123");
	}
    
    @GetMapping(ApiUrls.API_SPEC_LOGIN)
    public String apiSpecLogin(HttpServletRequest request , Model model)
	{
		if(basePath.isEmpty())
			model.addAttribute("basePath" , contextPath);
		else
			model.addAttribute("basePath", basePath);
        return "api-spec-login";
    }
    
    @PostMapping(ApiUrls.API_SPEC_LOGIN)
    public String validateApiSpecLogin(HttpServletRequest request, Model model) {  
    	String userName = request.getParameter("username");
    	String password = request.getParameter("password");
    	
    	String userPwd = apiSpecCredentialsMap.get(userName);
    	if(userPwd == null || !userPwd.equals(password)) {
    		model.addAttribute("error", "Invalid Credentials");
			if(basePath.isEmpty())
				model.addAttribute("basePath" , contextPath);
			else
				model.addAttribute("basePath", basePath);
			return "api-spec-login";
    	}    	      
    	HttpSession session = request.getSession(true);
    	session.setAttribute("API_SPEC_AUTH_USER", "admin");
		if(contextPath.isEmpty())
            return "redirect:"+basePath+"/swagger-ui.html";
		else
			return "redirect:/swagger-ui.html";

	}

	@GetMapping(value = ApiUrls.ADMIN_API_SPEC_DOWNLOAD , produces = MediaType.TEXT_HTML_VALUE)
	public void downloadFile(@Valid @ModelAttribute DocDownloadRequest docDownloadRequest ,
							 BindingResult bindingResult , HttpServletResponse httpServletResponse)
	{
		LOGGER.debug("Swagger document download request received for doc => {} " , docDownloadRequest.getDocType());
		if(bindingResult.hasErrors()) {
			LOGGER.debug("Input validation failed {}", DocDownloadRequest.class);
			//throw new ControllerException(bindingResult , ErrorCodeEnum.INPUT_VALIDATION_FAILED);
		}
		DocType docType = DocType.of(docDownloadRequest.getDocType());
		File file = swaggerUtilityService.getSwaggerDocumentationFile(docType);
		getRawFile(file, httpServletResponse);
	}


	void getRawFile(File file , HttpServletResponse httpServletResponse) {
		httpServletResponse.setContentType(MediaType.TEXT_HTML_VALUE);
		httpServletResponse.setHeader("Content-Disposition" , "inline" );
		try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
			FileCopyUtils.copy(inputStream, httpServletResponse.getOutputStream());
		} catch (IOException e) {
			//throw new ControllerException(ErrorCodeEnum.INPUT_VALIDATION_FAILED ,e);
		}
	}
}
