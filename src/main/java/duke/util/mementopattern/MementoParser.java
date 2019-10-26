package duke.util.mementopattern;

import duke.commands.AddPatientCommand;
import duke.commands.AddStandardTaskCommand;
import duke.commands.AssignTaskToPatientCommand;
import duke.commands.Command;
import duke.commands.DeletePatientCommand;
import duke.commands.DeletePatientTaskCommand;
import duke.commands.DeleteTaskCommand;
import duke.commands.UpdatePatientCommand;
import duke.commands.UpdateTaskCommand;
import duke.commands.UndoCommand;

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
