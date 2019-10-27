package duke.util.mementopattern;

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

public class MementoParser {

    /**
     *  .
     * @return .
     */
    public static String getSaveFlag(Command command) {
        if ((command instanceof AddPatientCommand) || (command instanceof AddTaskCommand)
                || (command instanceof AssignPeriodTaskCommand) || (command instanceof DeletePatientCommand)
                || (command instanceof DeleteAssignedTaskCommand) || (command instanceof DeleteTaskCommand)
                || (command instanceof UpdatePatientCommand) || (command instanceof UpdateTaskCommand)) {
            return "save";
        } else if (command instanceof UndoCommand) {
            return "pop";
        } else {
            return "ignore";
        }
    }
}
