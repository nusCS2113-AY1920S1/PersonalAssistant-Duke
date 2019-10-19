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

public class FindPatientCommand extends Command {

    private String command;
    private boolean hasBeenAddedBefore = false;

    public FindPatientCommand(String command) {
        this.command = command;
    }

    /**
     * .
     *
     * @param patientTask        .
     * @param tasks              .
     * @param patientManager     .
     * @param ui                 .
     * @param patientTaskStorage .
     * @param taskStorage        .
     * @param patientStorage     .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientManager,
                        Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage,
                        PatientStorage patientStorage, CmdFreqStorage cmdFreqStorage,
                        CommandManager commandManager) throws DukeException {
        this.hasBeenAddedBefore = true;
        String commandName = this.getClass().getSimpleName();
        if (!hasBeenAddedBefore) {
            commandManager.getCmdFreqTable().put(commandName, 1);
        }
        int count = commandManager.getCmdFreqTable().containsKey(commandName)
                    ? commandManager.getCmdFreqTable().get(commandName) : 0;
        commandManager.getCmdFreqTable().put(commandName, count + 1);

        char firstChar = command.charAt(0);
        if (firstChar == '#') {
            int id;
            try {
                id = Integer.parseInt(command.substring(1, command.length()));
            } catch (Exception e) {
                throw new DukeException("Please follow the format 'find patient #<id>' or 'find patient <name>'.");
            }
            Patient patient = patientManager.getPatient(id);
            cmdFreqStorage.save(commandManager.getCmdFreqTable());
            ui.patientsFoundById(patient);
        } else {
            ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(command);
            cmdFreqStorage.save(commandManager.getCmdFreqTable());
            ui.patientsFoundByName(patientsWithSameName, command);
        }
    }

    /**
     * .
     *
     * @return .
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
