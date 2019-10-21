package dolla.parser;

import dolla.Tag;
import dolla.Ui;
//import dolla.command.;

import dolla.action.undo;
import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.command.SwitchModeCommand;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * MainParser checks the current mode and user input
 * to create the relevant command.
 */
public class MainParser {
    private static String[] prevCommand = {"dolla","1"};

    /**
     * Returns a command corresponding to the user input by directing
     * the input to the relevant dolla.parser.
     * @param mode The mode Dolla is currently on.
     * @return a command corresponding to the user input.
     */
    public static Command handleInput(String mode, String inputLine) { // TODO: Rename to something else

        //Scanner input = new Scanner(System.in);
        //String inputLine = input.nextLine();
        String[] inputArray = inputLine.split(" ");
        String command = inputArray[0];
        boolean isSwitchMode = command.equals("dolla") || command.equals("entry")
                || command.equals("limit") || command.equals("debt")
                || command.equals("shortcut");

        if (command.equals("bye")) {
            //return new ExitCommand(); // TODO
        } else if (isSwitchMode) {
            return new SwitchModeCommand(command); // TODO
        }

        if(prevCommand[0].equals("undo") && prevCommand[1].equals("1")) {
            prevCommand[1] = "2";
        } else if(prevCommand[0].equals("undo") && prevCommand[1].equals("2")) {
            prevCommand[0] = command;
            prevCommand[1] = "1";
        } else { //not from redo
            prevCommand[0] = command;
            prevCommand[1] = "1";
        }

//    } else if(prevCommand[0].equals("redo") && prevCommand[1].equals("1")) {
//        prevCommand[1] = "2";
//    } else if(prevCommand[0].equals("redo") || prevCommand[1].equals("2")) {
//        prevCommand[0] = command;
//        prevCommand[1] = "1";

        Tag tag = new Tag(inputLine);
        tag.parseTag();

        switch (mode) {
        case "dolla":
            DollaParser dollaParser = new DollaParser(inputLine);
            //System.out.println("Running DollaParser...");
            return dollaParser.handleInput(mode, inputLine);
        case "entry":
            EntryParser entryParser = new EntryParser(inputLine);
            return entryParser.handleInput(mode, inputLine);
        case "debt":
            DebtsParser debtsParser = new DebtsParser(inputLine);
            return debtsParser.handleInput(mode, inputLine);
        case "limit":
            LimitParser limitParser = new LimitParser(inputLine);
            return limitParser.handleInput(mode, inputLine);
        case "modify entry":
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
     * This method will exit the entire program.
     */
    public static void exit() {
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "Bye. Hope to see you again soon!"
        ));
        Ui.printMsg(msg);
        //duke.Storage.save(tasks); // Don't need to save since any previous commands are already saved
    }
}
