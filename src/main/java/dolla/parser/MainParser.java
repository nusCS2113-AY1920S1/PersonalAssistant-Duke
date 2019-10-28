package dolla.parser;

import dolla.ui.Ui;

import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.command.SwitchModeCommand;

/**
 * MainParser directs the program to the appropriate parser depending on the current mode and the user's input.
 */
public class MainParser {

    private static final String MODE_DOLLA = "dolla";
    private static final String MODE_ENTRY = "entry";
    private static final String MODE_LIMIT = "limit";
    private static final String MODE_DEBT = "debt";
    private static final String MODE_SHORTCUT = "shortcut";
    private static final String SPACE = " ";
    private static final String COMMAND_BYE = "bye";

    /**
     * Returns a command corresponding to the user input by directing
     * the input to the relevant dolla.parser.
     * @param mode The mode Dolla is currently on.
     * @return a command corresponding to the user input.
     */
    public static Command handleInput(String mode, String inputLine) { // TODO: Rename to something else

        //Scanner input = new Scanner(System.in);
        //String inputLine = input.nextLine();
        String[] inputArray = inputLine.split(SPACE);
        String command = inputArray[0];
        boolean isExitCommand = command.equalsIgnoreCase(COMMAND_BYE);
        boolean isSwitchMode = command.equalsIgnoreCase(MODE_DOLLA) || command.equals(MODE_ENTRY)
                || command.equals(MODE_LIMIT) || command.equals(MODE_DEBT)
                || command.equals(MODE_SHORTCUT);
        if (isExitCommand) {
            exit(); // TODO: change
            //return new ExitCommand();
        } else if (isSwitchMode) {
            return new SwitchModeCommand(command); // TODO
        }

        switch (mode) {
        case MODE_DOLLA:
            DollaParser dollaParser = new DollaParser(inputLine);
            //System.out.println("Running DollaParser...");
            return dollaParser.handleInput(mode, inputLine);
        case MODE_ENTRY:
            EntryParser entryParser = new EntryParser(inputLine);
            return entryParser.handleInput(mode, inputLine);
        case MODE_DEBT:
            DebtsParser debtsParser = new DebtsParser(inputLine);
            return debtsParser.handleInput(mode, inputLine);
        case MODE_LIMIT:
            LimitParser limitParser = new LimitParser(inputLine);
            return limitParser.handleInput(mode, inputLine);
        case "modify entry": //is this a mode?
            ModifyParser modifyParser = new ModifyParser(inputLine);
            return modifyParser.handleInput(mode, inputLine);
        default:
            Ui.printInvalidCommandError();
            return new ErrorCommand();
        }

        /*
        String[] inputArray = inputLine.split(" ");
        String command = inputArray[0];

        switch (command) {
        case "list":
            return new ShowListCommand();
        case "done":
            try {
                return new CompleteCommand(inputArray[1]);
            } catch (IndexOutOfBoundsException e) {
                ArrayList<String> msg = new ArrayList<String>(
                Arrays.asList("Please use the format 'done <number>'!"
                ));
                Ui.printMsg(msg);
                break;
            }
        case "remove":
            try {
                return new RemoveCommand(inputArray[1]);
            } catch (IndexOutOfBoundsException e) {
                ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                        "Please use the format 'remove <number>'!"
                ));
                Ui.printMsg(msg);
                break;
            }
        case "find":
            return new FindStringCommand(inputLine);
        case "view":
            try {
                return new ViewScheduleCommand(inputArray[1]);
            } catch (IndexOutOfBoundsException e) {
                ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                        "Please use the format 'view today' or 'view <date>'!"
                ));
                Ui.printMsg(msg);
                break;
            } catch (DateTimeParseException e) {
                Ui.printDateFormatError();
                break;
            }
        case "snooze":
            try {
                String dateTimeArray = inputArray[2] + " " + inputArray[3];
                return new SnoozeCommand(inputArray[1], dateTimeArray);
            } catch (IndexOutOfBoundsException e) {
                Ui.printMsg("Please use the format 'snooze <task number> <new date> <new time>'!");
                break;
            } catch (DateTimeParseException e) {
                Ui.printDateFormatError();
                break;
            }
        default:
            return addToList(command, inputLine);
        }
        */
    }


    /*
     * Returns an add command corresponding to the specified command, otherwise alert the user
     * that the command is invalid.
     * @param command The command to be created,
     * @param inputLine The entire line input from the user.
     * @return Add command corresponding to the specified command.
     */
    /*
    public static Command addToList(String command, String inputLine) {

        String taskDescription;
        Command commandToRun = new ErrorCommand();

        try {
            taskDescription = inputLine.substring(command.length() + 1);
            switch (command) {
            case "todo":
                commandToRun = new AddTodoCommand(taskDescription);
                break;
            case "event":
                commandToRun = new AddEventCommand(taskDescription);
                break;
            case "deadline":
                commandToRun = new AddDeadlineCommand(taskDescription);
                break;
            case "duration":
                commandToRun = new AddFixDurationCommand(taskDescription);
                break;
            case "recurring":
                commandToRun = new AddRecurringTaskCommand(taskDescription);
                break;
            case "after":
                commandToRun = new AddDoAfterTaskCommand(taskDescription);
                break;
            default:
                Ui.printInvalidCommandError();
            }
        } catch (IndexOutOfBoundsException e) {
            ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                    "Invalid command given!"
            ));
            Ui.printMsg(msg);
        }
        return commandToRun;
    }
    */

    /**
     * This method will exit the entire program after printing a goodbye message.
     */
    public static void exit() {
        //TODO: DO SMTH
        Ui.printExitMsg();
    }
}
