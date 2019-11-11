package duke.util;

import duke.exceptions.DukeException;
import duke.models.assignedtasks.AssignedTask;
import duke.models.assignedtasks.UpcomingTasks;
import duke.models.patients.Patient;
import duke.models.tasks.Task;

import java.util.ArrayList;

//@@author HUANGXUANKUN

/**
 * Represents the necessary dukeUi elements for user interaction.
 */
public class DukeUi {
    private static DukeUi dukeUi;
    private String dukeResponses = "";
    private String userInput;

    /**
     * static method to create instance of Singleton class.
     *
     * @return DukeUi
     */
    public static DukeUi getDukeUi() {
        if (dukeUi == null) {
            dukeUi = new DukeUi();
        }
        return dukeUi;
    }

    /**
     * Increment the response to duke response collections for print out in GUI.
     *
     * @param dukeResponse duke's response for user
     */
    public void printDukeResponse(String dukeResponse) {
        this.dukeResponses = dukeResponses + dukeResponse + "\n";
        System.out.println(dukeResponse);
    }

    /**
     * return all the responses for current user input.
     *
     * @return the collections of all responses for user input
     */
    public String getDukeResponses() {
        return this.dukeResponses;
    }

    /**
     * Clear Duke's responses for previous user input.
     */
    public void clearResponses() {
        this.dukeResponses = "";
    }

    /**
     * Get user full command from GUI.
     *
     * @param userInput User input
     */
    public void readUserInputFromGui(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Reads user instruction.
     *
     * @return A string that represents the user instruction.
     */
    public String readUserInput() {
        return this.userInput;
    }

    /**
     * Print out message to indicate task is added.
     *
     * @param standardTask the standard task contains its description
     */
    public void taskAdded(Task standardTask) {
        printDukeResponse("Got it. I've added this task: \n"
            + standardTask.getDescription());
    }

    /**
     * Print out message to show Patient's info.
     *
     * @param patient it contains patient's information
     */
    public void showPatientInfo(Patient patient) {
        printDukeResponse("Name: "
            + patient.getName()
            + "  Id: "
            + patient.getId()
            + "\nNRIC: "
            + patient.getNric()
            + "  Room: "
            + patient.getRoom()
            + "\nRemark: "
            + patient.getRemark());
    }

    /**
     * Print out the info of a task.
     *
     * @param task any task which contains description
     */
    public void showTaskInfo(Task task) {
        printDukeResponse("Task: " + task.getDescription());
    }

    /**
     * Display all the patients with the similar name.
     *
     * @param patients a list contains patient with similar name
     * @param name     the name given by user for search
     */
    public void patientsFoundByName(ArrayList<Patient> patients, String name) {
        if (patients.size() > 0) {
            printDukeResponse("Got it. " + patients.size() + " patients is/are found with name: " + name);
            int i = 1;
            for (Patient patient : patients) {
                printDukeResponse("Patient #" + i);
                showPatientInfo(patient);
                showLine();
                i++;
            }
        } else {
            printDukeResponse("No patient was found with name: " + name);
        }
    }

    /**
     * Display all the tasks with same description name.
     *
     * @param tasks       task with same name being found
     * @param description the description of task being search
     */
    public void tasksFoundByDescription(ArrayList<Task> tasks, String description) {
        if (tasks.size() > 0) {
            printDukeResponse("Got it. " + tasks.size() + " tasks is/are found with description: " + description);
            int i = 1;
            for (Task task : tasks) {
                printDukeResponse("Task #" + i);
                showTaskInfo(task);
                showLine();
                i++;
            }
        } else {
            printDukeResponse("No task was found with description: " + description);
        }
    }
    //@@author

    //@@author kkeejjuunn

    /**
     * Print out patient is being found.
     *
     * @param patient patient being found
     */
    public void patientsFoundById(Patient patient) {
        printDukeResponse("Got it. The patient is found.");
        showPatientInfo(patient);
    }

    //@@author kkeejjuunn

    /**
     * Print out task is being found.
     *
     * @param task task being found
     */
    public void taskFoundById(Task task) {
        printDukeResponse("Got it. The task is found.");
        showTaskInfo(task);
    }

    /**
     * Print message of a patient is being added.
     *
     * @param patient it contains info of the patient being added
     */
    public void patientAdded(Patient patient) {
        printDukeResponse("Got it. The following patient has been added:  ");
        showPatientInfo(patient);
    }


    /**
     * Print message of a patient is being assigned to task.
     *
     * @param assignedTask it contains the patient task relation and its info
     * @param patientName  the name of patient being assigned
     * @param taskName     the name of task which is associated with the patient
     */
    public void patientTaskAssigned(AssignedTask assignedTask, String patientName, String taskName) {
        printDukeResponse("Got it. The following Patient ID: "
            + assignedTask.getPid()
            + " "
            + patientName
            + " has been assigned the Task ID: "
            + assignedTask.getTid()
            + " "
            + taskName);
    }

    //@@author kkeejjuunn

    /**
     * It shows message of a patient being deleted.
     */
    public void patientDeleted() {
        printDukeResponse("Got it. The patient is deleted.\n");
    }

    //@@author kkeejjuunn

    /**
     * It shows message of a task being deleted successfully.
     */
    public void taskDeleted() {
        printDukeResponse("Got it. The task is deleted.");
    }


    /**
     * It lists out all info of patients.
     *
     * @param id the patients to be listed out
     */
    public void patientTaskDeleted(int id) {
        printDukeResponse("Got it. The task with unique ID: " + id + " has been deleted");
    }

    /**
     * It lists out all info of patients.
     *
     * @param patient the patients to be listed out
     */
    public void patientTaskAllDeleted(Patient patient) {
        printDukeResponse("Got it. The tasks belong to: ");
        printDukeResponse(patient.getName());
        printDukeResponse("has been deleted");
    }

    /**
     * It lists out all info of patients.
     *
     * @param patients the patients to be listed out
     */
    public void listAllPatients(ArrayList<Patient> patients) {
        for (Patient patient : patients) {
            showPatientInfo(patient);
            showLine();
        }
    }

    /**
     * It lists out all info of tasks.
     *
     * @param taskList the tasks to be listed out
     */
    public void listAllTasks(ArrayList<Task> taskList) {
        int index = 1;
        printDukeResponse("Here's a list of your tasks: \n");
        for (Task task : taskList) {
            printDukeResponse(index
                + ". "
                + task.getDescription()
                + " (ID: "
                + task.getId()
                + ")"
                + "\n");
            index++;
        }
    }

    /**
     * Shows a divider line.
     */
    public void showLine() {
        printDukeResponse("______________________");
    }


    /**
     * Shows bye message to user.
     */
    public void exitInformation() {
        printDukeResponse("Bye. Hope to see you again soon!");
    }


    /**
     * Show information is being updated successfully.
     */
    public void showUpdatedSuccessfully() {
        printDukeResponse("I have successfully updated the following information: \n");
    }

    /**
     * Show message of loading failure.
     */
    public void showLoadingError() {
        printDukeResponse("Failed to load from local data file!");
    }

    /**
     * Show message of undo.
     */
    public void showUndoSuccess() {
        printDukeResponse("Undo command received");
    }

    /**
     * It displays all the tasks assigned to the patient who is to be deleted.
     *
     * @param patient     patient given by user
     * @param patientTask list of patienttasks being found associated with the patient
     * @param tasks       list of tasks assigned to the patient
     */
    public void patientTaskFound(Patient patient, ArrayList<AssignedTask> patientTask, ArrayList<Task> tasks) {
        printDukeResponse("The tasks of patient " + patient.getId() + " " + patient.getName() + " is found : \n");
        for (int i = 0; i < patientTask.size(); i++) {
            printDukeResponse(tasks.get(i).getId() + ". " + tasks.get(i).getDescription() + "\n");
            printDukeResponse(patientTask.get(i).toString() + "\n");
        }
    }

    //@@author kkeejjuunn

    /**
     * It shows all info of patientTasks found which are associated with the task given by user.
     *
     * @param task     task given by user
     * @param patients list of patients relate to task
     */
    public void taskPatientFound(Task task, ArrayList<Patient> patients) {
        printDukeResponse("The task " + task.getId() + " " + task.getDescription()
            + " is assigned to following patient(s) : \n");
        for (int i = 0; i < patients.size(); i++) {
            printDukeResponse(patients.get(i).getId() + ". " + patients.get(i).getName() + "\n");
        }
    }

    //@@author kkeejjuunn

    /**
     * It shows all info of tasks found which are associated with the patient given by user.
     *
     * @param patient patient given by user
     * @param tasks   list of tasks relate to patienttasks found
     */
    public void assignedTasksFoundWhenDeletePatient(Patient patient, ArrayList<Task> tasks) {
        printDukeResponse(patient.getName() + " is assigned with the following tasks : \n");
        for (int i = 0; i < tasks.size(); i++) {
            printDukeResponse(tasks.get(i).getId() + ". " + tasks.get(i).getDescription() + "\n");
        }
    }

    //@@author qjie7

    /**
     * Provide the necessary task details from the user for short cut feature.
     *
     * @param info The type of task information that want to be retrieved.
     * @return Either the task name or task id depend on the parameter info.
     * @throws DukeException throw a dukeException with error message for debugging.
     * @author QIAN JIE
     * @version 1.3
     */

    public String getTaskInfo(String info) throws DukeException {

        if (info.equals("name")) {
            printDukeResponse("Task Name ?");
            String taskName = dukeUi.readUserInput();
            return taskName;
        } else if (info.equals("id")) {
            printDukeResponse("Task ID?");
            String taskId = "#" + dukeUi.readUserInput();
            return taskId;
        } else if (info.equals("change")) {
            printDukeResponse("What would you like to change??");
            String change = dukeUi.readUserInput();
            return change;
        } else if (info.equals("changeValue")) {
            printDukeResponse("Change to ?");
            String changeValue = dukeUi.readUserInput();
            return changeValue;
        } else {
            throw new DukeException(DukeUi.class, "Please provide a proper parameter into getPatient function!");
        }
    }

    /**
     * Provide the necessary patient details from the user for short cut feature.
     *
     * @param info The type of task information that want to be retrieved.
     * @return Either the task name or task id depend on the parameter info.
     * @throws DukeException throw a dukeException with error message for debugging.
     * @author QIAN JIE
     * @version 1.3
     */

    public String getPatientInfo(String info) throws DukeException {
        if (info.equals("name")) {
            printDukeResponse("Patient Name ?");
            String patientName = dukeUi.readUserInput();
            return patientName;
        } else if (info.equals("id")) {
            printDukeResponse("Patient ID Number ?");
            String patientId = "#" + dukeUi.readUserInput();
            return patientId;
        } else if (info.equals("nric")) {
            printDukeResponse("NRIC?");
            String nric = dukeUi.readUserInput();
            return nric;
        } else if (info.equals("room")) {
            printDukeResponse("Room??");
            String room = dukeUi.readUserInput();
            return room;
        } else if (info.equals("remark")) {
            printDukeResponse("Remarks?");
            String remark = dukeUi.readUserInput();
            return remark;
        } else if (info.equals("change")) {
            printDukeResponse("what would you like to change?");
            String change = dukeUi.readUserInput();
            return change;
        } else if (info.equals("changeValue")) {
            printDukeResponse("Change to ?");
            String changeValue = dukeUi.readUserInput();
            return changeValue;
        } else {
            throw new DukeException(DukeUi.class, "Please provide a proper parameter into getPatient function!");
        }
    }

    public void showPieChartResponse() {
        printDukeResponse("Here is the Pie Chart");
    }

    public void showBarChartResponse() {
        printDukeResponse("Here is the Bar Chart");
    }

    //@@lmtaek

    /**
     * Prints out list of available command inputs + formats for the user.
     *
     * @param helpOptions The list of command options to be printed.
     */
    public void showHelpOptions(ArrayList<String> helpOptions) {
        String output = "These are the commands that the user can use, and their respective formats:\n\n";
        for (String command : helpOptions) {
            output += " - " + command + "\n\n";
        }
        printDukeResponse(output);
    }

    /**
     * Prints out lists for each day of upcoming week + the tasks designated for those days.
     *
     * @param upcomingTasks The list of tasks for the upcoming date.
     */
    public void showUpcomingTasks(UpcomingTasks upcomingTasks) {
        String output = "";
        output += upcomingTasks.getFormattedDate() + ":\n";
        ArrayList<String> taskDescriptions = upcomingTasks.getUpcomingTaskDescriptions();
        ArrayList<String> patientNames = upcomingTasks.getPatientNames();
        for (int i = 0; i < upcomingTasks.getUpcomingTasks().size(); i++) {
            output += "Unique ID: " + upcomingTasks.getUpcomingTasks().get(i).getUuid() + ".\nDescription: "
                + taskDescriptions.get(i) + "\nFor patient: " + patientNames.get(i) + "\n\n";
        }
        printDukeResponse(output);

    }
    //@@author

}
