//@@lmtaek

package duke.commands;

import duke.commands.assignedtask.AssignDeadlineTaskCommand;
import duke.commands.assignedtask.AssignPeriodTaskCommand;
import duke.commands.assignedtask.DeleteAssignedTaskCommand;
import duke.commands.assignedtask.FindAssignedTaskCommand;
import duke.commands.functional.DukeCommand;
import duke.commands.functional.ExitCommand;
import duke.commands.functional.HelpCommand;
import duke.commands.functional.PieChartCommand;
import duke.commands.functional.UndoCommand;
import duke.commands.patient.AddPatientCommand;
import duke.commands.patient.DeletePatientCommand;
import duke.commands.patient.FindPatientCommand;
import duke.commands.patient.ListPatientsCommand;
import duke.commands.patient.UpdatePatientCommand;
import duke.commands.task.AddTaskCommand;
import duke.commands.task.DeleteTaskCommand;
import duke.commands.task.FindTaskCommand;
import duke.commands.task.ListTasksCommand;
import duke.commands.task.UpcomingTasksCommand;
import duke.commands.task.UpdateTaskCommand;
import duke.exceptions.DukeException;
import duke.util.DukeUi;
import duke.util.Parser;
import duke.util.TypoCorrector;

import java.time.LocalDateTime;

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
            DukeUi.getDukeUi().typoCorrection(possibleCommand);
            userInput = possibleCommand;
        }

        String[] command = userInput.toLowerCase().split(":");
        String keyWord = command[0].trim();

        Parser parser = new Parser(userInput);
        switch (keyWord) {
        case "add patient":
            try {
                return new AddPatientCommand(parser.parseUserInput());
            } catch (Exception e) {
                throw new DukeException("Please follow the  "
                        + "`add patient :<patient name> :<NRIC> :<patient room> :<patient_remark>` "
                        + "format.");
            }
        case "add task":
            try {
                return new AddTaskCommand(parser.parseUserInput()[0]);
            } catch (Exception e) {
                throw new DukeException("Please follow the `add task :<task description>` format.");
            }
        case "assign deadline task":
            try {
                return new AssignDeadlineTaskCommand(parser.parseUserInput());
            } catch (Exception e) {
                throw new DukeException("Please follow the "
                        + "`assign deadline task :#<patient id> :#<task id> "
                        + ":<dd/MM/YYYY HHmm>` format.");
            }
        case "assign period task":
            try {
                return new AssignPeriodTaskCommand(parser.parseUserInput());
            } catch (Exception e) {
                throw new DukeException("Please follow the "
                        + "`assign event task :#<patient id> :#<task ID> "
                        + ":<dd/MM/YYYY HHmm> to <dd/MM/YYYY HHmm>` format.");
            }
        case "list patients":
            return new ListPatientsCommand();
        case "list tasks":
            return new ListTasksCommand();
        case "delete assigned task":
            try {
                return new DeleteAssignedTaskCommand(parser.parseUserInput()[0]);
            } catch (Exception e) {
                throw new DukeException("Please follow the `delete assigned task :#<patient id>"
                        + " :#<task id>` or "
                        + " `delete patient task :%<unique assigned task id>` format.");
            }
        case "delete patient":
            try {
                return new DeletePatientCommand(parser.parseUserInput()[0]);
            } catch (Exception e) {
                throw new DukeException("Please follow the `delete patient :#<patient id>` format");
            }
        case "delete task":
            try {
                return new DeleteTaskCommand(parser.parseUserInput()[0]);
            } catch (Exception e) {
                throw new DukeException("Please follow the `delete task :#<task id>` format");
            }
        case "find patient":
            try {
                return new FindPatientCommand(parser.parseUserInput()[0]);
            } catch (Exception e) {
                throw new DukeException("Please use the `find patient :#<patient id>` format.");
            }
        case "find task":
            try {
                return new FindTaskCommand(parser.parseUserInput()[0]);
            } catch (Exception e) {
                throw new DukeException("Please use the `find task :#<task id>` format.");
            }
        case "find assigned tasks":
            try {
                return new FindAssignedTaskCommand(parser.parseUserInput()[0]);
            } catch (Exception e) {
                throw new DukeException("Please use the `find assigned tasks "
                        + ":#<patient id>` format.");
            }
        case "update patient":
            try {
                return new UpdatePatientCommand(parser.parseUserInput());
            } catch (Exception e) {
                throw new DukeException("Please use the `update patient :<patient name> or #<patient id>"
                        + ":<name/NRIC/room/remark> :<updated info>` format.");
            }
        case "update task":
            try {
                return new UpdateTaskCommand(parser.parseUserInput());
            } catch (Exception e) {
                throw new DukeException("Please use the `update task :#<task id>"
                        + " :<updated description>` format.");
            }
        case "show upcoming tasks":
            return new UpcomingTasksCommand(LocalDateTime.now());
        case "duke":
            return new DukeCommand();
        case "bye":
            return new ExitCommand();
        case "undo":
            return new UndoCommand();
        case "help":
            return new HelpCommand();
        case "piechart":
            return new PieChartCommand();
        default:
            throw new DukeException("Could not understand user input.");
        }
    }
}
