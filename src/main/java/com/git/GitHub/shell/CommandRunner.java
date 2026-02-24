package com.git.GitHub.shell;

import com.git.GitHub.commands.InitCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.nio.file.Paths;

@ShellComponent
public class CommandRunner {
    /**
     * Takes init command from shell and initialise a repo.
     *
     * @return confirmation for initialised repo.
     */
    @ShellMethod(key = "init")
    public String gitInit() {
        return InitCommand.init(Paths.get("./"));
    }
}
