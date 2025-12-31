package com.mindteck.common.requestModifier;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class CustomInputStreamReader extends ServletInputStream {

    private final ByteArrayInputStream byteArrayInputStream;

    public CustomInputStreamReader(String body) {
        this.byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
    }

    @Override
    public int read() throws IOException {
        return byteArrayInputStream.read();
    }

    @Override
    public boolean isFinished() {
        return byteArrayInputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        throw new RuntimeException("Not implemented");
    }
}
