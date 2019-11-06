package sgtravel.logic.commands;

import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;

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
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    public CommandResultText execute(Model model) {
        return new CommandResultText(prompt);
    }
}
