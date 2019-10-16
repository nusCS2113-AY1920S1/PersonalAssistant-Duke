package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;
import duke.task.ToDoTask;

public class NewPatientCommand extends ArgCommand {
    /**
     * Creates a new NewPatientCommand, setting the parameters for its inherited methods.
     */
    public NewPatientCommand() {
        emptyArgMsg = "You didn't tell me anything about the patient!";
        cmdArgLevel = ArgLevel.NONE;
        switches.put("name", ArgLevel.REQUIRED);
        switches.put("bed", ArgLevel.REQUIRED);
        switches.put("allergies", ArgLevel.REQUIRED);
        switches.put("go", ArgLevel.OPTIONAL);
        switches.put("height", ArgLevel.OPTIONAL);
        switches.put("weight", ArgLevel.OPTIONAL);
        switches.put("age", ArgLevel.OPTIONAL);
        switches.put("number", ArgLevel.OPTIONAL);
        switches.put("address", ArgLevel.OPTIONAL);
        switches.put("history", ArgLevel.OPTIONAL);
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);

        

        String addStr = core.taskList.addTask(new ToDoTask(arg));
        core.storage.writeTaskFile(core.taskList.getFileStr());
        core.ui.print(core.taskList.getAddReport(System.lineSeparator() + "  " + addStr, 1));
    }
}
