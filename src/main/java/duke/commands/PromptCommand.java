package duke.commands;

import duke.storage.Storage;

public class PromptCommand extends Command {
    private String prompt;

    public PromptCommand(String prompt) {
        this.prompt = prompt;
    }

    public CommandResult execute(Storage storage) {
        return new CommandResult(prompt);
    }
}
