package com.git.GitHub.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@UtilityClass
public class FileUtils {

    private final Path WORK_DIR = Paths.get("./");
    public byte[] convertToBlob(byte[] fileBytes) {
        String header = "blob " + fileBytes.length + "\0";
        byte[] headerBytes = header.getBytes();
        byte[] contentWithHeader = new byte[headerBytes.length + fileBytes.length];
        System.arraycopy(headerBytes, 0, contentWithHeader, 0, headerBytes.length);
        System.arraycopy(fileBytes, 0, contentWithHeader, headerBytes.length, fileBytes.length);
        return contentWithHeader;
    }

    public String generateHash(byte[] content) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hashBytes = digest.digest(content);
        return bytesToHex(hashBytes);
    }

    public String bytesToHex(byte[] hashBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    @SneakyThrows
    public void storeBlob(String hashedContent, byte[] content) {
        Path objectsDir = WORK_DIR.resolve(".ygit").resolve("objects");

        String dirName = hashedContent.substring(0, 2);
        String fileName = hashedContent.substring(2);

        Path objectDir = objectsDir.resolve(dirName);
        Path objectFile = objectDir.resolve(fileName);

        if (!Files.exists(objectDir)) {
            Files.createDirectories(objectDir);
        }

        if (!Files.exists(objectFile)) {
            Files.write(objectFile, content);
        }
    }

    @SneakyThrows
    public void updateIndexFile(Path file, String hashedContent) {
        Path index = WORK_DIR.resolve(".ygit").resolve("index");

        if(!Files.exists(index)) {
            Files.createFile(index);
        }
        String indexContent = file.getFileName() + "|" + hashedContent + "\n";
        Files.writeString(index, indexContent, StandardOpenOption.APPEND);
    }

    @SneakyThrows
    public boolean include(String fileToIgnore) {
        Path ignore = WORK_DIR.resolve(".ygitignore");
        List<String> patterns = Files.readAllLines(ignore);
        for(String pattern : patterns) {
            if (fileToIgnore.contains(pattern)) return false;
        }
        return true;
    }
}
