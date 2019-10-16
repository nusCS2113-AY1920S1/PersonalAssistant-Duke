package interpreter;

import executor.command.CommandType;
import executor.command.Executor;
import executor.task.TaskList;

public class Interpreter {

    /**
     * Interprets the userInput relative to the TaskList provided and executes the Command.
     * @param taskList The caller's TaskList
     * @param userInput The userInput taken from the User Interface
     * @return True if the Command executed calls for an ExitRequest, false otherwise
     */
    public static boolean interpret(TaskList taskList, String userInput) {
        CommandType commandType = Parser.parseCommandType(userInput);
        boolean exitRequest = Executor.runCommand(taskList, commandType, userInput);
        return exitRequest;
    }
}
