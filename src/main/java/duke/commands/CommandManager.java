//@@lmtaek

package duke.commands;

import duke.commands.assignedtask.AssignTaskToPatientCommand;
import duke.commands.assignedtask.DeleteAssignedTaskCommand;
import duke.commands.assignedtask.FindAssignedTaskCommand;
import duke.commands.functional.DukeCommand;
import duke.commands.functional.ExitCommand;
import duke.commands.functional.UndoCommand;
import duke.commands.patient.AddPatientCommand;
import duke.commands.patient.DeletePatientCommand;
import duke.commands.patient.FindPatientCommand;
import duke.commands.patient.ListPatientsCommand;
import duke.commands.patient.UpdatePatientCommand;
import duke.commands.task.AddTaskCommand;
import duke.commands.task.DeleteTaskCommand;
import duke.commands.task.ListTasksCommand;
import duke.commands.task.UpdateTaskCommand;
import duke.exceptions.DukeException;
import duke.util.Parser;
import duke.util.TypoCorrector;
import duke.util.Ui;

/**
 * Represents a Parser that parses user input into a specific
 * type of Command.
 */
public class CommandManager {

    /**
     * Decides which command to execute based on keywords available in the user's input.
     *
     * @param userInput The user's input.
     * @return The command dictated by the user.
     */
    public static Command manageCommand(String userInput) throws DukeException {
        userInput = userInput.trim();
        String possibleCommand = TypoCorrector.commandCorrection(userInput);
        if (!possibleCommand.equals(userInput)) {
            Ui.getUi().typoCorrection(possibleCommand);
            userInput = possibleCommand;
        }
        String[] command = userInput.toLowerCase().split(":");
        String keyWord = command[0].trim();

        Parser parser = new Parser(userInput);
        switch (keyWord) {
        case "add patient":
            return new AddPatientCommand(parser.parseAddPatient());
        case "add task":
            return new AddTaskCommand(parser.parseAddTask());
        case "assign deadline task":
            return new AssignTaskToPatientCommand(parser.parseAssignDeadlineTask());
        case "assign event task":
            return new AssignTaskToPatientCommand(parser.parseAssignEventTask());
        case "list patients":
            return new ListPatientsCommand();
        case "list tasks":
            return new ListTasksCommand();
        case "delete assigned task":
            return new DeleteAssignedTaskCommand(parser.parseDeleteAssignedTask());
        case "delete patient":
            return new DeletePatientCommand(parser.parseDeletePatient());
        case "delete task":
            return new DeleteTaskCommand(parser.parseDeleteTask());
        case "find patient":
            return new FindPatientCommand((parser.parseFindPatient()));
        case "find assigned tasks":
            return new FindAssignedTaskCommand((parser.parseFindAssignedTasks()));
        case "update patient":
            return new UpdatePatientCommand(parser.parseUpdatePatient());
        case "update task":
            return new UpdateTaskCommand(parser.parseUpdateTask());
        case "duke":
            return new DukeCommand();
        case "bye":
            return new ExitCommand();
        case "undo" :
            return new UndoCommand();
        default:
            throw new DukeException("Could not understand user input.");
        }
    }
}
