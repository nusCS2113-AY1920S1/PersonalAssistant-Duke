package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

public class PromptCommand extends Command {
    private String prompt;

    public PromptCommand(String prompt) {
        this.prompt = prompt;
    }

    public CommandResultText execute(Model model) {
        return new CommandResultText(prompt);
    }
}
