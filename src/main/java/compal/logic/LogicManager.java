package compal.logic;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.CommandResult;
import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.ParserManager;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import compal.storage.TaskStorageManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Logger;

//@@author SholihinK

/**
 * The LogicManager Class handles the logic of Wallet.
 */
public class LogicManager {

    private static final String BYE_TOKEN = "bye.";
    private ParserManager parserManager;
    private TaskStorageManager taskStorageManager;
    private TaskList taskList;
    private static final Logger logger = LogUtils.getLogger(LogicManager.class);


    /**
     * Construct logic manager class.
     */
    public LogicManager() {
        this.parserManager = new ParserManager();
        this.taskStorageManager = new TaskStorageManager();
        this.taskList = new TaskList();

        ArrayList<Task> taskArrList = new ArrayList<>(taskStorageManager.loadData());
        this.taskList.setArrList(taskArrList);
    }

    /**
     * Passes user input to parserManager to be processed. parserManager returns suitable
     * command object to carry out the user's aim.
     */
    public CommandResult logicExecute(String fullCommand) throws CommandException, ParserException, ParseException {
        logger.info("User input received:" + fullCommand + ". Sending it to be parsed");

        Command command = parserManager.processCmd(fullCommand);
        CommandResult cmdResult = command.commandExecute(taskList);

        //save to file if required
        if (cmdResult.requireSaving) {
            logger.info("Updating tasks.txt file");
            taskList.sortTask(taskList.getArrList());
            taskStorageManager.saveData(taskList.getArrList());
        }

        if (cmdResult.feedbackToUser.equals(BYE_TOKEN)) {
            logger.info("Exiting COMPal!");
            System.exit(0);
        }

        return cmdResult;

    }

}