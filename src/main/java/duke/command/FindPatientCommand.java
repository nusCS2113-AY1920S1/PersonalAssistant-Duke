package duke.command;

import duke.core.CommandManager;
import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.statistic.CommandCounter;
import duke.storage.CounterStorage;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTaskList;
import duke.task.TaskManager;

import java.util.ArrayList;

public class FindPatientCommand extends Command {

    private String command;

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
                        PatientStorage patientStorage, CounterStorage counterStorage,
                        CommandCounter commandCounter) throws DukeException {
        String commandName = this.getClass().getSimpleName();
        commandCounter.runCommandCounter(commandCounter.getCommandTable(), commandName);
        char firstChar = command.charAt(0);
        if (firstChar == '#') {
            int id;
            try {
                id = Integer.parseInt(command.substring(1, command.length()));
            } catch (Exception e) {
                throw new DukeException("Please follow the format 'find patient #<id>' or 'find patient <name>'.");
            }
            Patient patient = patientManager.getPatient(id);
            counterStorage.save(commandCounter.getCommandTable());
            ui.patientsFoundById(patient);
        } else {
            ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(command);
            counterStorage.save(commandCounter.getCommandTable());
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
