package org.everythingjboss.jdg;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileContentKeyValueGenerator implements KeyValueGenerator<Long, char[]> {
    
    private String fileContent;
    
    public FileContentKeyValueGenerator(File file) throws IOException {
        this.fileContent = FileUtils.readFileToString(file);
    }
    
    @Override
    public Long generateKey(long i) {
        return Long.valueOf(i);
    }

    @Override
    public char[] generateValue() {
        return fileContent.toCharArray(); 
    }
}
