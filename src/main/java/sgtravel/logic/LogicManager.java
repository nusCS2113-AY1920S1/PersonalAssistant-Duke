package sgtravel.logic;

import sgtravel.commons.Messages;
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
import sgtravel.model.lists.RouteList;
import sgtravel.model.planning.Itinerary;

import java.util.HashMap;
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
            logger.log(Level.INFO, "Editing...");
            c = EditorManager.edit(userInput);
        } else  {
            try {
                c = Parser.parseComplexCommand(userInput);
                conversationManager.clearContext();
                logger.log(Level.INFO, "Executing command...");
            } catch (ChronologyAfterPresentException | ChronologyBeforePresentException
                    | ChronologyInconsistentException | ApiException e) {
                throw e;
            } catch (ParseException e) {
                c = getCommandFromConversationManager(userInput);
                logger.log(Level.INFO, "Conversing...");
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

    /**
     * Gets the welcome message.
     * @return The welcome message.
     */
    public String getWelcomeMessage() {
        HashMap<String, Itinerary> itineraries = model.getItineraryTable();
        RouteList routes = model.getRoutes();
        String message = Messages.STARTUP_WELCOME_MESSAGE + model.getName() + "\n\n";
        message += Messages.STARTUP_WELCOME_MESSAGE_ITINERARY_START + itineraries.size()
                + Messages.STARTUP_WELCOME_MESSAGE_ITINERARY_END;
        message += Messages.STARTUP_WELCOME_MESSAGE_ROUTE_START + routes.size()
                + Messages.STARTUP_WELCOME_MESSAGE_ROUTE_END;
        message += Messages.STARTUP_WELCOME_MESSAGE_HELP;
        return message;
    }
}
