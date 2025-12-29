package com.git.GitHub.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CommandRunner {

    /**
     * Takes init command from shell and initialise a repo.
     *
     * @return confirmation for initialised repo.
     */
    @ShellMethod(key = "init")
    public String gitInit() {
        return "Repo initialised";
    }
}
