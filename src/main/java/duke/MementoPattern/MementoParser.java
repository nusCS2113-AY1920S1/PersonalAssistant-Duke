package duke.MementoPattern;

import duke.command.*;

public class MementoParser {

    public static String getSaveFlag(Command command){
        if ((command instanceof AddPatientCommand) || (command instanceof AddStandardTaskCommand)
                || (command instanceof AssignTaskToPatientCommand) || (command instanceof DeletePatientCommand)
                || (command instanceof DeletePatientTaskCommand) || (command instanceof DeleteTaskCommand)
                || (command instanceof UpdatePatientCommand) || (command instanceof UpdateTaskCommand)){
            return "save";
        } else if (command instanceof UndoCommand) {
            return "pop";
        } else {
            return "ignore";
        }
    }
}
