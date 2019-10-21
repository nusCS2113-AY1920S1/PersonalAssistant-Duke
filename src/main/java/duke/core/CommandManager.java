package duke.core;

import duke.command.AddPatientCommand;
import duke.command.AddStandardTaskCommand;
import duke.command.AssignTaskToPatientCommand;
import duke.command.Command;
import duke.command.DeletePatientCommand;
import duke.command.DeletePatientTaskCommand;
import duke.command.DeleteTaskCommand;
import duke.command.ExitCommand;
import duke.command.FindPatientCommand;
import duke.command.FindPatientTaskCommand;
import duke.command.ListPatientsCommand;
import duke.command.ListTasksCommand;
import duke.command.UpdatePatientCommand;
import duke.command.UpdateTaskCommand;

/**
 * Represents a Parser that parses user input into a specific
 * type of Command.
 */
public class CommandManager {

    /**
     * Parses a Task from a string array.
     *
     * @param userInput The string array to be parsed.
     * @return The Command received from user.
     */
    public static Command manageCommand(String userInput) throws DukeException {
        userInput = userInput.trim();
        String possibleCommand = TypoCorrector.commandCorrection(userInput);
        if (!possibleCommand.equals(userInput)) {
            if (Ui.getUi().confirmTypoCorrection(possibleCommand, userInput)) {
                userInput = possibleCommand;
            }
        }
        String[] command = userInput.toLowerCase().split("\\s+", 4);
        String firstKeyword = command[0];
        String secondKeyword = "";
        String thirdKeyword = "";
        if (command.length >= 2) {
            secondKeyword = command[1];
        }
        if (command.length >= 3) {
            thirdKeyword = command[2];

        }

        Parser parser = new Parser(userInput);

        switch (firstKeyword) {
        case "add":
            if ((secondKeyword != "") && secondKeyword.equals("patient")) {
                String[] formattedInput = parser.parseAdd();
                AddPatientCommand addPatientCommand = new AddPatientCommand(formattedInput);
                return addPatientCommand;
            } else if ((secondKeyword != "") && secondKeyword.equals("task")) {
                String formattedInput = parser.parseAdd()[0];
                AddStandardTaskCommand addStandardTaskCommand = new AddStandardTaskCommand(formattedInput);
                return addStandardTaskCommand;
            } else {
                throw new DukeException("Add command fails.");
            }
        case "assign":
            return new AssignTaskToPatientCommand(parser.parseAssign());
        case "list":
            String[] tempCommand = parser.parseList();
            String nextKeyword = tempCommand[0].toLowerCase();
            if (nextKeyword.equals("patients")) {
                return new ListPatientsCommand();
            } else if (nextKeyword.equals("tasks")) {
                return new ListTasksCommand();
            } else {
                throw new DukeException("Invalid 'list' command.");
            }
        case "delete":
            if ((secondKeyword != "")
                    && (thirdKeyword != "")
                    && secondKeyword.equals("patient")
                    && thirdKeyword.equals("task")) {
                return new DeletePatientTaskCommand(parser.parseDeletePatientTask());
            } else if ((secondKeyword != "") && secondKeyword.equals("patient")) {
                String formattedInput = parser.parseDeletePatient();
                return new DeletePatientCommand(formattedInput);
            } else if ((secondKeyword != "") && secondKeyword.equals("task")) {
                return new DeleteTaskCommand(parser.parseDeleteTask());
            } else {
                throw new DukeException("Invalid 'delete' command.");
            }
        case "find":
            if ((secondKeyword != "") && secondKeyword.equals("patient")) {
                return new FindPatientCommand(parser.parseFind());
            } else if (secondKeyword.equals("patient") && ((thirdKeyword != "") && thirdKeyword.equals("task"))) {
                return new FindPatientTaskCommand(parser.parseFind());
            } else {
                throw new DukeException("Invalid 'find' command. ");
            }
        case "update":
            if ((secondKeyword != "") && secondKeyword.equals("patient")) {
                String formattedInput = parser.parseUpdatePatient();
                return new UpdatePatientCommand(formattedInput);
            } else if (secondKeyword.equals("task")) {
                String formattedInput = parser.parseUpdateTask();
                return new UpdateTaskCommand(formattedInput);
            } else {
                throw new DukeException("Invalid 'update' command. ");
            }
        case "bye":
            ExitCommand exitCommand = new ExitCommand();
            return exitCommand;
        default:
            throw new DukeException("Could not understand user input");
        }
    }
}
