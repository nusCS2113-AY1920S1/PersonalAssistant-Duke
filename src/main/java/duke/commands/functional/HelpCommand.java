package duke.commands.functional;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.models.patients.PatientManager;
import duke.models.tasks.TaskManager;
import duke.storages.StorageManager;
import duke.util.Ui;

import java.util.ArrayList;

public class HelpCommand implements Command {

    ArrayList<String> userCommandList = new ArrayList<String>();

    public HelpCommand() {
        userCommandList.add("Create a task: `add task :<description>`");
        userCommandList.add("Create a patient profile: `add patient "
                + ":<patient name> :<patient NRIC> :<patient room> :<patient remark>`");
        userCommandList.add("Assign a patient to a deadline task: `assign deadline task "
                + ":<patient ID> :<task ID> :<dd/MM/yyyy HHmm>`");
        userCommandList.add("Assign a patient to a period task: `assign period task task "
                + ":<patient ID> :<task ID> :<dd/MM/yyyy HHmm> :<dd/MM/yyyy HHmm>`");
        userCommandList.add("List patients: `list patients`");
        userCommandList.add("List tasks: `list tasks`");
        userCommandList.add("Delete a patient: `delete patient :<patient ID>`");
        userCommandList.add("Delete an assigned task: `delete assigned task :<assigned task ID>`");
        userCommandList.add("Find a patient: `find patient :<patient ID>`");
        userCommandList.add("Find a patient's assigned: `find assigned tasks "
                + ":<patient ID>");
        userCommandList.add("Update a patient's data: `update patient "
                + ":<name/NRIC/room/remark> :<new information>`");
        userCommandList.add("Update a task's description: `update task :<new description>`");
        userCommandList.add("Exit the program: `bye`");


    }

    @Override
    public void execute(AssignedTaskManager patientTask, TaskManager tasks, PatientManager patientList, Ui ui, StorageManager storageManager) throws DukeException {
        ui.showHelpOptions(userCommandList);

    }

    public String getHelpCommands() {
        String output = "Here is the available list of user commands and their respective formats:\n\n";
        for (String command : userCommandList) {
            output += "- " + command + "\n";
        }
        return output;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
