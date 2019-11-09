package sgtravel.logic;

import sgtravel.commons.exceptions.ApiException;
import sgtravel.commons.exceptions.ChronologyAfterPresentException;
import sgtravel.commons.exceptions.ChronologyBeforePresentException;
import sgtravel.commons.exceptions.ChronologyInconsistentException;
import sgtravel.commons.exceptions.DukeException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.results.CommandResult;
import sgtravel.logic.commands.results.PanelResult;
import sgtravel.logic.conversations.ConversationManager;
import sgtravel.logic.edits.EditorManager;
import sgtravel.logic.parsers.Parser;
import sgtravel.model.Model;
import sgtravel.model.ModelManager;

import javafx.scene.input.KeyCode;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main logic of the application.
 */
public class LogicManager implements Logic {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Model model;
    private ConversationManager conversationManager;

    /**
     * Constructs a LogicManager instance.
     */
    public LogicManager() {
        model = new ModelManager();
        conversationManager = new ConversationManager();
    }

    /**
     * Gets response from LogicManager.
     *
     * @param userInput The input string from user.
     * @return CommandResult Object containing information for Ui to display.
     */
    @Override
    public CommandResult execute(String userInput) throws DukeException {
        Command c;
        if (EditorManager.isActive()) {
            logger.log(Level.INFO, "editing...");
            c = EditorManager.edit(userInput);
        } else  {
            try {
                c = Parser.parseComplexCommand(userInput);
                conversationManager.clearContext();
            } catch (ChronologyAfterPresentException | ChronologyBeforePresentException
                    | ChronologyInconsistentException | ApiException e) {
                throw e;
            } catch (ParseException e) {
                c = getCommandFromConversationManager(userInput);
            }
        }
        return (CommandResult) c.execute(model);
    }

    /**
     * Gets response from LogicManager.
     *
     * @param keyCode The KeyCode for the EditorManager.
     */
    public PanelResult execute(KeyCode keyCode) {
        if (EditorManager.isActive()) {
            return EditorManager.edit(keyCode);
        }
        return new PanelResult();
    }

    /**
     * Gets a command from ConversationManager.
     *
     * @param userInput The user input.
     */
    private Command getCommandFromConversationManager(String userInput) throws DukeException {
        conversationManager.converse(userInput);
        return conversationManager.getCommand();
    }

    public String getName() {
        return model.getName();
    }

    public Model getModel() { return model; }
}
