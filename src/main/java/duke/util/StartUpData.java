//@@author lmtaek

package duke.util;

import duke.exceptions.DukeException;
import duke.models.assignedtasks.AssignedTask;
import duke.models.assignedtasks.AssignedTaskWithDate;
import duke.models.assignedtasks.AssignedTaskWithPeriod;
import duke.models.patients.Patient;
import duke.models.tasks.Task;

import java.util.ArrayList;

public class StartUpData {

    ArrayList<Patient> newPatientList = new ArrayList<Patient>();
    ArrayList<Task> newTaskList = new ArrayList<Task>();
    ArrayList<AssignedTask> newAssignedTaskList = new ArrayList<AssignedTask>();


    /**
     * Creates a pre-made Patient array list to pre-populate Dukepital's data,
     * when no data is given beforehand.
     * @return The pre-generated array list of patients.
     */
    public ArrayList<Patient> getPatients() {
        int[] patientIDs = {1, 2, 3, 4, 5, 6};
        String[] patientNames = {"John Doe", "Barbara Jones", "Synthia Smith",
                "Sam Flores", "Emily Roth", "Nick Wong"};
        String[] nricNumbers = {"G0001234L", "G7894560W", "G1432598R", "S0897236J",
                "F7891239P", "S1324567P"};
        String[] rooms = {"9B", "8A", "5R", "12C", "19R", "24B"};
        String[] remarks = {"Hasn't had an appetite for past 2 days",
                "Complaining about foot injury", "Allergy to shellfish", "Nothing",
                "Prefers privacy", "Symptoms are improving"};

        for (int i = 0; i < patientIDs.length; i++) {
            newPatientList.add(new Patient(patientIDs[i], patientNames[i],
                    nricNumbers[i], rooms[i], remarks[i]));
        }

        return newPatientList;
    }

    /**
     * Creates a pre-made Task array list to pre-populate Dukepital's data,
     * when no data is given beforehand.
     * @return The pre-generated array list of tasks.
     */
    public ArrayList<Task> getTasks() {
        int[] taskIDs = {1, 2, 3, 4, 5, 6, 7, 8};
        String[] taskDescriptions = {"Take medicine", "Check temperature", "Prep for surgery",
                "Bring lunch", "Change sheets", "Let family visit", "Give shot", "Change IV"};

        for (int i = 0; i < taskIDs.length; i++) {
            newTaskList.add(new Task(taskIDs[i], taskDescriptions[i]));
        }

        return newTaskList;
    }

    /**
     * Creates a pre-made AssignedTask array list to pre-populate Dukepital's data,
     * when no data is given beforehand.
     * @return The pre-generated array list of assigned tasks.
     */
    public ArrayList<AssignedTask> getAssignedTasks() throws DukeException {

        newAssignedTaskList.add(new AssignedTaskWithDate(4, 1,
                "14/11/2019 1200", "deadline"));
        newAssignedTaskList.add(new AssignedTaskWithPeriod(3, 5, "16/11/2019 0100",
                "19/11/2019 2359", "period"));
        newAssignedTaskList.add(new AssignedTaskWithDate(1, 2,
                "20/11/2019 1200", "deadline"));
        newAssignedTaskList.add(new AssignedTaskWithPeriod(2, 5, "15/11/2019 1000",
                "16/11/2019 1000", "period"));
        newAssignedTaskList.add(new AssignedTaskWithDate(6, 3,
                "12/11/2019 1300", "deadline"));
        newAssignedTaskList.add(new AssignedTaskWithDate(5, 5,
                "15/11/2019 1555", "deadline"));

        return newAssignedTaskList;
    }


}
