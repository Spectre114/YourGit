package com.git.GitHub.promptProvider;

import org.springframework.stereotype.Component;

import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;

@Component
public class CustomPromptProvider implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("git:>");
    }
}
