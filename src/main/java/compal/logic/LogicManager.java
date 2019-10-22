package compal.logic;

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

/**
 * The LogicManager Class handles the logic of Wallet.
 */
public class LogicManager {

    private static final String BYE_TOKEN = "bye.";
    private ParserManager parserManager;
    private TaskStorageManager taskStorageManager;
    private TaskList taskList;


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

        Command command = parserManager.processCmd(fullCommand);
        CommandResult cmdResult = command.commandExecute(taskList);

        //save to file if required
        if (cmdResult.requireSaving) {
            taskStorageManager.saveData(taskList.getArrList());
        }

        if (cmdResult.feedbackToUser.equals(BYE_TOKEN)) {
            System.exit(0);
        }
        return cmdResult;

    }

}