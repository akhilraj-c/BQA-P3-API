package com.mindteck.common.responseModifier;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CustomOutputStreamReader extends ServletOutputStream {

    private ByteArrayOutputStream baos;

    public CustomOutputStreamReader(ByteArrayOutputStream baos) {
        this.baos = baos;
    }


    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }

    @Override
    public void write(int b) throws IOException {
     baos.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        baos.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        baos.write(b, off, len);
    }
}
