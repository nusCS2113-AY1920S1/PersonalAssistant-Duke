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
//public class UpdatePatientCommand extends Command {
//
//    private String command;
//
//    /**
//     * .
//     *
//     * @param command .
//     */
//    public UpdatePatientCommand(String command) {
//        this.command = command;
//    }
//
//    /**
//     * .
//     *
//     * @param patientTask        .
//     * @param tasks              .
//     * @param patientManager     .
//     * @param ui                 .
//     * @param patientTaskStorage .
//     * @param taskStorage        .
//     * @param patientStorage     .
//     * @throws DukeException .
//     */
//    @Override
//    public void execute(PatientTaskList patientTask, TaskManager tasks, PatientManager patientManager,
//                        Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage,
//                        PatientStorage patientStorage) throws DukeException {
//        String[] tempCommand = command.split(" ", 3);
//        char firstChar = tempCommand[0].charAt(0);
//        if (firstChar == '#') {
//            int id;
//            try {
//                id = Integer.parseInt(tempCommand[0].substring(1, tempCommand[0].length()));
//                Patient patientToBeUpdated = patientManager.getPatient(id);
//                if (tempCommand[1].toLowerCase().equals("name")) {
//                    patientToBeUpdated.setName(tempCommand[2]);
//                } else if (tempCommand[1].toLowerCase().equals("nric")) {
//                    patientToBeUpdated.setNric(tempCommand[2]);
//                } else if (tempCommand[1].toLowerCase().equals("room")) {
//                    patientToBeUpdated.setRoom(tempCommand[2]);
//                } else {
//                    throw new DukeException("You can only update 'Name', 'NRIC', or 'Room' of the patient");
//                }
//
//                patientStorage.save(patientManager.getPatientList());
//
//                ui.showUpdatedSuccessfully();
//                ui.showPatientInfo(patientToBeUpdated);
//            } catch (Exception e) {
//                throw new DukeException(
//                        "Please follow the format 'update patient #<id> <Name/NRIC/Room> <new information>'.");
//            }
//        } else {
//            throw new DukeException(
//                    "Please follow the format 'update patient #<id> <Name/NRIC/Room> <new information>'.");
//        }
//    }
//
//    /**
//     * .
//     *
//     * @return .
//     */
//    @Override
//    public boolean isExit() {
//        return false;
//    }
//}
