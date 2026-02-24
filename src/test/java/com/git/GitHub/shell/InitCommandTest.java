package com.git.GitHub.shell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;

import com.git.GitHub.commands.InitCommand;

@ExtendWith(MockitoExtension.class)
public class InitCommandTest {

    @TempDir
    Path tempDir;

    @AfterEach
    public void cleanUp() throws IOException {
        Path root = Paths.get(tempDir + ".ygit");
        if (Files.notExists(root)) return;

        Files.walk(root)
                .sorted(Comparator.reverseOrder()) // delete children first
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    public void gitInit_new_Test() {
        String init = InitCommand.init(tempDir);
        Path root = tempDir.resolve(".ygit");
        Path objects = root.resolve("objects");
        Path refs = root.resolve("refs");
        assertTrue(Files.exists(root));
        assertTrue(Files.exists(objects));
        assertTrue(Files.exists(refs));
        assertTrue(Files.exists(root.resolve("config")));
        assertTrue(Files.exists(root.resolve("HEAD")));
        assertEquals("Repository Initialised", init);
    }

    @Test
    @SneakyThrows
    public void gitInit_exist_Test() {
        Path root = Paths.get(tempDir + "/.ygit");
        Files.createDirectory(root);
        Files.createDirectory(root.resolve("objects"));
        Files.createDirectory(root.resolve("refs"));
        Files.createDirectory(root.resolve("refs/heads"));

        Files.writeString(
                root.resolve("HEAD"),
                "ref: refs/heads/main\n"
        );

        Files.writeString(
                root.resolve("config"),
                "[core]\nrepositoryformatversion = 0\n"
        );
        String init = InitCommand.init(tempDir);
        assertEquals("Repository already initialised", init);
    }
}
