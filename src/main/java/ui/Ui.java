package ui;

import common.CommandFormat;
import common.TaskList;
import payment.Payee;
import payment.Payments;
import project.Fund;
import project.Project;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Ui to interact with the user.
 */
public class Ui {
    public static final String line = "    ____________________________________________________________\n";
    private static CommandFormat commandFormat = new CommandFormat();
    Scanner in;

    /**
     * Creates a Ui instance with a scanner to read user input.
     */
    public Ui() {
        this.in = new Scanner(System.in);
    }

    /**
     * Reads input from the user.
     * @return Input from the user.
     */
    public String readInput() {
        return in.nextLine();
    }

    /**
     * Prints starting message for the AlphaNUS program.
     */
    public void startMessage() {
        System.out.print(line);
        System.out.println("\t" + "Hello! I'm AlphaNUS");
        System.out.println("");
        System.out.println("\t" + "Enter \"help\" to see things that I can do!");
        System.out.print(line);
    }

    /**
     * Prints ending message for the AlphaNUS program.
     */
    public void byeMessage() {
        System.out.print(line);
        System.out.println("\t" + "Bye. Hope to see you again soon!");
        System.out.print(line);
    }

    /**
     * Prints a message to indicate that there are no projects in the record.
     */
    public void printNoProjectMessage() {
        System.out.print(line);
        System.out.println("\t" + "There are no projects in the record.");
        System.out.println("\t" + "Please add a new project.");
        System.out.println("\t" + "Format: add project pr/PROJECTNAME");
        System.out.print(line);
    }

    /**
     * Prints a message to list all projects in the record.
     * @param projectslist ArrayList containing all projects in record.
     */
    public void printProjectsList(ArrayList<Project> projectslist) {
        int index = 1;
        System.out.print(line);
        System.out.println("\t" + "Here is the list of projects:");
        for (Project project: projectslist) {
            System.out.println("\t" + index + ". " + project.projectname);
            index++;
        }
        System.out.println("\t" + "There are " + projectslist.size() + " projects in the record.");
        System.out.print(line);
    }

    /**
     * Prints a message to indicate that a project has been added.
     * @param newProject The project that was added.
     * @param projectsize The current number of projects in the projectmap.
     */
    public void printAddProject(Project newProject, int projectsize) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've added this project:");
        System.out.println("\t" + "Name: " + newProject.projectname);
        System.out.println("\t" + "Budget: " + newProject.budget);
        System.out.println("\t" + "Spending: " + newProject.spending);
        System.out.println("\t" + "There are " + projectsize + " projects in the record.");
        System.out.print(line);
    }

    /**
     * Prints a message to indicate that a project has been deleted.
     * @param deletedProject The project that was deleted.
     * @param projectsize The current number of projects in the projectmap.
     */
    public void printDeleteProject(Project deletedProject, int projectsize) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've deleted this project:");
        System.out.println("\t" + "Name: " + deletedProject.projectname);
        System.out.println("\t" + "Budget: " + deletedProject.budget);
        System.out.println("\t" + "Spending: " + deletedProject.spending);
        System.out.println("\t" + "There are " + projectsize + " projects in the record.");
        System.out.print(line);
    }

    /**
     * Prints the number of projects in the projectmap and the current project
     * that is being worked on.
     * @param currentprojectname Name of the current project.
     * @param projectsize The current number of projects in the projectmap.
     */
    public void printProjectStatus(String currentprojectname, int projectsize) {
        System.out.print(line);
        if (currentprojectname == null) {
            System.out.println("\t" + "There are no projects in the record.");
        } else {
            System.out.println("\t" + "Current Project: " + currentprojectname);
            System.out.println("\t" + "There are " + projectsize + " projects in the record.");
        }
        System.out.print(line);
    }

    /**
     * Prints message to indicate the project that is gone to.
     * @param projectname Name of project that is gone to.
     */
    public void printGoToProject(String projectname) {
        System.out.print(line);
        System.out.println("\t" + "Going to Project: " + projectname);
        System.out.print(line);
    }

    /**
     * Prints the list of tasks or the matching list of tasks depending on the command.
     * @param list TaskList of the user.
     * @param command Command given by the user.
     */
    public void printList(TaskList list, String command) {
        int listsize = list.size();

        // prints list or matching tasks in list
        if (command.equals("list")) {
            System.out.print(line + "     Here are the tasks in your list:\n");
        } else {
            if (command.equals("find")) {
                System.out.print(line + "     Here are the matching tasks in your list:\n");
            }
        }
        for (int i = 0; i < listsize; i++) {
            int listnum = i + 1;
            System.out.print("     " + listnum + "." + list.get(i).giveTask() + "\n");
        }
        System.out.print(line);
    }

    /**
     * prints the list of input commands entered by the user.
     * @param list1 list of input commands entered by the user.
     */
    public void printHistoryList(ArrayList<String> list1) {
        System.out.println("Here is the history of your input commands:");
        for (int i = 0; i < list1.size(); i = i + 1) {
            System.out.println(list1.get(i));
        }
    }

    /**
     * prints the list of input commands entered by the user within the given period.
     * @param list1 list of input commands entered by the user within the given period.
     */
    public void printviewHistoryList(ArrayList<String> list1, String date1, String date2) {
        System.out.println("Here is the history of your input commands from " + date1 + " to " + date2 + ":");
        for (int i = 0; i < list1.size(); i = i + 1) {
            System.out.println(list1.get(i));
        }
    }
    /**
     * Prints dates from which history was cleared.
     * @param date1 from date to delete history.
     * @param date2 to date to delete history.
     */
    public void printdeletehistory(String date1, String date2) {
        System.out.println("Got it. The input commands from " + date1 + " to " 
            + date2 + " have been deleted from the history");
    }

    /**
     * Prints the list of payments of a payee.
     * @param paymentList paymentList of the payee.
     */
    public void printPaymentList(ArrayList<Payments> paymentList) {
        for (Payments payments : paymentList) {
            System.out.println(payments.givePayments());
        }
    }

    /**
     * Prints message to indicate deletion of a Payment from the Payee and the number of Payments left.
     * @param payment Representation of the Payment that is deleted.
     * @param payee the name of the payee to whom Payment was being made to
     * @param size the number of payments in the record for this Payee after deletion
     */
    public void printDeletePaymentMessage(String payee, Payments payment, int size, String currentProjectName) {
        System.out.print(line + "     Noted. I've removed this payment: \n");
        System.out.println("\t" + "Payee: " + payee);
        System.out.println("\t" + "Item: " + payment.item);
        System.out.println("\t" + "Cost: " + payment.cost);
        System.out.println("\t" + "Invoice: " + payment.inv);
        System.out.print("\t" + payee + " now has " + size + " payments in project " + currentProjectName + ".\n");
        System.out.print(line);
    }

    /**
     * Prints message to indicate a Task is done.
     * @param numdone Index of the Task in the TaskList.
     * @param tasklist TaskList of the user.
     */
    public void printDoneMessage(int numdone, TaskList tasklist) {
        System.out.print(line + "     Nice! I've marked this task as done: \n");
        System.out.print("       " + tasklist.get(numdone).giveTask() + "\n");
        System.out.print(line);
    }

    /**
     * Prints message to indicate a task being added.
     * @param task Task.
     * @param tasklist Tasklist.
     */
    public void printAddedMessage(Task task, TaskList tasklist) {
        System.out.print(line + "     Got it. I've added this task:  \n");
        System.out.print("       " + task.giveTask() + "\n");
        int tasksize = tasklist.size();
        System.out.print("     Now you have " + tasksize + " tasks in the list." + "\n");
        System.out.print(line);
    }

    /**
     * Prints message to indicate a Task being snoozed.
     * @param task Task to be snoozed.
     */
    public void printSnoozeMessage(Task task) {
        System.out.print(line + "     Got it. I've snoozed this task:  \n");
        System.out.print("       " + task.giveTask() + "\n");
        System.out.print(line);
    }

    /**
     * Prints message to indicate a Task being postponed.
     * @param task Task to be postponed.
     */
    public void printPostponeMessage(Task task) {
        System.out.print(line + "     Got it. I've postponed this task:  \n");
        System.out.print("       " + task.giveTask() + "\n");
        System.out.print(line);
    }

    /**
     * Prints message to indicate a Task being rescheduled.
     * @param task Task to be snoozed.
     */
    public void printRescheduleMessage(Task task) {
        System.out.print(line + "     Got it. I've rescheduled this task:  \n");
        System.out.print("       " + task.giveTask() + "\n");
        System.out.print(line);
    }

    /**
     * Prints message to indicate a payee has been edited.
     * @param payee Payee that had been edited.
     */
    public void printEditMessage(Payee payee) {
        System.out.print(line + "     Got it. I've edited this payee:  \n");
        System.out.println("\t" + "Payee: " + payee.payee);
        System.out.println("\t" + "Email: " + payee.email);
        System.out.println("\t" + "Matric No: " + payee.matricNum);
        System.out.println("\t" + "Phone No: " + payee.phoneNum);
        System.out.print(line);
    }
    
    /**
     * Prints message to indicate a payment has been edited.
     * @param payment Payment that had been edited
     */
    public void printEditMessage(Payments payment, String name) {
        System.out.print(line + "     Got it. I've edited this payment:  \n");
        System.out.println("\t" + "Payee: " + name);
        System.out.println("\t" + "Item: " + payment.item);
        System.out.println("\t" + "Cost: " + payment.cost);
        System.out.println("\t" + "Invoice: " + payment.inv);
        System.out.println("\t" + "Deadline: " + payment.deadline);
        System.out.println("\t" + "Status: " + payment.status);
        System.out.print(line);
    }

    /**
     * Prints the message for the exception thrown.
     * @param message Exception message.
     */
    public void exceptionMessage(String message) {
        System.out.print(line);
        System.out.println(message);
        System.out.print(line);
    }

    /**
     * Prints message to indicate a Payment being added to a certain Payee.
     * @param payee name of entity Payment is directed to
     * @param payment the new Payment containing the relevant information added to Payee object.
     */
    public void printAddPaymentMessage(String payee, Payments payment, int size, String currentProjectName) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've added this payment:");
        System.out.println("\t" + "Payee: " + payee);
        System.out.println("\t" + "Item: " + payment.item);
        System.out.println("\t" + "Cost: " + payment.cost);
        System.out.println("\t" + "Invoice: " + payment.inv);
        System.out.println("\t" + "Deadline: " + payment.deadline);
        System.out.println("\t" + "Status: " + payment.status);
        System.out.print("\t" + payee + " now has " + size + " payments in project " + currentProjectName + ".\n");
        System.out.print(line);
    }

    /**
     * Prints message to indicate a Payment being added to a certain Payee.
     * @param payee Payee containing identification information of Payee.
     * @param name the name of Payee to make Payments to.
     */
    public void printAddPayeeMessage(String name, Payee payee, int payeesize, String currentProjectName) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've added this payee:");
        System.out.println("\t" + "Payee: " + name);
        System.out.println("\t" + "Email: " + payee.email);
        System.out.println("\t" + "Matric No: " + payee.matricNum);
        System.out.println("\t" + "Phone No: " + payee.phoneNum);
        System.out.print("\t" + "There are " + payeesize + " payees in project " + currentProjectName + ".\n");
        System.out.print(line);
    }

    /**
     * Prints message to indicate a deadline with an invoice added.
     * @param task Task to be edited.
     */
    public void printAddInvoiceMessage(Task task) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've added invoice to this deadline:");
        System.out.print("\t" + task.giveTask() + "\n");
    }

    /**
     * Prints message to indicate a Payment being deleted.
     * @param payee Payee containing identification information of Payee.
     * @param name the name of Payee to make Payments to.
     */
    public void printdeletePayeeMessage(String name, Payee payee, int payeesize, String currentProjectName) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've deleted this payee:");
        System.out.println("\t" + "Payee: " + name);
        System.out.println("\t" + "Email: " + payee.email);
        System.out.println("\t" + "Matric No: " + payee.matricNum);
        System.out.println("\t" + "Phone No: " + payee.phoneNum);
        System.out.print("\t" + "There are " + payeesize + " payees in project " + currentProjectName + ".\n");
        System.out.print(line);
    }

    /**
     * Prints message of adding a fund to all projects.
     * @param fund the fund for all projects.
     */
    public void printSetFundMessage(Fund fund) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've set overall fund as follow:");
        System.out.print(fund.giveFund());
        System.out.print(line);
    }

    /**
     * Prints message of adding a fund to all projects.
     * @param fund the fund for all projects.
     */
    public void printAddFundMessage(Fund fund, double amount) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've add " + amount + "to the fund. The new fund is as follow:");
        System.out.print(fund.giveFund());
        System.out.print(line);
    }

    /**
     * Prints message of adding a fund to all projects.
     * @param fund the fund for all projects.
     * @param amount amount to be added.
     * @param project project to be added to.
     */
    public void printAssignFundMessage(Fund fund, double amount, Project project) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've assigned " + amount + " to the project:");
        System.out.print(project.giveProject());
        System.out.println("");
        System.out.println("");
        System.out.println("\t" + "The new fund is as follows:");
        System.out.print(fund.giveFund());
        System.out.print(line);
    }
    /**
     * Prints out the statement of accounts.
     * @param managermap managermap containing Payee and Payments information.
     */

    public void generateStatementofAccounts(HashMap<String, Payee> managermap) {
        System.out.print("Item\tExpense\n");
        for (Payee payee : managermap.values()) {
            for (Payments payment : payee.payments) {
                System.out.println(payment.item + "\t" + payment.cost);
            }
        }
    }
    /**
     * Prints message to indicate that the previous command has been undone.
     */
    public void undoMessage(){
        System.out.println("Got it! I have undone the previous command.");
    }
    /**
     * Prints message to indicate that the previous command has been redone.
     */
    public void redoMessage(){
        System.out.println("Got it! I have redone the previous command.");
    }
    /**
     * Prints out a help message with command formats.
     */
    public void printHelpMessage() {
        System.out.print(line);
        System.out.println("\t" + "*Help*");
        System.out.println("");
        System.out.println("\t" + "Add Project:          " + commandFormat.addProjectFormat());
        System.out.println("\t" + "Delete Project:       " + commandFormat.deleteProjectFormat());
        System.out.println("\t" + "List Projects:        " + commandFormat.listProjectFormat());
        System.out.println("\t" + "Go to a Project:      " + commandFormat.gotoProjectFormat());
        System.out.println("\t" + "Set Fund:             " + commandFormat.setFundFormat());
        System.out.println("\t" + "Add Fund:             " + commandFormat.addFundFormat());
        System.out.println("\t" + "Assign Fund:          " + commandFormat.assignFundFormat());
        System.out.println("\t" + "Add Payee:            " + commandFormat.addPayeeFormat());
        System.out.println("\t" + "Add Payment:          " + commandFormat.addPaymentFormat());
        System.out.println("\t" + "Delete Payee:         " + commandFormat.deletePayeeFormat());
        System.out.println("\t" + "Edit Payment/Payee:   " + commandFormat.editPaymentFormat());
        System.out.println("\t" + "History of Commands:  " + commandFormat.historyFormat());
        System.out.println("\t" + "View History within a certain period:         " + commandFormat.viewhistoryFormat());
        System.out.println("\t" + "Exit:                 " + commandFormat.exitFormat());
        System.out.print(line);
    }
    /**
     * Returns the scanned input.
     * @return Scanned input.
     */
    public Scanner getIn() {
        return this.in;
    }
}
