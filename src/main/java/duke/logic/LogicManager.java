package duke.logic;

import duke.logic.commands.Command;
import duke.logic.commands.results.CommandResult;
import duke.commons.exceptions.DukeApiException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.DukeUnknownCommandException;
import duke.logic.commands.results.PanelResult;
import duke.logic.conversations.ConversationManager;
import duke.logic.edits.EditorManager;
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

    /**
     * Creates LogicManager instance.
     */
    public LogicManager() {
        model = new ModelManager();
        conversationManager = new ConversationManager(model.getRouteManager());
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
        } else  {
            if (model.getRouteManager().isActivated() && !conversationManager.isInConversation()) {
                userInput = model.getRouteManager().getConversationPrefix() + userInput;
            }
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
}
