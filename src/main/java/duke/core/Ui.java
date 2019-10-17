package duke.core;

import duke.patient.Patient;
import duke.relation.PatientTask;
import duke.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the necessary ui elements for user interaction.
 */
public class Ui {
    /**
     * A Scanner to read user input.
     */
    private Scanner scanner;

    /**
     * Constructs a singleton Ui design pattern by using lazy initialization.
     */

    private Ui() {
        scanner = new Scanner(System.in);
    }

    private static Ui ui;

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
     * Reads user instruction.
     *
     * @return A string that represents the user instruction.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Display the error message start with an emoji.
     *
     * @param errorMessage the message of error/exception
     */
    public void showError(String errorMessage) {
        System.out.println("\u2639" + errorMessage); // Emoji of sad face
    }

    /**
     * Print out message to indicate task is added.
     *
     * @param standardTask the standard task contains its description
     */
    public void taskAdded(Task standardTask) {
        System.out.println("Got it. I've added this task: \n"
                + standardTask.getDescription());
    }

    /**
     * Print out message to show Patient's info.
     *
     * @param patient it contains patient's information
     */
    public void showPatientInfo(Patient patient) {
        System.out.println("Name: "
                + patient.getName()
                + "  Id: "
                + patient.getID()
                + "\nNRIC: "
                + patient.getNRIC()
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
        System.out.println("Task: " + task.getDescription());
    }

    /**
     * Display all the patients with the similar name.
     *
     * @param patients a list contains patient with similar name
     * @param name     the name given by user for search
     */
    public void patientsFoundByName(ArrayList<Patient> patients, String name) {
        if (patients.size() > 0) {
            System.out.println("Got it. " + patients.size() + " patients is/are found with name: " + name);
            int i = 1;
            for (Patient patient : patients) {
                System.out.println("Patient #" + i);
                showPatientInfo(patient);
                showLine();
                i++;
            }
        } else {
            System.out.println("No patient was found with name: " + name);
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
            System.out.println("Got it. " + tasks.size() + " tasks is/are found with description: " + description);
            int i = 1;
            for (Task task : tasks) {
                System.out.println("Task #" + i);
                showTaskInfo(task);
                showLine();
                i++;
            }
        } else {
            System.out.println("No task was found with description: " + description);
        }
    }

    /**
     * Print out patient is being found.
     *
     * @param patient patient being found
     */
    public void patientsFoundById(Patient patient) {
        System.out.println("Got it. The patient is found.");
        showPatientInfo(patient);
    }

    /**
     * Print message of a patient is being added.
     *
     * @param patient it contains info of the patient being added
     */
    public void patientAdded(Patient patient) {
        System.out.println("Got it. The following patient has been added:  ");
        showPatientInfo(patient);
    }


    /**
     * Print message of a patient is being assigned to task.
     *
     * @param patientTask it contains the patient task relation and its info
     * @param patientName the name of patient being assigned
     * @param taskName    the name of task which is associated with the patient
     */
    public void patientTaskAssigned(PatientTask patientTask, String patientName, String taskName) {
        System.out.println("Got it. The following Patient ID: "
                + patientTask.getPatientId()
                + " "
                + patientName
                + " has been assigned the Task ID: "
                + patientTask.getTaskID()
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
            System.out.println("Enter the index number of the patient to delete, or enter number 0 to cancel: ");
            String command = readCommand();
            try {
                chosenNumber = Integer.parseInt(command);
            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
                continue;
            }
            if (chosenNumber >= 0 && chosenNumber <= numberOfPatients) {
                if (chosenNumber == 0) {
                    System.out.println("Delete command is canceled.");
                }
                return chosenNumber;
            } else {
                System.out.println("The patient #"
                        + chosenNumber
                        + " does not exist. Please enter a valid index number!");
            }
        }
    }

    /**
     * It asks user to choose a task to be deleted from a list of tasks.
     *
     * @param numberOfTasks the number of task contain in the list
     * @return the number being choosen by user. If return -1, it means user canceled the deletion
     */
    public int chooseTaskToDelete(int numberOfTasks) {
        int chosenNumber = -1;
        while (true) {
            System.out.println("Enter the number of task to delete, or enter number 0 to cancel: ");
            String command = readCommand();
            try {
                chosenNumber = Integer.parseInt(command);
            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
                continue;
            }
            if (chosenNumber >= 0 && chosenNumber <= numberOfTasks) {
                if (chosenNumber == 0) {
                    System.out.println("Delete command is canceled");
                }
                return chosenNumber;
            } else {
                System.out.println("The task #" + chosenNumber + " does not exist. Please enter a valid number!");
            }
        }

    }

    /**
     * It confirms with user on the deletion of a patient.
     * If user confirms, key in 'Y'. Otherwise key in 'N'.
     *
     * @param patient it contains patient's info
     * @return true if user confirmed the deletion. False otherwise.
     */
    public boolean confirmPatientToBeDeleted(Patient patient) {
        showPatientInfo(patient);
        while (true) {
            System.out.println("The patient is to be deleted. Are you sure (Y/N)? ");
            String command = readCommand();
            if (command.toLowerCase().equals("y")) {
                return true;
            } else if (command.toLowerCase().equals("n")) {
                System.out.println("Delete command is canceled");
                return false;
            } else {
                System.out.println("Please enter only Y/N to confirm/cancel deletion!");
            }
        }
    }

    /**
     * It shows message of a patient being deleted.
     */
    public void patientDeleted() {
        System.out.println("Got it. The patient is deleted.");
    }

    /**
     * It shows message of a task being deleted.
     */
    public void taskDeleted() {
        System.out.println("Got it. The task is deleted.");
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
        System.out.println("Here's a list of your tasks: \n");
        for (Task task : taskList) {
            System.out.println(index
                    + ". "
                    + task.getDescription()
                    + " (ID: "
                    + task.getID()
                    + ")"
                    + "\n");
            index++;
        }
    }

    /**
     * It confirms with user on the deletion of a task.
     * If user confirms, key in 'Y'. Otherwise key in 'N'.
     *
     * @param task it contains task's info
     * @return true if user confirmed the deletion. False otherwise.
     */
    public boolean confirmTaskToBeDeleted(Task task) {
        showTaskInfo(task);
        while (true) {
            System.out.println("The task is to be deleted. Are you sure (Y/N)? ");
            String command = readCommand();
            if (command.toLowerCase().equals("y")) {
                return true;
            } else if (command.toLowerCase().equals("n")) {
                System.out.println("Delete command is canceled");
                return false;
            } else {
                System.out.println("Please enter only Y/N to confirm/cancel deletion!");
            }
        }
    }

    /**
     * Shows a divider line.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }


    /**
     * Shows bye message to user.
     */
    public void exitInformation() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Shows Duke logo and welcome message, and user input instructions.
     */
    public void showWelcome() {
        String logo = " _____        _              _ _        _ \n" +
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

        System.out.println(logo);
        System.out.println("Hello! I'm Duke\nWhat can I do for you?\n\n");
        System.out.println("Enter 'help' to show a list of commands ");
    }

    /**
     * Show information is being updated successfully.
     */
    public void showUpdatedSuccessfully() {
        System.out.println("I have successfully updated the following information: \n");
    }

    /**
     * Show message of loading failure.
     */
    public void showLoadingError() {
        System.out.println("Failed to load from local data file!");
    }

    /**
     * It shows all info of patientTasks found which are associated with the patient given by user.
     *
     * @param patient     patient given by user
     * @param patientTask list of patienttasks being found associated with the patient
     * @param tasks       list of tasks relate to patienttasks found
     */
    public void patientTaskFound(Patient patient, ArrayList<PatientTask> patientTask, ArrayList<Task> tasks) {
        System.out.println("The tasks of patient " + patient.getID() + " " + patient.getName() + " is found : \n");
        for (int i = 0; i < patientTask.size(); i++) {
            showLine();
            System.out.println(tasks.get(i).getID() + ". " + tasks.get(i).getDescription() + "\n");
            System.out.println(patientTask.get(i).toString());
            showLine();
        }
    }
}
