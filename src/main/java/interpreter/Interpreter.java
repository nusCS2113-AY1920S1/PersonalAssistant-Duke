package interpreter;

import executor.command.CommandType;
import executor.command.Executor;
import executor.task.TaskList;
import ui.Wallet;

public class Interpreter {

    /**
     * Interprets the userInput relative to the TaskList provided and executes the Command.
     * @param taskList The caller's TaskList
     * @param userInput The userInput taken from the User Interface
     * @return True if the Command executed calls for an ExitRequest, false otherwise
     */
    public static boolean interpret(TaskList taskList, Wallet wallet, String userInput) {
        CommandType commandType = Parser.parseForCommandType(userInput);
        boolean exitRequest = Executor.runCommand(taskList, wallet, commandType, userInput);
        return exitRequest;
    }
}
