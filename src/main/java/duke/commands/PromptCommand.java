package duke.commands;

import duke.model.Model;

public class PromptCommand extends Command {
    private String prompt;

    public PromptCommand(String prompt) {
        this.prompt = prompt;
    }

    public CommandResult execute(Model model) {
        return new CommandResult(prompt);
    }
}
