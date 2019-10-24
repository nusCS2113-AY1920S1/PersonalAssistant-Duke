package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

/**
 * Class representing a command to handle a Prompt.
 */
public class PromptCommand extends Command {
    private String prompt;

    /**
     * Creates a new PromptCommand with the given prompt.
     *
     * @param prompt The prompt to display.
     */
    public PromptCommand(String prompt) {
        this.prompt = prompt;
    }

    /**
     * Executes this command with given prompt.
     *
     * @param model The Model object containing task list.
     * @return The CommandResult
     */
    public CommandResultText execute(Model model) {
        return new CommandResultText(prompt);
    }
}
