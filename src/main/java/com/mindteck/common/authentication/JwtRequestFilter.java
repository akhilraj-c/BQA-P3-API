package com.mindteck.common.authentication;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindteck.common.ErrorResponseBuilder.Data;
import com.mindteck.common.ErrorResponseBuilder.ErrorResponse;
import com.mindteck.common.dao.UserLoginInfoDao;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.user.service.impl.UserServiceImpl;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.models_cas.UserLoginInfo;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserServiceImpl jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserLoginInfoDao userLoginInfoDao;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        /*return new AntPathMatcher().match("/api/auth/**", request.getServletPath()) ||
                AntPathMatcher().match("/api/auth/**", request.getServletPath());*/
        return (new AntPathMatcher().match("/api/auth/**", request.getServletPath())
                || new AntPathMatcher().match( "/api/user/generate-pre-signed-url",request.getServletPath())
                || new AntPathMatcher().match("/v2/api-docs", request.getServletPath())
                || new AntPathMatcher().match("/swagger-resources", request.getServletPath())
                || new AntPathMatcher().match("/configuration/security", request.getServletPath())
                || new AntPathMatcher().match("/configuration/ui", request.getServletPath())
                || new AntPathMatcher().match("/swagger-resources/**", request.getServletPath())
                || new AntPathMatcher().match("swagger-ui.html", request.getServletPath())
                || new AntPathMatcher().match("/webjars/**", request.getServletPath())
                || new AntPathMatcher().match("/swagger-ui/**", request.getServletPath())
                || new AntPathMatcher().match("/v3/api-docs/**", request.getServletPath())
                || new AntPathMatcher().match("swagger-ui.html**", request.getServletPath()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);

            UserLoginInfo loginInfo  = userLoginInfoDao.getByToken(jwtToken);
            if(loginInfo==null){
                System.out.println("Invalid JWT Token");
                setErrorResponse(HttpStatus.BAD_REQUEST, response, "Invalid JWT Token");
            }

            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
                setErrorResponse(HttpStatus.BAD_REQUEST, response, "Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
                setErrorResponse(HttpStatus.BAD_REQUEST, response, "Expired JWt");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
            /* commenting for development
            setErrorResponse(HttpStatus.BAD_REQUEST, response, "Invalid token");*/
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // A class used for errors
        Data errorData =  Data.builder()
                .code(40001)
                .message(message)
                .build();
        Status commonStatus = WebUtils.getStatus();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(commonStatus)
                .data(errorData)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
