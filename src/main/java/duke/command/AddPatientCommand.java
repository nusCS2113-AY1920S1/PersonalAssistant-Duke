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

public class AddPatientCommand extends Command {

    private Patient newPatient;

    /**
     * .
     *
     * @param patientInfo .
     * @throws DukeException .
     */
    public AddPatientCommand(String[] patientInfo) throws DukeException {
        super();
        try {
            this.newPatient = new Patient(patientInfo[0], patientInfo[1], patientInfo[2], patientInfo[3]);

        } catch (Exception e) {
            throw new DukeException("Please follow the format 'add patient <name> <NRIC> <Room> <remark>'. ");
        }
    }

    @Override
    public void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientList, Ui ui,
                        PatientTaskStorage patientTaskStorage, TaskStorage taskStorage,
                        PatientStorage patientStorage, CounterStorage counterStorage,
                        CommandCounter commandCounter) throws DukeException {

        //this.hasBeenAddedBefore = true;
        String commandName = this.getClass().getSimpleName();
        commandCounter.runCommandCounter(commandCounter.getCommandTable(), commandName);
        patientList.addPatient(newPatient);
        patientStorage.save(patientList.getPatientList());
        counterStorage.save(commandCounter.getCommandTable());
        ui.patientAdded(newPatient);
    }

    @Override
    public boolean isExit() {
        return false;
    }


}
