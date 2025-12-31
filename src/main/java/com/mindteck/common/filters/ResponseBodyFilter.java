package com.mindteck.common.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindteck.common.models.rest.EncModel;
import com.mindteck.common.responseModifier.CustomResponseWrapper;
import com.mindteck.common.utils.EncryptionUtils;
import com.mindteck.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
public class ResponseBodyFilter implements Filter {
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/public/qualification-details"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String path = req.getRequestURI();
        boolean isExcluded = EXCLUDED_PATHS.stream().anyMatch(path::equalsIgnoreCase);

        if (isExcluded) {
            filterChain.doFilter(servletRequest, servletResponse); // Skip filter logic
//            System.out.println("Check works");
            return;
        }

        CustomResponseWrapper wrapper = new CustomResponseWrapper((HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest,wrapper);

        String original = new String(wrapper.getDataStream());
        LOGGER.debug("Response : {}" , original);
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String enc= "";
        try{
            if(null != request.getHeader("enc") && Boolean.parseBoolean(request.getHeader("enc"))) {
            String key = WebUtils.getAesToken();
            enc = EncryptionUtils.encryptDataWithAES(original,key);
            ObjectMapper objectMapper = new ObjectMapper();
            String response = objectMapper.writeValueAsString(new EncModel(enc));
            servletResponse.setContentLength(response.length());
            servletResponse.getOutputStream().write(response.getBytes());
         } else {
              servletResponse.setContentLength(original.length());
              servletResponse.getOutputStream().write(original.getBytes());
           }

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }


}
