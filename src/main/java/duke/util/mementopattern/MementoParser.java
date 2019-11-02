//@@author WEIFENG-NUSCEG

package duke.util.mementopattern;

import duke.commands.assignedtask.AssignDeadlineTaskCommand;
import duke.commands.assignedtask.AssignPeriodTaskCommand;
import duke.commands.patient.AddPatientCommand;
import duke.commands.task.AddTaskCommand;
import duke.commands.Command;
import duke.commands.patient.DeletePatientCommand;
import duke.commands.assignedtask.DeleteAssignedTaskCommand;
import duke.commands.task.DeleteTaskCommand;
import duke.commands.patient.UpdatePatientCommand;
import duke.commands.task.UpdateTaskCommand;
import duke.commands.functional.UndoCommand;

/**
 * This is parser that checks the type of Command it takes in, if the command has anything to do with
 * the modification of the internal state of the object, it will return a "save", if it is an undo command
 * the parser will return a "pop", else it will return a "ignore".
 */
public class MementoParser {

    /**
     * This method will check the type of the command it received and return respective status.
     *
     * @param command a command received from duke.
     * @return a string which indicates the operation type (save, pop, ignore)
     */
    public static String getSaveFlag(Command command) {
        if ((command instanceof AddPatientCommand) || (command instanceof AddTaskCommand)
                || (command instanceof AssignPeriodTaskCommand) || (command instanceof DeletePatientCommand)
                || (command instanceof DeleteAssignedTaskCommand) || (command instanceof DeleteTaskCommand)
                || (command instanceof UpdatePatientCommand) || (command instanceof UpdateTaskCommand)
                || (command instanceof AssignDeadlineTaskCommand)) {
            return "save";
        } else if (command instanceof UndoCommand) {
            return "pop";
        } else {
            return "ignore";
        }
    }


}
