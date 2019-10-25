package duke.mementopattern;

import duke.command.AddPatientCommand;
import duke.command.AddStandardTaskCommand;
import duke.command.AssignTaskToPatientCommand;
import duke.command.Command;
import duke.command.DeletePatientCommand;
import duke.command.DeletePatientTaskCommand;
import duke.command.DeleteTaskCommand;
import duke.command.UpdatePatientCommand;
import duke.command.UpdateTaskCommand;
import duke.command.UndoCommand;

public class MementoParser {

    /**
     *  .
     * @return .
     */
    public static String getSaveFlag(Command command) {
        if ((command instanceof AddPatientCommand) || (command instanceof AddStandardTaskCommand)
                || (command instanceof AssignTaskToPatientCommand) || (command instanceof DeletePatientCommand)
                || (command instanceof DeletePatientTaskCommand) || (command instanceof DeleteTaskCommand)
                || (command instanceof UpdatePatientCommand) || (command instanceof UpdateTaskCommand)) {
            return "save";
        } else if (command instanceof UndoCommand) {
            return "pop";
        } else {
            return "ignore";
        }
    }
}
