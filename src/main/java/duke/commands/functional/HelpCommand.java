//@@lmtaek

package duke.commands.functional;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.models.patients.PatientManager;
import duke.models.tasks.TaskManager;
import duke.storages.StorageManager;
import duke.util.DukeUi;

import java.util.ArrayList;

public class HelpCommand implements Command {

    ArrayList<String> userCommandList = new ArrayList<String>();

    /**
     * Lists all available commands + their respective formats for the user.
     */
    public HelpCommand() {
        userCommandList.add("Create a task:\n `add task :<description>`");
        userCommandList.add("Create a patient profile:\n `add patient "
                + ":<patient name> :<patient NRIC> :<patient room> :<patient remark>`");
        userCommandList.add("Assign a patient to a deadline task:\n `assign deadline task "
                + ":<patient ID> :<task ID> :<dd/MM/yyyy HHmm>`");
        userCommandList.add("Assign a patient to a period task:\n `assign period task task "
                + ":<patient ID> :<task ID> :<dd/MM/yyyy HHmm> :<dd/MM/yyyy HHmm>`");
        userCommandList.add("List patients:\n `list patients`");
        userCommandList.add("List tasks:\n `list tasks`");
        userCommandList.add("Delete a patient:\n `delete patient :#<patient ID>`");
        userCommandList.add("Delete a task:\n `delete task :#<task ID>`");
        userCommandList.add("Delete an assigned task:\n `delete assigned task :#<assigned task ID>`");
        userCommandList.add("Find a patient:\n `find patient :#<patient ID> OR <patient name>`");
        userCommandList.add("Find a task:\n `find task :#<task ID> OR <task description>");
        userCommandList.add("Find a patient's assigned tasks:\n `find assigned tasks "
                + ":#<patient ID>");
        userCommandList.add("Update a patient's data:\n `update patient :#<patient id> "
                + ":<name/NRIC/room/remark> :<new information>`");
        userCommandList.add("Update a task's description:\n `update task :#<task id>"
                + ":<new description>`");
        userCommandList.add("Undo an action:\n `undo`");
        userCommandList.add("View help guide:\n `help`");
        userCommandList.add("Exit the program:\n `bye`");


    }

    @Override
    public void execute(AssignedTaskManager patientTask, TaskManager tasks,
                        PatientManager patientList, DukeUi dukeUi,
                        StorageManager storageManager) throws DukeException {
        dukeUi.showHelpOptions(userCommandList);

    }

    /**
     * Gives a string output with all available commands + formats.
     * @return String with commands + input formats.
     */
    public ArrayList<String> getHelpCommands() {
        return userCommandList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
