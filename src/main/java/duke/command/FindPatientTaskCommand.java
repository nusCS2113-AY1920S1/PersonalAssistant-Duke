package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.relation.PatientTask;
import duke.relation.PatientTaskList;
import duke.statistic.Counter;
import duke.storage.CounterStorage;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.task.Task;
import duke.task.TaskManager;

import java.util.ArrayList;

public class FindPatientTaskCommand extends Command {

    private String patientTaskInfo;
    private int id;

    /**
     * .
     *
     * @param patientTaskInfo .
     */
    public FindPatientTaskCommand(String patientTaskInfo) throws DukeException {
        super();
        this.patientTaskInfo = patientTaskInfo;

        if (patientTaskInfo.charAt(0) == '#') {
            try {
                this.id = Integer.parseInt(patientTaskInfo.substring(1));

            } catch (Exception e) {
                throw new DukeException("Failed to find ID.");
            }
        }
    }

    /**
     * .
     *
     * @param patientTaskList    .
     * @param tasksManager       .
     * @param patientManager     .
     * @param ui                 .
     * @param patientTaskStorage .
     * @param taskStorage        .
     * @param patientStorage     .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTaskList, TaskManager tasksManager, PatientManager patientManager,
                        Ui ui, PatientTaskStorage patientTaskStorage,
                        TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        if (id != 0) {
            try {
                Patient patient = patientManager.getPatient(id);
                ArrayList<PatientTask> patientTask = patientTaskList.getPatientTask(id);
                ArrayList<Task> tempTask = new ArrayList<>();
                for (PatientTask tempPatientTask : patientTask) {
                    tempTask.add(tasksManager.getTask(tempPatientTask.getTaskID()));
                }
                ui.patientTaskFound(patient, patientTask, tempTask);
            } catch (Exception e) {
                throw new DukeException("Please follow the format 'find patient task #<id>' or 'find patient task <name>'.");
            }
        } else {
            String name = patientTaskInfo.toLowerCase();
            ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(name);
            ArrayList<PatientTask> patientWithTask = new ArrayList<>();
            ArrayList<Task> tempTask = new ArrayList<>();

            try {
                for (Patient patient : patientsWithSameName) {
                    if (patient.getName().toLowerCase().equals(name)) {
                        patientWithTask = patientTaskList.getPatientTask(patient.getID());
                    }
                }
                for (PatientTask tempPatientTask : patientWithTask) {
                    tempTask.add(tasksManager.getTask(tempPatientTask.getTaskID()));
                }
                ui.patientTaskFound(patientsWithSameName.get(0), patientWithTask, tempTask);
            } catch (Exception e) {
                throw new DukeException(e.getMessage()
                        + "Please follow the format 'find patient task #<id>' or 'find patient task <name>'.");
            }
        }
    }


    @Override
    public boolean isExit() {
        return false;
    }

}