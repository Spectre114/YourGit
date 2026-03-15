package com.git.GitHub.commands;

import com.git.GitHub.utils.FileUtils;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public class AddCommand {

    private final Path WORK_DIR = Paths.get("./");

    @SneakyThrows
    public void addAll() {

        Files.walk(WORK_DIR)
                .filter(Files::isRegularFile)
                .filter(path -> !path.startsWith(WORK_DIR.resolve(".ygit")))
                .filter(path -> FileUtils.include(path.toString()))
                .forEach(path -> {
                    try {
                        add(WORK_DIR.relativize(path).toString());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

    }

    @SneakyThrows
    public void add(String file) {
        Path filePath = WORK_DIR.resolve("./" + file);
        byte[] fileBytes = Files.readAllBytes(filePath);
        byte[] content = FileUtils.convertToBlob(fileBytes);
        String hashedContent = FileUtils.generateHash(content);
        FileUtils.storeBlob(hashedContent, content);
        FileUtils.updateIndexFile(filePath, hashedContent);
    }
}
