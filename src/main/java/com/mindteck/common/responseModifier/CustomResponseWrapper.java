package com.mindteck.common.responseModifier;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomResponseWrapper extends HttpServletResponseWrapper {


    private ByteArrayOutputStream outputStream;
    private CustomOutputStreamReader outputStreamReader;

    public CustomResponseWrapper(HttpServletResponse response) {
        super(response);
        outputStream = new ByteArrayOutputStream();
    }



    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if(null ==outputStreamReader)
            outputStreamReader = new CustomOutputStreamReader(outputStream);
        return outputStreamReader;
    }


    public byte[] getDataStream() {
        return outputStream.toByteArray();
    }
}
