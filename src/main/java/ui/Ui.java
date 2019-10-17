package ui;

import common.TaskList;
import payment.Payee;
import payment.Payments;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Ui to interact with the user.
 */
public class Ui {
    private static final String line = "    ____________________________________________________________\n";
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
     * Prints starting message for the Duke program.
     */
    public void startMessage() {
        String logo = line
                + "     Hello! I'm Duke\n"
                + "     What can I do for you?\n"
                + line;
        System.out.print(logo);
    }

    /**
     * Prints ending message for the Duke program.
     */
    public void byeMessage() {
        String output = line + "     Bye. Hope to see you again soon!\n" + line;
        System.out.println(output);
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
    public void printDeletePaymentMessage(String payee, Payments payment, int size) {
        System.out.print(line + "     Noted. I've removed this payment: \n");
        System.out.println("\t" + "Payee: " + payee);
        System.out.println("\t" + "Item: " + payment.item);
        System.out.println("\t" + "Cost: " + payment.cost);
        System.out.println("\t" + "Invoice: " + payment.inv);
        System.out.print("\t" + payee + " now has " + size + " payments in the record." + "\n");
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
     * Prints message to indicate a task being edited.
     * @param task Task to be edited.
     */
    public void printEditMessage(Task task) {
        System.out.print(line + "     Got it. I've edited this task:  \n");
        System.out.print("       " + task.giveTask() + "\n");
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
    public void printAddPaymentMessage(String payee, Payments payment, int size) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've added this payment:");
        System.out.println("\t" + "Payee: " + payee);
        System.out.println("\t" + "Item: " + payment.item);
        System.out.println("\t" + "Cost: " + payment.cost);
        System.out.println("\t" + "Invoice: " + payment.inv);
        System.out.println("\t" + "Deadline: " + payment.deadline);
        System.out.println("\t" + "Status: " + payment.status);
        System.out.print("\t" + payee + " now has " + size + " payments in the record." + "\n");
        System.out.print(line);
    }

    /**
     * Prints message to indicate a Payment being added to a certain Payee.
     * @param payee Payee containing identification information of Payee.
     * @param name the name of Payee to make Payments to.
     */
    public void printAddPayeeMessage(String name, Payee payee, int payeesize) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've added this payee:");
        System.out.println("\t" + "Payee: " + name);
        System.out.println("\t" + "Email: " + payee.email);
        System.out.println("\t" + "Matric No: " + payee.matricNum);
        System.out.println("\t" + "Phone No: " + payee.phoneNum);
        System.out.print("\t" + "There are " + payeesize + " payees in the record." + "\n");
        System.out.print(line);
    }

    /**
     * Prints message to indicate a Payment being deleted.
     * @param payee Payee containing identification information of Payee.
     * @param name the name of Payee to make Payments to.
     */
    public void printdeletePayeeMessage(String name, Payee payee) {
        System.out.print(line);
        System.out.println("\t" + "Got it. I've deleted this payee:");
        System.out.println("\t" + "Payee: " + name);
        System.out.println("\t" + "Email: " + payee.email);
        System.out.println("\t" + "Matric No: " + payee.matricNum);
        System.out.println("\t" + "Phone No: " + payee.phoneNum);
        System.out.print(line);
    }

    /**
     * Prints out the statement of accounts.
     * @param managermap managermap containing Payee and Payments information.
     */
    public static void generateStatementofAccounts(HashMap<String, Payee> managermap) {
        System.out.print("Item\tExpense\n");
        for (Payee payee : managermap.values()) {
            for (Payments payment : payee.payments) {
                System.out.println(payment.item + "\t" + payment.cost);
            }
        }
    }

    /**
     * Returns the scanned input.
     * @return Scanned input.
     */
    public Scanner getIn() {
        return this.in;
    }
}
