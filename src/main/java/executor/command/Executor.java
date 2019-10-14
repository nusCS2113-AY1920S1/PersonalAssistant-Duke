package executor.command;

import executor.task.TaskList;

public class Executor {

    /**
     * Constructor for 'Executor' Class.
     */
    public Executor() {
    }

    /**
     * Parses the user input and executes the Command specified.
     *
     * @param userInput User input from the CLI
     * @return True if the Command executed calls for an ExitRequest, false otherwise
     */
    public static boolean runCommand(TaskList taskList, CommandType commandType, String userInput) {
        Command c = createCommand(commandType, userInput);
        c.execute(taskList);
        return c.getExitRequest();
    }

    /**
     * Creates a specific Command sub-Object given the CommandType.
     * @param commandType The type of command to be created
     * @param userInput The input to be initialized with the command
     * @return The generated Command
     */
    public static Command createCommand(CommandType commandType, String userInput) {
        Command c;
        switch (commandType) {
        case BLANK:
            c = new CommandBlank();
            break;

        case LIST:
            c = new CommandList();
            break;

        case BYE:
            c = new CommandBye();
            break;

        case DELETE:
            c = new CommandDelete(userInput);
            break;

        case FIND:
            c = new CommandFind(userInput);
            break;

        case DONE:
            c = new CommandMarkDone(userInput);
            break;

        case REMINDER:
            c = new CommandReminder();
            break;

        case QUEUE:
            c = new CommandQueue(userInput);
            break;

        case VIEWSCHEDULE:
            c = new CommandSchedule(userInput);
            break;

        default:
            c = new CommandNewTask(userInput);
            break;
        }
        return c;
    }

}
