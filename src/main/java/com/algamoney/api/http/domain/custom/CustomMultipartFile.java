package com.algamoney.api.http.domain.custom;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Data
public class CustomMultipartFile implements MultipartFile {

    private final byte[] fileContent;

    private String fileName;

    private String contentType;

    private File file;

    private String destPath = System.getProperty("java.io.tmpdir");

    private FileOutputStream fileOutputStream;

    public CustomMultipartFile(byte[] fileData, String name) {
        this.fileContent = fileData;
        this.fileName = name;
        file = new File(destPath + fileName);
    }

    @Override
    public String getName() {

        return fileName;
    }

    @Override
    public String getOriginalFilename() {

        return fileName;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public byte[] getBytes() {
        return fileContent;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(fileContent);
    }

    @Override
    public void transferTo(File file) throws IllegalStateException, IOException {
        fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(fileContent);
    }

    public void clearOutStreams() throws IOException {
        if (null != fileOutputStream) {
            fileOutputStream.flush();
            fileOutputStream.close();
            file.deleteOnExit();
        }
    }
}


