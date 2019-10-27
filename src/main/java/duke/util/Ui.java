package duke.util;

import duke.exceptions.DukeException;
import duke.models.patients.Patient;
import duke.models.assignedtasks.AssignedTask;
import duke.models.tasks.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the necessary ui elements for user interaction.
 */
public class Ui {
    private Scanner scanner;
    private static Ui ui;
    private String dukeResponses = "";
    private String userInput;

    /**
     * static method to create instance of Singleton class.
     *
     * @return Ui
     */
    public static Ui getUi() {
        if (ui == null) {
            ui = new Ui();
        }
        return ui;
    }

    /**
     * Increment the response to duke response collections for print out in GUI.
     * Perform system.out.println for debugging in terminal.
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
     * Display the error message start with an emoji.
     *
     * @param errorMessage the message of error/exception
     */
    public void showError(String errorMessage) {
        printDukeResponse("\u2639" + errorMessage); // Emoji of sad face
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

    /**
     * Print out patient is being found.
     *
     * @param patient patient being found
     */
    public void patientsFoundById(Patient patient) {
        printDukeResponse("Got it. The patient is found.");
        showPatientInfo(patient);
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

    /**
     * It asks user to choose a patient to be deleted from a list of patients.
     *
     * @param numberOfPatients the number of patients contain in the list
     * @return the number being choosen by user. If return -1, it means user canceled the deletion
     */
    public int choosePatientToDelete(int numberOfPatients) {
        int chosenNumber = -1;
        while (true) {
            printDukeResponse("Enter the index number of the patient to delete, or enter number 0 to cancel: ");
            String command = readUserInput();
            try {
                chosenNumber = Integer.parseInt(command);
            } catch (Exception e) {
                printDukeResponse("Please enter a valid number!");
                continue;
            }
            if (chosenNumber >= 0 && chosenNumber <= numberOfPatients) {
                if (chosenNumber == 0) {
                    printDukeResponse("Delete command is canceled.");
                }
                return chosenNumber;
            } else {
                printDukeResponse("The patient #"
                    + chosenNumber
                    + " does not exist. Please enter a valid index number!");
            }
        }
    }

    //@@author kkeejjuunn

    /**
     * It asks user to choose a task to be deleted from a list of tasks.
     *
     * @param numberOfTasks the number of task contain in the list
     * @return the index being chosen by user. If return -1, it means user canceled the deletion
     */
    public int chooseTaskToDelete(int numberOfTasks) {
        int chosenNumber = -1;
        while (true) {
            printDukeResponse("Enter the index of task to delete, or enter number 0 to cancel: ");
            String command = readUserInput();
            try {
                chosenNumber = Integer.parseInt(command);
            } catch (Exception e) {
                printDukeResponse("Please enter a valid number!");
                continue;
            }
            if (chosenNumber >= 0 && chosenNumber <= numberOfTasks) {
                if (chosenNumber == 0) {
                    printDukeResponse("Delete command is canceled");
                }
                return chosenNumber;
            } else {
                printDukeResponse("The task #" + chosenNumber + " does not exist. Please enter a valid number!");
            }
        }
    }


    /**
     * It confirms with user on the deletion of a patient.
     * It reminds user that the tasks assigned to this user will be delete
     * If user confirms, key in 'Y'. Otherwise key in 'N'.
     *
     * @param patient           it contains patient's info
     * @param withTasksAssigned it indicates whether the patient is assigned to any tasks
     * @return true if user confirmed the deletion. False otherwise.
     */
    public boolean confirmPatientToBeDeleted(Patient patient, boolean withTasksAssigned) {
        while (true) {
            if (withTasksAssigned) {
                printDukeResponse("The patient with above tasks assigned is to be deleted. Are you sure (Y/N)?");
            } else {
                printDukeResponse("The patient is to be deleted. Are you sure (Y/N)? ");
            }
            String command = readUserInput();
            if (command.toLowerCase().equals("y")) {
                return true;
            } else if (command.toLowerCase().equals("n")) {
                printDukeResponse("Delete command is canceled");
                return false;
            } else {
                printDukeResponse("Please enter only Y/N to confirm/cancel deletion!");
            }
        }
    }

    /**
     * It shows message of a patient being deleted.
     */
    public void patientDeleted() {
        printDukeResponse("Got it. The patient is deleted.");
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

    //@@author kkeejjuunn

    /**
     * It confirms with user on the deletion of a task.
     * It alerts user that the deletion will cause the current patient who assigned
     * to this task will no longer assigned to this task.
     * If user confirms, key in 'Y'. Otherwise key in 'N'.
     *
     * @param task                 contains task's info
     * @param assignedToAnyPatient indicates whether the task is assigned to any patient
     * @return true if user confirmed the deletion. False otherwise.
     */
    public boolean confirmTaskToBeDeleted(Task task, boolean assignedToAnyPatient) {
        while (true) {
            if (assignedToAnyPatient) {
                printDukeResponse("The task is to be deleted. These patients will no "
                    + "longer assigned to this task. Are you sure (Y/N)?");
            } else {
                printDukeResponse("The task is to be deleted. Are you sure (Y/N)? ");
            }
            String command = readUserInput();
            if (command.toLowerCase().equals("y")) {
                return true;
            } else if (command.toLowerCase().equals("n")) {
                printDukeResponse("Delete command is canceled");
                return false;
            } else {
                printDukeResponse("Please enter only Y/N to confirm/cancel deletion!");
            }
        }
    }


    /**
     * It confirms with user on the deletion of a task.
     * If user confirms, key in 'Y'. Otherwise key in 'N'.
     *
     * @param correctedCommand the correctedCommand
     * @return true if user confirmed the deletion. False otherwise.
     */
    public boolean confirmTypoCorrection(String correctedCommand, String userInput) {
        printDukeResponse("Ambiguous format! Did you mean(Y/N): \n" + correctedCommand);
        while (true) {
            String command = readUserInput();
            if (command.toLowerCase().equals("y")) {
                return true;
            } else if (command.toLowerCase().equals("n")) {
                printDukeResponse("Proceed with original command: " + userInput);
                return false;
            } else {
                printDukeResponse("Please enter only Y/N to proceed with recommended command: " + correctedCommand);
            }
        }
    }

    /**
     * .
     *
     * @param correctedCommand the correctedCommand
     * @return
     */
    public void typoCorrection(String correctedCommand) {
        printDukeResponse("Ambiguous command keywords!\nProceed with command: " + correctedCommand);
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
     * Shows Duke logo and welcome message, and user input instructions.
     */
    public void showWelcome() {
        String logo = " _____        _              _ _        _ \n"
            +
            "|  __ \\      | |            (_) |      | |\n"
            +
            "| |  | |_   _| | _____ _ __  _| |_ __ _| |\n"
            +
            "| |  | | | | | |/ / _ \\ '_ \\| | __/ _` | |\n"
            +
            "| |__| | |_| |   <  __/ |_) | | || (_| | |\n"
            +
            "|_____/ \\__,_|_|\\_\\___| .__/|_|\\__\\__,_|_|\n"
            +
            "                      | |                 \n"
            +
            "                      |_|                 \n";

        printDukeResponse(logo);
        printDukeResponse("Hello! I'm Duke\nWhat can I do for you?\n\n");
        printDukeResponse("Enter 'help' to show a list of commands ");
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
     * It shows all info of tasks found which are associated with the patient given by user.
     *
     * @param patient     patient given by user
     * @param patientTask list of patienttasks being found associated with the patient
     * @param tasks       list of tasks relate to patienttasks found
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
     * @param task        task given by user
     * @param patientTask list of patienttasks being found associated with the task
     * @param patients    list of patients relate to task
     */
    public void taskPatientFound(Task task, ArrayList<AssignedTask> patientTask, ArrayList<Patient> patients) {
        printDukeResponse("The task " + task.getId() + " " + task.getDescription()
            + " assigned to following patient(s) is/are found : \n");
        for (int i = 0; i < patientTask.size(); i++) {
            showLine();
            printDukeResponse(patients.get(i).getId() + ". " + patients.get(i).getName() + "\n");
            printDukeResponse(patientTask.get(i).toString());
            showLine();
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
            String taskName = ui.readUserInput();
            return taskName;
        } else if (info.equals("id")) {
            printDukeResponse("Task ID?");
            String taskId = "#" + ui.readUserInput();
            return taskId;
        } else if (info.equals("change")) {
            printDukeResponse("What would you like to change??");
            String change = ui.readUserInput();
            return change;
        } else if (info.equals("changeValue")) {
            printDukeResponse("Change to ?");
            String changeValue = ui.readUserInput();
            return changeValue;
        } else {
            throw new DukeException("Please provide a proper parameter into getPatient function!");
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
            String patientName = ui.readUserInput();
            return patientName;
        } else if (info.equals("id")) {
            printDukeResponse("Patient ID Number ?");
            String patientId = "#" + ui.readUserInput();
            return patientId;
        } else if (info.equals("nric")) {
            printDukeResponse("NRIC?");
            String nric = ui.readUserInput();
            return nric;
        } else if (info.equals("room")) {
            printDukeResponse("Room??");
            String room = ui.readUserInput();
            return room;
        } else if (info.equals("remark")) {
            printDukeResponse("Remarks?");
            String remark = ui.readUserInput();
            return remark;
        } else if (info.equals("change")) {
            printDukeResponse("what would you like to change?");
            String change = ui.readUserInput();
            return change;
        } else if (info.equals("changeValue")) {
            printDukeResponse("Change to ?");
            String changeValue = ui.readUserInput();
            return changeValue;
        } else {
            throw new DukeException("Please provide a proper parameter into getPatient function!");
        }
    }
    //@@author

}
