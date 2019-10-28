package duke.logic;

import duke.logic.commands.Command;
import duke.logic.commands.results.CommandResult;
import duke.commons.exceptions.DukeApiException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.DukeUnknownCommandException;
import duke.logic.commands.results.PanelResult;
import duke.logic.conversations.ConversationManager;
import duke.logic.parsers.Parser;
import duke.model.Model;
import duke.model.ModelManager;

import javafx.scene.input.KeyCode;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main logic of the application.
 */
public class LogicManager extends Logic {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Model model;
    private ConversationManager conversationManager;
    public boolean isNewUser;

    /**
     * Creates LogicManager instance.
     */
    public LogicManager() {
        conversationManager = new ConversationManager();
        model = new ModelManager();
        this.isNewUser = model.isNewUser();
    }

    /**
     * Gets response from LogicManager during setup.
     *
     * @param userInput The input string from user.
     * @return CommandResult Object containing information for Ui to display.
     */
    public CommandResult setup(String userInput) throws DukeException {
        Command c;
        try {
            c = Parser.parseComplexCommand(userInput);
            conversationManager.clearContext();
        } catch (DukeUnknownCommandException e) {
            conversationManager.converse(userInput);
            c = conversationManager.getCommand();
        }
        CommandResult result = (CommandResult) c.execute(model);
        isNewUser = model.isNewUser();
        return result;
    }

    /**
     * Gets response from LogicManager.
     *
     * @param userInput The input string from user.
     * @return CommandResult Object containing information for Ui to display.
     */
    public CommandResult execute(String userInput) throws DukeException {
        Command c;
        if (EditorManager.isActive()) {
            logger.log(Level.INFO, "editing...");
            c = EditorManager.edit(userInput);
        } else {
            try {
                c = Parser.parseComplexCommand(userInput);
                conversationManager.clearContext();
            } catch (DukeApiException e) {
                throw new DukeException((e.getMessage()));
            } catch (DukeUnknownCommandException e) {
                c = getCommandFromConversationManager(userInput);
            }
        }
        return (CommandResult) c.execute(model);
    }

    /**
     * Gets response from LogicManager.
     */
    public PanelResult execute(KeyCode keyCode) {
        if (EditorManager.isActive()) {
            return EditorManager.edit(keyCode);
        }
        return new PanelResult();
    }

    /**
     * Gets a command from ConversationManager.
     */
    private Command getCommandFromConversationManager(String userInput) throws DukeException {
        conversationManager.converse(userInput);
        return conversationManager.getCommand();
    }

    public String getName() {
        return model.getName();
    }
}
