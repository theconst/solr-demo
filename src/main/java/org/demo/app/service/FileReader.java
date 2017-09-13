package org.demo.app.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class FileReader {

    private static final Charset CHARSET = Charset.defaultCharset();

    public String readFileToString(File file) throws IOException {
        return FileUtils.readFileToString(file, CHARSET);
    }
}
