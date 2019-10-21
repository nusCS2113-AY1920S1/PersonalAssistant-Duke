package duke.logic;

import duke.logic.commands.Command;
import duke.logic.commands.results.CommandResult;
import duke.commons.exceptions.DukeApiException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.DukeUnknownCommandException;
import duke.logic.conversations.ConversationManager;
import duke.logic.parsers.Parser;
import duke.model.Model;
import duke.model.ModelManager;

public class LogicManager extends Logic {
    private Model model;
    private ConversationManager conversationManager;

    /**
     * Creates LogicManager instance.
     */
    public LogicManager() {
        conversationManager = new ConversationManager();
        model = new ModelManager();
    }

    /**
     * Gets response from LogicManager.
     *
     * @param userInput The input string from user.
     * @return CommandResult Object containing information for Ui to display.
     */
    public CommandResult execute(String userInput) throws DukeException {
        Command c;
        try {
            c = Parser.parseSingleCommand(userInput);
            conversationManager.clearContext();
        } catch (DukeApiException e) {
            throw new DukeException((e.getMessage()));
        } catch (DukeUnknownCommandException e) {
            c = getCommandFromConversationManager(userInput);
        }
        return (CommandResult) c.execute(model);
    }

    /**
     * Gets a command from ConversationManager.
     */
    private Command getCommandFromConversationManager(String userInput) throws DukeException {
        conversationManager.converse(userInput);
        if (conversationManager.isFinished()) {
            String result = conversationManager.getResult();
            return Parser.parseComplexCommand(result);
        } else {
            String prompt = conversationManager.getPrompt();
            return Parser.parsePromptCommand(prompt);
        }
    }
}
