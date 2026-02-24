package com.git.GitHub.commands;

import java.nio.file.Files;
import java.nio.file.Path;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InitCommand {

    @SneakyThrows
    public String init(Path workingDir) {
        Path root = workingDir.resolve(".ygit");
        if(Files.exists(root)) {
            return "Repository already initialised";
        }
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
        Files.setAttribute(root, "dos:hidden", true);
        return "Repository Initialised";
    }

}
