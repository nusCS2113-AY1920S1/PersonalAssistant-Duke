package compal.logic;

import compal.commons.Messages;
import compal.logic.command.Command;
import compal.logic.command.CommandResult;
import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.ParserManager;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.TaskList;
import compal.storage.TaskStorageManager;
import compal.ui.UiUtil;

/**
 * The LogicManager Class handles the logic of Wallet.
 */
public class LogicManager {
    public static final String MESSAGE_ERROR_COMMAND = "An error encountered while executing command.";
    public static final String BYE_TOKEN = "bye.";
    private final UiUtil uiUtil;

    private ParserManager parserManager;
    private TaskStorageManager taskStorageManager;


    /**
     * Construct logic manager class.
     */
    public LogicManager() {
        this.parserManager = new ParserManager();
        this.taskStorageManager = new TaskStorageManager();
        this.uiUtil = new UiUtil();
    }

    /**
     * Passes user input to parserManager to be processed. parserManager returns suitable
     * command object to carry out the user's aim.
     */
    public void logicExecute(String fullCommand, TaskList tasklist) throws CommandException, ParserException {
        uiUtil.clearPrimary();
        Command command = parserManager.processCmd(fullCommand);
        CommandResult cmdResult = command.commandExecute(tasklist);
        uiUtil.printg(cmdResult.feedbackToUser);

        //save to file
        System.out.println("Saving data...");
        taskStorageManager.saveData(tasklist.getArrList());

        if (cmdResult.feedbackToUser.equals(BYE_TOKEN)) {
            System.exit(0);
        }

    }

}