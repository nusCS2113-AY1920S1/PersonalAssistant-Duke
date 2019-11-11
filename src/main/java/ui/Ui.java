package ui;

import common.AlphaNUSException;
import common.CommandFormat;
import task.TaskList;
import payment.Payee;
import payment.Payments;
import payment.Status;
import project.Fund;
import project.Project;
import project.ProjectManager;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/**
 * Ui to interact with the user.
 */
public class Ui {
    public static final String line = "    ____________________________________________________________\n";
    private static CommandFormat commandFormat = new CommandFormat();
    private Scanner in;

    /**
     * Creates a Ui instance with a scanner to read user input.
     */
    public Ui() {
        this.in = new Scanner(System.in);
    }


    /**
     * Reads input from the user.
     * 
     * @return Input from the user.
     */
    public String readInput() {
        return in.nextLine();
    }

    /**
     * Prints starting message for the AlphaNUS program.
     */
    public void startMessage() {
        System.out.println("\t" + "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("\t" + "$            Hello! I'm AlphaNUS             $");
        System.out.println("\t" + "$                                            $");
        System.out.println("\t" + "$ Enter \"help\" to see things that I can do!  $");
        System.out.println("\t" + "$                                            $");
        System.out.println("\t" + "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
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
        System.out.println("\t" + "Format: " + commandFormat.addProjectFormat());
        System.out.print(line);
    }

    /**
     * Prints a message to list all projects in the record.
     * 
     * @param projectslist ArrayList containing all projects in record.
     */
    public void printProjectsList(ArrayList<Project> projectslist, String currentprojectname) {
        int index = 1;
        System.out.print(line);
        System.out.println("\t" + "Here is the list of projects:");
        for (Project project : projectslist) {
            System.out.println("\t" + index + ". " + project.projectname);
            System.out.println("\t\t" + "Budget: " + project.budget);
            System.out.println("\t\t" + "Spending: " + project.spending);
            System.out.println("");
            index++;
        }
        System.out.println("\t" + "There are " + projectslist.size() + " projects in the record.");
        if (currentprojectname == null) {
            System.out.println("\t" + "Please go to a project to manage its payments and payees.");
            System.out.println("\t" + "Command Format: " + commandFormat.gotoProjectFormat());
        } else {
            System.out.println("\t" + "Current Project: " + currentprojectname);
        }
        System.out.print(line);
    }

    /**
     * Prints a message to indicate that a project has been added.
     * 
     * @param newProject  The project that was added.
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
     * 
     * @param deletedProject The project that was deleted.
     * @param projectsize    The current number of projects in the projectmap.
     */
    public void printDeleteProject(Project deletedProject, int projectsize, Fund fund) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've deleted this project:");
        System.out.println("\t" + "Name: " + deletedProject.projectname);
        System.out.println("\t" + "Budget: " + deletedProject.budget);
        System.out.println("\t" + "Spending: " + deletedProject.spending);
        System.out.println("\t" + "There are " + projectsize + " projects in the record.");
        System.out.println("");
        System.out.println("\t" + "The updated fund is as follows:");
        System.out.print(fund.giveFund());
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
     * 
     * @param projectname Name of project that is gone to.
     */
    public void printGoToProject(String projectname) {
        System.out.print(line);
        System.out.println("\t" + "Going to Project: " + projectname);
        System.out.println("\t" + "You can now manage payments and payees in " + projectname);
        System.out.print(line);
    }

    /**
     * Prints the list of tasks or the matching list of tasks depending on the
     * command.
     * 
     * @param list    TaskList of the user.
     * @param command Command given by the user.
     */
    public void printList(TaskList list, String command) {
        int listsize = list.size();

        // prints list or matching tasks in list
        if (command.equals("list")) {
            System.out.print(line + "     Here are the tasks in your list:\n");
        } else if (command.equals("find")) {
            System.out.print(line + "     Here are the matching tasks in your list:\n");
        } else if (command.equals("View Schedule")) {
            System.out.print(line + "     Here are the tasks on this day:\n");
        }
        for (int i = 0; i < listsize; i++) {
            int listnum = i + 1;
            System.out.print("     " + listnum + "." + list.get(i).giveTask() + "\n");
        }
        System.out.print(line);
    }

    /**
     * prints the list of input commands entered by the user.
     * 
     * @param list1 list of input commands entered by the user.
     */
    public void printHistoryList(ArrayList<String> list1) {
        System.out.println("Here is the history of your input commands:");
        for (int i = 0; i < list1.size(); i = i + 1) {
            System.out.println(list1.get(i));
        }
    }

    /**
     * prints the list of input commands entered by the user within the given
     * period.
     * 
     * @param list1 list of input commands entered by the user within the given
     *              period.
     */
    public void printviewHistoryList(ArrayList<String> list1, String date1, String date2) {
        System.out.println("Here is the history of your input commands from " + date1 + " to " + date2 + ":");
        for (int i = 0; i < list1.size(); i = i + 1) {
            System.out.println(list1.get(i));
        }
    }

    /**
     * Prints dates from which history was cleared.
     * 
     * @param date1 from date to delete history.
     * @param date2 to date to delete history.
     */
    public void printdeletehistory(String date1, String date2) {
        System.out.println(
                "Got it. The input commands from " + date1 + " to " + date2 + " have been deleted from the history");
    }

    //@@author karansarat
    /**
     * Prints a message with the details of a payee that the user requested for.
     * @param payee the payee object the user queried for.
     */
    public void printFoundMessage(Payee payee) {
        System.out.println("\tHere are the details you requested!");
        payee.printPayee();
    }

    /**
     * Prints a message with the details of a payment that the user requested for.
     * @param payment the payment object the user queried for.
     */
    public void printFoundMessage(Payments payment) {
        System.out.println("\tHere are the details you requested!");
        payment.printPayment();
    }

    /**
     * Prints the list of payments of a payee.
     * @param name Name of project
     * @param paymentList paymentList of the payee.
     */
    public void printPaymentList(String name, ArrayList<Payments> paymentList) {
        if (paymentList.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        System.out.print(line);
        System.out.println("Here are your payments in" + name + ":");
        int i = 0;
        for (Payments payments : paymentList) {
            System.out.println();
            System.out.println("Payment " + ++i + ":");
            payments.printPayment();
        }
        System.out.print(line);
    }

    /**
     * Prints the list of payments of a payee.
     * 
     * @param paymentList paymentList of the payee.
     */
    public void printPaymentList(String name, ArrayList<Payments> paymentList, Status status) {
        System.out.println(line);
        System.out.println("In Project " + name + ";");
        if (status == Status.PENDING) {
            System.out.println("\tHere are your pending payments:");
        } else if (status == Status.OVERDUE) {
            System.out.println("\tHere are your overdue payments:");
        } else if (status == Status.APPROVED) {
            System.out.println("\tHere are your approved payments:");
        }
        int i = 0;
        for (Payments payments : paymentList) {
            System.out.println();
            System.out.println("\tPayment " + ++i + ":");
            payments.printPayment();
        }
        System.out.print(line);
    }

    /**
     * Prints the list of payees of a project.
     * @param managerMap a HashMap of the payees in a project.
     */
    public void printPayeeList(HashMap<String, Payee> managerMap) {
        System.out.println("Here are your payees in Project " + managerMap.get(0).project + ":");
        int i = 0;   
        for (Payee payee : managerMap.values()) {
            System.out.println("Payee " + ++i + ":");
            payee.printPayee();
        }
    }

    /**
     * Prints message to indicate deletion of a Payment from the Payee and the
     * number of Payments left.
     * 
     * @param payment Representation of the Payment that is deleted.
     * @param size    the number of payments in the record for this Payee after
     *                deletion
     */
    public void printDeletePaymentMessage(Payments payment, int size) {
        System.out.print(line + "     Noted. I've removed this payment: \n");
        payment.printPayment();
        System.out.print("\t" + payment.payee + " now has " + size 
            + " payments in project " + payment.project + ".\n");
        System.out.print(line);
    }

    /**
     * Prints message to indicate a Task is done.
     * 
     * @param numdone  Index of the Task in the TaskList.
     * @param tasklist TaskList of the user.
     */
    public void printDoneMessage(int numdone, TaskList tasklist) {
        System.out.print(line + "     Nice! I've marked this task as done: \n");
        System.out.print("       " + tasklist.get(numdone).giveTask() + "\n");
        System.out.print(line);
    }

    /**
     * Prints message to indicate a Task is done.
     * @param id Index of the Task in the TaskList.
     * @param tasklist TaskList of the user.
     */
    public void printDeleteTaskMessage(int id, TaskList tasklist) {
        System.out.print(line + "     Got it! I've removed this task from the list: \n");
        System.out.print("       " + tasklist.get(id).giveTask() + "\n");
        System.out.print(line);
    }

    //@@author lijiayu980606
    /**
     * Prints message to indicate a task being added.
     * 
     * @param task     Task.
     * @param tasklist Tasklist.
     */
    public void printAddedMessage(Task task, TaskList tasklist) {
        System.out.print(line + "     Got it. I've added this task:  \n");
        System.out.print("       " + task.giveTask() + "\n");
        int tasksize = tasklist.size();
        System.out.print("     Now you have " + tasksize + " tasks in the list." + "\n");
        System.out.print(line);
    }

    //@@author lijiayu980606
    /**
     * Prints message to indicate a Task being snoozed.
     * 
     * @param task Task to be snoozed.
     */
    public void printSnoozeMessage(Task task) {
        System.out.print(line + "     Got it. I've snoozed this task by 1 day:  \n");
        System.out.print("       " + task.giveTask() + "\n");
        System.out.print(line);
    }

    /**
     * Prints message to indicate a Task being postponed.
     * 
     * @param task Task to be postponed.
     */
    public void printPostponeMessage(Task task) {
        System.out.print(line + "     Got it. I've postponed this task:  \n");
        System.out.print("       " + task.giveTask() + "\n");
        System.out.print(line);
    }

    /**
     * Prints message to indicate a Task being rescheduled.
     * 
     * @param task Task to be snoozed.
     */
    public void printRescheduleMessage(Task task) {
        System.out.print(line + "     Got it. I've rescheduled this task:  \n");
        System.out.print("       " + task.giveTask() + "\n");
        System.out.print(line);
    }

    //@@author
    /**
     * Prints message to indicate a payee has been edited.
     * 
     * @param payee Payee that had been edited.
     */
    public void printEditMessage(Payee payee) {
        System.out.print(line + "     Got it. I've edited this payee:  \n");
        payee.printPayee();
        System.out.print(line);
    }

    /**
     * Prints message to indicate a payment has been edited.
     * 
     * @param payment Payment that had been edited
     */
    public void printEditMessage(Payments payment, String name) {
        System.out.print(line + "     Got it. I've edited this payment:  \n");
        payment.printPayment();
        System.out.print(line);
    }

    /**
     * Prints the message for the exception thrown.
     * 
     * @param message Exception message.
     */
    public void exceptionMessage(String message) {
        System.out.print(line);
        System.out.println(message);
        System.out.print(line);
    }

    //@@author lijiayu980606
    /**
     * Prints the message for the goto exception thrown.
     * Also prints projects list for user reference.
     * @param message Exception message.
     */
    public void gotoExceptionMessage(String message, ArrayList<Project> projectslist) {
        int index = 1;
        System.out.print(line);
        System.out.println(message);
        System.out.println("\t" + "Here is the list of projects:");
        for (Project project : projectslist) {
            System.out.println("\t" + index + ". " + project.projectname);
            System.out.println("\t\t" + "Budget: " + project.budget);
            System.out.println("\t\t" + "Spending: " + project.spending);
            System.out.println("");
            index++;
        }
        System.out.println("\t" + "There are " + projectslist.size() + " projects in the record.");
        System.out.print(line);
    }

    /**
     * TODO.
     */
    public void printInsufficientBudget(ProjectManager projectManager) {
        String currProjectName = projectManager.currentprojectname;
        exceptionMessage("     :( OOPS!!! There is not enough budget left.\n"
                        + " Total budget: " + projectManager.projectmap.get(currProjectName).getBudget() + "\n"
                        + " Budget spent: " + projectManager.projectmap.get(currProjectName).getSpending() + "\n"
                        + " Budget remaining: " + projectManager.projectmap.get(currProjectName).getRemaining() + "\n");
    }

    //@@author
    /**
     * Prints message to indicate a Payment being added to a certain Payee.
     * 
     * @param payment the new Payment containing the relevant information added to
     *                Payee object.
     */
    public void printAddPaymentMessage(Payments payment, int size, String currentprojectname) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've added this payment:");
        payment.printPayment();
        System.out.print("\t" + payment.payee + " now has " + size 
            + " payments in project " + currentprojectname + ".\n");
        System.out.print(line);
    }

    /**
     * Prints message to indicate a Payment being added to a certain Payee.
     * 
     * @param payee Payee containing identification information of Payee.
     * @param payeesize the number of payees in project
     */
    public void printAddPayeeMessage(Payee payee, int payeesize) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've added this payee:");
        payee.printPayee();
        System.out.println("\tProject " + payee.project + " now has " + payeesize + " payee(s).");
    }

    //@@author lijiayu980606
    /**
     * Prints message to indicate a the total amount paid by a payee in a certain project.
     * @param payee name of the payee.
     * @param amount total amount paid by the payee.
     * @param project the current project name.
     */
    public void printTotalCostMessage(String payee, double amount, String project) {
        System.out.print(line);
        System.out.println("\t" + "Current project is " + project + ".");
        System.out.println("\t" + "The total cost paid by " + payee + " is: " + amount + " dollars.");
        System.out.println("\t" + " use `getpayee " + payee + "` command to see the detail of the payee's payments.");
        System.out.print(line);
    }

    //@@author
    /**
     * Prints message to indicate a Payment being deleted.
     * 
     * @param payee Payee containing identification information of Payee.
     * @param name  the name of Payee to make Payments to.
     */
    public void printdeletePayeeMessage(String name, Payee payee, int payeesize, String currentProjectName) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've deleted this payee:");
        payee.printPayee();
        System.out.print("\t" + "There are " + payeesize + " payees in project " + currentProjectName + ".\n");
        System.out.print(line);
    }

    /**
     * Takes a user input and substring of input.
     * Prints a message to suggest a valid form of input. 
     * @param dict A Set of words that will be used
     * @param input A string representing the user's input
     * @param word A substring of input that is to be replaced by a guess
     */
    public void printSuggestion(Set<String> dict, String input, String word) throws AlphaNUSException {
        Suggest suggest = new Suggest();
        String suggestion = suggest.guess(dict, word);
        String replacement = input.replaceFirst(word, suggestion);
        exceptionMessage("\tMaybe you meant: " + replacement);
    }

    //@@author lijiayu980606
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
     * @param amount amount to be added.
     */
    public void printAddFundMessage(Fund fund, double amount) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've added " + amount + " dollars to the fund. The new fund is as follows:");
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
     * Prints message of adding a fund to all projects.
     * @param fund the fund for all projects.
     * @param amount amount to be added.
     * @param project project to be added to.
     * @param projectname the name of the affected project
     */
    public void printReduceBudgetMessage(Fund fund, Double amount, Project project, String projectname) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've reduced " + amount + " dollars from the budget of the project "
                + projectname + ":");
        System.out.println(project.giveProject());
        System.out.println("");
        System.out.println("\t" + "The new fund is as follows:");
        System.out.print(fund.giveFund());
        System.out.print(line);
    }

    /**
     * Prints message of adding a fund to all projects.
     * @param fund the fund for all projects.
     * @param amount amount to be set.
     */
    public void printResetFundMessage(Fund fund, double amount) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've changed the new fund as " + amount
                + " dollars. The new fund is as follow:");
        System.out.print(fund.giveFund());
        System.out.print(line);
    }

    /**
     * Prints out the statement of accounts.
     * @param managermap managermap containing Payee and Payments information.
     */
    //@@author
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
    public void undoMessage() {
        System.out.println("Got it! I have undone the previous command.");
    }

    /**
     * Prints message to indicate that the previous command has been redone.
     */
    public void redoMessage() {
        System.out.println("Got it! I have redone the previous command.");
    }

    /**
     * Prints out the pending and overdue payments in a sorted order with the earliest on eon the top.
     * @param paymentlist the list of all payments
     */
    public void printReminderMessage(ArrayList<Payments> paymentlist) {
        System.out.print(line);
        System.out.print("\tYour reminder is as follows:\n");
        for (int i = 0; i < paymentlist.size(); i++) {
            Payments p = paymentlist.get(i);
            System.out.println("\t" + (i + 1) + ":");
            System.out.println("\t" + "Deadline: " + p.deadline);
            System.out.println("\t" + "Project: " + p.project);
            System.out.println("\t" + "Payee: " + p.payee);
            System.out.println("\t" + "Item: " + p.item);
            System.out.println("\t" + "Status: " + p.status);
        }
        System.out.print(line);
    }

    /**
     * Prints message to indicate completion of backup.
     * @param projectsize Number of projects in record.
     * @param backupfund Backup fund that was loaded from storage.
     */
    public void printBackupComplete(int projectsize, Fund backupfund) {
        System.out.print(line);
        System.out.println("\t" + "Load Complete!");
        System.out.println("\t" + "Replaced projects with " + projectsize + " new projects "
                            + "from backup.");
        System.out.println("\t" + "Replaced history with history data from backup.");
        System.out.println("\t" + "Replaced fund with fund data from backup.");
        System.out.println("\t" + "Replaced tasklist with fund data from backup.");
        System.out.println("");
        System.out.println("\t" + "Current fund data is as follows:");
        System.out.print(backupfund.giveFund());
        System.out.print(line);
    }

    //@@author
    /**
     * Prints out a help message with command formats.
     */
    public void printHelpMessage() {
        System.out.print(line);
        System.out.println("\t*Help*");
        System.out.println("");
        System.out.println("Project:");
        System.out.println("\tLoad Backup:         " + commandFormat.loadBackupFormat());
        System.out.println("\tAdd Project:         " + commandFormat.addProjectFormat());
        System.out.println("\tDelete Project:      " + commandFormat.deleteProjectFormat());
        System.out.println("\tList Projects:       " + commandFormat.listProjectFormat());
        System.out.println("\tGo to a Project:     " + commandFormat.gotoProjectFormat());
        System.out.println("\tShow Budget:         " + commandFormat.showBudgetFormat());
        System.out.println("\tReduce Budget:       " + commandFormat.reducebudgetFormat());
        System.out.println("Fund:");
        System.out.println("\tSet Fund:            " + commandFormat.setFundFormat());
        System.out.println("\tAdd Fund:            " + commandFormat.addFundFormat());
        System.out.println("\tAssign budget:       " + commandFormat.assignFundFormat());
        System.out.println("\tChange Fund:         " + commandFormat.resetFundFormat());
        System.out.println("\tShow Fund:           " + commandFormat.showFundFormat());
        System.out.println("Payment and Payee:");
        System.out.println("\tAdd Payee:           " + commandFormat.addPayeeFormat());
        System.out.println("\tAdd Payment:         " + commandFormat.addPaymentFormat());
        System.out.println("\tDelete Payee:        " + commandFormat.deletePayeeFormat());
        System.out.println("\tDelete Payment:      " + commandFormat.deletePaymentFormat());
        System.out.println("\tList Current Project Payments: " + commandFormat.listPaymentCurrFormat());
        System.out.println("\tList Specific Project Payments:" + commandFormat.listPaymentProjectFormat());
        System.out.println("\tList Payee Payments: " + commandFormat.listPaymentPayeeFormat());
        System.out.println("\tFind Payee:          " + commandFormat.findPayeeFormat());
        System.out.println("\tFind Payment:        " + commandFormat.findPaymentFormat());
        System.out.println("\tEdit Payment:        " + commandFormat.editPaymentFormat());
        System.out.println("\tEdit Payee:          " + commandFormat.editPayeeFormat());
        System.out.println("\tTotal Cost:          " + commandFormat.totalCostFormat());
        System.out.println("\tReminder:            " + commandFormat.reminderFormat());
        System.out.println("Tasks:");
        System.out.println("\tAdd Todo:            " + commandFormat.addTodoFormat());
        System.out.println("\tAdd Deadline:        " + commandFormat.addDeadlineFormat());
        System.out.println("\tDone Task:           " + commandFormat.doneTaskFormat());
        System.out.println("\tDelete Task:         " + commandFormat.deleteTaskFormat());
        System.out.println("\tFind Task:           " + commandFormat.findTaskFormat());
        System.out.println("\tList Tasks:          " + commandFormat.listTasksFormat());
        System.out.println("\tSnooze Deadline:     " + commandFormat.snoozeFormat());
        System.out.println("\tPostpone Deadline:   " + commandFormat.postponeFormat());
        System.out.println("\tReschedule Deadline: " + commandFormat.rescheduleFormat());
        System.out.println("\tView Schedule:       " + commandFormat.viewScheduleFormat());
        System.out.println("History:");
        System.out.println("\tHistory of Commands: " + commandFormat.historyFormat());
        System.out.println("\tView History within a certain period: " + commandFormat.viewhistoryFormat());
        System.out.println("\tExit:                " + commandFormat.exitFormat());

        System.out.print(line);
    }

    public void cantUndomessage() {
        System.out.println("This command can't be undone. Only add payment, delete payment, add payee and delete payee can be undone.");
    }

    public void cantRedomessage() {
        System.out.println("A command can only be redone if the previous command is undo.");
    }

    /**
     * Returns the scanned input.
     * @return Scanned input.
     */
    public Scanner getIn() {
        return this.in;
    }
}
