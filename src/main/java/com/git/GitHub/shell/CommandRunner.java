package com.git.GitHub.shell;

import com.git.GitHub.commands.AddCommand;
import com.git.GitHub.commands.InitCommand;
import com.git.GitHub.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.nio.file.Paths;
import java.util.Arrays;

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

    @ShellMethod(key = "add")
    public String gitAdd(@ShellOption(arity = Integer.MAX_VALUE) String... files) {

        for (String file : files) {
            if (file.equals(".")) {
                AddCommand.addAll();
            } else {
                AddCommand.add(file);
            }
        }
       return "Files staged successfully";
    }

    @ShellMethod(key = "exit")
    public void exit() {
        System.exit(0);
    }
}
