package com.git.GitHub.shell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommandRunnerTest {

    CommandRunner cmd;

    @BeforeEach
    public void initialise() {
        cmd = new CommandRunner();
    }

    @Test
    public void getInitTest() {
        String init = cmd.gitInit();
        assertEquals("Repo initialised", init);
    }
}
