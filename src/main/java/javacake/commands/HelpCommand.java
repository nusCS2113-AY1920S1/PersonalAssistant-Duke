package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

public class HelpCommand extends Command {

    private StringBuilder stringBuilder = new StringBuilder();

    /**
     * Constructor for HelpCommand.
     * @param str Input command from user.
     */
    public HelpCommand(String str) {
        type = CmdType.HELP;
        input = str;
    }

    /**
     * Execute S.O.S.
     * @param logic tracks current location in program
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @throws CakeException Error thrown when unable to close reader
     * @return method for the related help requested.
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        String[] wordsList = input.split("\\s+");
        if (wordsList.length == 1) {
            checksParam(input);
            return mainHelp();
        } else {
            switch (wordsList[1]) {
            case "back":
                return backHelp();
            case "change":
                return changeHelp();
            case "deadline":
                return deadlineHelp();
            case "delete":
                return deleteHelp();
            case "done":
                return doneHelp();
            case "exit":
                return exitHelp();
            case "goto":
                return gotoHelp();
            case "help":
                return helpHelp();
            case "list":
                return listHelp();
            case "reset":
                return resetHelp();
            case "score":
                return scoreHelp();
            case "snooze":
                return snoozeHelp();
            case "overview":
                return overviewHelp();
            case "createnote":
                return createNoteHelp();
            case "editnote":
                return editNoteHelp();
            case "listnote":
                return listNoteHelp();
            case "viewnote":
                return viewNoteHelp();
            default:
                throw new CakeException("No such command found. Try again.");
            }
        }
    }

    /**
     * Displays list of built-in commands.
     * @return List of commands that users can execute.
     */
    private String mainHelp() {
        stringBuilder.append("Here is the list of available commands:\n\n");
        stringBuilder.append("- ").append("back").append("\n");
        stringBuilder.append("- ").append("change").append("\n");
        stringBuilder.append("- ").append("deadline").append("\n");
        stringBuilder.append("- ").append("delete").append("\n");
        stringBuilder.append("- ").append("done").append("\n");
        stringBuilder.append("- ").append("exit").append("\n");
        stringBuilder.append("- ").append("goto").append("\n");
        stringBuilder.append("- ").append("help").append("\n");
        stringBuilder.append("- ").append("list").append("\n");
        stringBuilder.append("- ").append("overview").append("\n");
        stringBuilder.append("- ").append("reset").append("\n");
        stringBuilder.append("- ").append("score").append("\n");
        stringBuilder.append("- ").append("snooze").append("\n");
        stringBuilder.append("- ").append("createnote").append("\n");
        stringBuilder.append("- ").append("editnote").append("\n");
        stringBuilder.append("- ").append("listnote").append("\n");
        stringBuilder.append("- ").append("viewnote").append("\n");
        stringBuilder.append("\nType 'help COMMAND_NAME' to learn more in-depth!\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how back command work.
     * @return String of instructions to guide user on back command.
     */
    private String backHelp() {
        stringBuilder.append("- ").append("back").append("\n");
        stringBuilder.append("Command to jump back to previous state after calling 'goto X'").append("\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how change command work.
     * @return String of instructions to guide user on back command.
     */
    private String changeHelp() {
        stringBuilder.append("- ").append("change").append("\n");
        stringBuilder.append("Command to switch between:\n -LIGHT_MODE[default]\n -DARK_MODE").append("\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how deadline command work.
     * @return String of instructions to guide user on back command.
     */
    private String deadlineHelp() {
        stringBuilder.append("- ").append("deadline").append("\n");
        stringBuilder.append("Command to add a deadline task").append("\n");
        stringBuilder.append("Simply type in 'deadline [task] /by [desired date]'!").append("\n");
        stringBuilder.append("And please keep your task length to less than 40 characters!").append("\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how delete command work.
     * @return String of instructions to guide user on back command.
     */
    private String deleteHelp() {
        stringBuilder.append("- ").append("delete ").append("\n");
        stringBuilder.append("Command to delete a deadline task").append("\n");
        stringBuilder.append("Simply type in 'delete [task_num]'!").append("\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how done command work.
     * @return String of instructions to guide user on back command.
     */
    private String doneHelp() {
        stringBuilder.append("- ").append("done").append("\n");
        stringBuilder.append("Command to set a deadline task as done").append("\n");
        stringBuilder.append("Simply type in 'done [task_num]'!").append("\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how exit command work.
     * @return String of instructions to guide user on exit command.
     */
    private String exitHelp() {
        stringBuilder.append("- ").append("exit").append("\n");
        stringBuilder.append("Command to exit from the program").append("\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how goto command work.
     * @return String of instructions to guide user on goto command.
     */
    private String gotoHelp() {
        stringBuilder.append("- ").append("goto").append("\n");
        stringBuilder.append("Command to jump to next index of content!").append("\n");
        stringBuilder.append("Simply type in 'goto X' OR 'goto X.Y' [X and Y are positive integers]!").append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("- ").append("E.g. goto '1' will bring you to the content in index 1.").append("\n");
        stringBuilder.append("- ").append("E.g. goto '1.2' will bring you to ");
        stringBuilder.append("- ").append("index 1 followed by 2!").append("\n");
        stringBuilder.append("- ").append("You get the point!").append("\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how help command work.
     * @return String of instructions to guide user on help command.
     */
    private String helpHelp() {
        stringBuilder.append("- ").append("help").append("\n");
        stringBuilder.append("I am trying to help you!!!").append("\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how list command work.
     * @return String of instructions to guide user on list command.
     */
    private String listHelp() {
        stringBuilder.append("- ").append("list").append("\n");
        stringBuilder.append("Command to display table-of-contents").append("\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how reset command work.
     * @return String of instructions to guide user on back command.
     */
    private String resetHelp() {
        stringBuilder.append("- ").append("reset").append("\n");
        stringBuilder.append("Command to reset the user profile")
                .append("\n").append("Confirmation will be requested before reset").append("\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how score command work.
     * @return String of instructions to guide user on score command.
     */
    private String scoreHelp() {
        stringBuilder.append("- ").append("score").append("\n");
        stringBuilder.append("Command to get your current overall score for JavaCake!").append("\n");
        stringBuilder.append("The number of # indicates the number of marks").append("\n");
        stringBuilder.append("The number of - indicates marks yet to be obtained").append("\n");
        stringBuilder.append("They are displayed in sorted order, from Quiz 1 to Quiz ALL,").append("\n");
        stringBuilder.append("and from easy to hard difficulty").append("\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how snooze command work.
     * @return String of instructions to guide user on back command.
     */
    private String snoozeHelp() {
        stringBuilder.append("- ").append("snooze").append("\n");
        stringBuilder.append("Command to reschedule a deadline task").append("\n");
        stringBuilder.append("Simply type in 'snooze [task_num] /by [new date]'!").append("\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how overview command work.
     * @return String of instructions to guide user on overview command.
     */
    private String overviewHelp() {
        stringBuilder.append("- ").append("overview").append("\n");
        stringBuilder.append("Command to view all the content installed in JavaCake!").append("\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how createnote command work.
     * @return String of instructions to guide user on createnote command.
     */
    private String createNoteHelp() {
        stringBuilder.append("- ").append("createnote").append("\n");
        stringBuilder.append("Command to create your own notes in JavaCake!").append("\n");
        stringBuilder.append("Simply type in 'createnote' or 'createnote [desired name of note]'!").append("\n");
        stringBuilder.append("And please keep your file name in alphanumeric to avoid illegal characters!")
                .append("\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how editnote command work.
     * @return String of instructions to guide user on editnote command.
     */
    private String editNoteHelp() {
        stringBuilder.append("- ").append("editnote").append("\n");
        stringBuilder.append("Command to edit the pre-existing note created by you!").append("\n");
        stringBuilder.append("View the notes available on the right window!").append("\n");
        stringBuilder.append("Simply type in 'editnote [name of note]' to edit!").append("\n");
        stringBuilder.append("Remember '.txt' must be omitted!").append("\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how listnote command work.
     * @return String of instructions to guide user on listnote command.
     */
    private String listNoteHelp() {
        stringBuilder.append("- ").append("listnote").append("\n");
        stringBuilder.append("Command to list all the pre-existing note created by you!").append("\n");
        stringBuilder.append("Simply type in 'listnote' to refresh the list of notes!").append("\n");
        return stringBuilder.toString();
    }

    /**
     * Provides a brief explanation on how viewnote command work.
     * @return String of instructions to guide user on viewnote command.
     */
    private String viewNoteHelp() {
        stringBuilder.append("- ").append("viewnote").append("\n");
        stringBuilder.append("Command to view a pre-existing note created by you!").append("\n");
        stringBuilder.append("Simply type in 'viewnote [name of note]' to show the note!").append("\n");
        return stringBuilder.toString();
    }

}
