package org.everythingjboss.jdg;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.FileUtils;

public class CompressedFileContentKeyValueGenerator implements KeyValueGenerator<Long, byte[]> {
    
    private String fileContent;
    
    public CompressedFileContentKeyValueGenerator(File file) throws IOException {
        this.fileContent = FileUtils.readFileToString(file);
    }
    
    @Override
    public Long generateKey(long i) {
        return Long.valueOf(i);
    }

    @Override
    public byte[] generateValue() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(fileContent.getBytes());
            gzip.close();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
