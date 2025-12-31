package com.mindteck.common.requestModifier;



import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CustomRequestWrapper extends HttpServletRequestWrapper {

    private String body;

    private String queryParams;

    private Map<String , String[]> queryParamMap = new HashMap<>();




    public CustomRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        // Read the request body
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = request.getReader();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();
        this.body = stringBuilder.toString();
        this.queryParamMap.putAll(request.getParameterMap());
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CustomInputStreamReader(this.body);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.body.getBytes());
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.queryParamMap;
    }

    public void setQueryParamMap(Map<String ,String[]> queryParamMap) {
        this.queryParamMap = queryParamMap;
    }

    @Override
    public String getParameter(String name) {
        String[] value = this.queryParamMap.get(name);
        return value != null ? value[0] : null;
    }



    @Override
    public String[] getParameterValues(String name) {
        return queryParamMap.get(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(this.queryParamMap.keySet());
    }
}
