//package duke.command;
//
//import duke.core.DukeException;
//import duke.core.Ui;
//import duke.patient.Patient;
//import duke.patient.PatientManager;
//import duke.storage.PatientStorage;
//import duke.storage.PatientTaskStorage;
//import duke.storage.TaskStorage;
//import duke.relation.PatientTaskList;
//import duke.task.TaskManager;
//
//import java.util.ArrayList;
//
//public class ListPatientsCommand extends Command {
//
//    public ListPatientsCommand() {
//        super();
//    }
//
//    /**
//     * .
//     *
//     * @param patientTask        .
//     * @param tasks              .
//     * @param patientList        .
//     * @param ui                 .
//     * @param patientTaskStorage .
//     * @param taskStorage        .
//     * @param patientStorage     .
//     * @throws DukeException .
//     */
//    @Override
//    public void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientList,
//                        Ui ui, PatientTaskStorage patientTaskStorage,
//                        TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
//        ArrayList<Patient> list = patientList.getPatientList();
//        ui.listAllPatients(list);
//    }
//
//    @Override
//    public boolean isExit() {
//        return false;
//    }
//}
