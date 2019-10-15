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
     * weifeng
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

    public void showError(String e) {
        System.out.println("☹" + e);
    }

    public void taskAdded(Task standardTask) {
        System.out.println("Got it. I've added this task: \n" + standardTask.getDescription());
    }

    public void showPatientInfo(Patient patient) {
        System.out.println("Name: " + patient.getName() + "  Id: " + patient.getID()
                + "\nNRIC: " + patient.getNRIC() + "  Room: " + patient.getRoom()
                + "\nRemark: " + patient.getRemark());
    }

    public void showTaskInfo(Task task) {
        System.out.println("Task: " + task.getDescription());
    }

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

    public void patientsFoundById(Patient patient) {
        System.out.println("Got it. The patient is found.");
        showPatientInfo(patient);
    }


    public void patientAdded(Patient patient) {
        System.out.println("Got it. The following patient has been added:  ");
        showPatientInfo(patient);
    }

    public void patientTaskAssigned(PatientTask patientTask, String patientName, String taskName) {
        System.out.println("Got it. The following Patient ID: " + patientTask.getPatientId() + " " + patientName + " has been assigned the Task ID: " + patientTask.getTaskID() + " " + taskName);
    }

    public int choosePatientToDelete(int numberOfPatients) {
        int chosenNumber = -1;
        while (true) {
            System.out.println("Enter the number of patient to delete, or enter number 0 to cancel: ");
            String command = readCommand();
            try {
                chosenNumber = Integer.parseInt(command);
            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
                continue;
            }
            if (chosenNumber >= 0 && chosenNumber <= numberOfPatients) {
                if (chosenNumber == 0) {
                    System.out.println("Delete command is canceled");
                }
                return chosenNumber;
            } else {
                System.out.println("The patient #" + chosenNumber + " does not exist. Please enter a valid number!");
            }
        }

    }

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

    public void patientDeleted() {
        System.out.println("Got it. The patient is deleted.");
    }

    public void taskDeleted() {
        System.out.println("Got it. The task is deleted.");
    }

    public void listAllPatients(ArrayList<Patient> patients) {
        for (Patient patient : patients) {
            showPatientInfo(patient);
            showLine();
        }
    }

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
                "|  __ \\      | |            (_) |      | |\n" +
                "| |  | |_   _| | _____ _ __  _| |_ __ _| |\n" +
                "| |  | | | | | |/ / _ \\ '_ \\| | __/ _` | |\n" +
                "| |__| | |_| |   <  __/ |_) | | || (_| | |\n" +
                "|_____/ \\__,_|_|\\_\\___| .__/|_|\\__\\__,_|_|\n" +
                "                      | |                 \n" +
                "                      |_|                 \n";

        System.out.println(logo);
        System.out.println("Hello! I'm Duke\nWhat can I do for you?\n\n");
        System.out.println("Enter 'help' to show a list of commands ");
    }


    public void showUpdateStatus(Patient patient , String targetInfo) {
        System.out.println("I have successfully updated the " + targetInfo + " of " + patient.getName() + " ID:" + patient.getID()  );
    }

    /**
     * Shows an error in loading the file where past tasks are stored.
     */
    public void showLoadingError() {
        System.out.println("Failed to Load from local text file!");
    }

    public void patientTaskFound(Patient patient, ArrayList<PatientTask> patientTask, ArrayList<Task> tasks) {
        System.out.println("The tasks of patient " + patient.getID() + " " + patient.getName() + " is found : \n");
        for (int i = 0; i < patientTask.size(); i++){
            showLine();
            System.out.println( tasks.get(i).getID() + ". " + tasks.get(i).getDescription() +"\n");
            System.out.println( patientTask.get(i).toString());
            showLine();
        }
    }

}
