package compal.logic;

import compal.commons.Messages;
import compal.logic.command.Command;
import compal.logic.command.CommandResult;
import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.ParserManager;
import compal.logic.parser.exceptions.ParseException;
import compal.model.tasks.Task;
import compal.ui.UiUtil;

import java.util.ArrayList;

/**
 * The LogicManager Class handles the logic of Wallet.
 */
public class LogicManager {
    public static final String MESSAGE_ERROR_COMMAND = "An error encountered while executing command.";
    public static final String BYE_TOKEN = "bye.";
    private final UiUtil uiUtil;

    private Messages messages;
    private ParserManager parserManager;

    public LogicManager() {
        this.parserManager = new ParserManager();
        this.messages = new Messages();
        this.uiUtil = new UiUtil();
    }

    /**
     * Passes user input to parserManager to be processed. parserManager returns suitable
     * command object to carry out the user's aim.
     */
    public void logicExecute(String fullCommand, ArrayList<Task> taskArrList) throws CommandException, ParseException {

        Command command = parserManager.processCmd(fullCommand);
        CommandResult cmdResult = command.commandExecute(taskArrList);
        uiUtil.printg(cmdResult.feedbackToUser);
        if (cmdResult.feedbackToUser.equals(BYE_TOKEN)) {
            System.exit(0);
        }

    }

}