package duke.command;

import duke.core.DukeException;
import duke.patient.PatientManager;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTaskList;
import duke.core.Ui;
import duke.task.TaskManager;

public class HelpCommand extends Command {

    @Override
    public void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientList, Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        //ui.showHelpCommand();

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
