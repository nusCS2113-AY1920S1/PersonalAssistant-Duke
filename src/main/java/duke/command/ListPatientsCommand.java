package duke.command;

import duke.core.CommandManager;
import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.storage.CmdFreqStorage;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTaskList;
import duke.task.TaskManager;

import java.util.ArrayList;

public class ListPatientsCommand extends Command {
    private boolean hasBeenAddedBefore = false;

    public ListPatientsCommand() {
        super();
    }

    /**
     * .
     *
     * @param patientTask        .
     * @param tasks              .
     * @param patientList        .
     * @param ui                 .
     * @param patientTaskStorage .
     * @param taskStorage        .
     * @param patientStorage     .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientList,
                        Ui ui, PatientTaskStorage patientTaskStorage,
                        TaskStorage taskStorage, PatientStorage patientStorage, CmdFreqStorage cmdFreqStorage,
                        CommandManager commandManager) throws DukeException {
        this.hasBeenAddedBefore = true;
        String commandName = this.getClass().getSimpleName();
        if (!hasBeenAddedBefore) {
            commandManager.getCmdFreqTable().put(commandName, 1);
        }
        int count = commandManager.getCmdFreqTable().containsKey(commandName)
                    ? commandManager.getCmdFreqTable().get(commandName) : 0;
        commandManager.getCmdFreqTable().put(commandName, count + 1);
        ArrayList<Patient> list = patientList.getPatientList();
        cmdFreqStorage.save(commandManager.getCmdFreqTable());
        ui.listAllPatients(list);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
