package wallet.ui;

import wallet.logic.command.ListCommand;
import wallet.model.contact.Contact;
import wallet.model.help.Help;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.thread.ChartThread;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    /**
     * Scanner object used for reading input from user.
     */
    private Scanner sc;
    private static final String MESSAGE_NOTE = "Note the following when reading help sections:";
    private static final String MESSAGE_INDICATOR_REQUIRED = "<> indicates required parameters for command";
    private static final String MESSAGE_INDICATOR_OPTIONAL = "[]  indicates optional parameters for command";
    private static final String MESSAGE_AVAILABLE_SECTIONS = "The following help sections are available:";
    private static final String MESSAGE_CHOOSE_SECTION = "Read a section by typing in: help <section number>";


    /**
     * Constructs a new ui.Ui object.
     */
    public Ui() {
        sc = new Scanner(System.in);
    }

    //@@author matthewng1996

    /**
     * Prints the welcome message of the program.
     */
    public void welcomeMsg() {
        String logo = " __     __     ______     __         __         ______     ______  \n"
                    + "/\\ \\  _ \\ \\   /\\  __ \\   /\\ \\       /\\ \\       /\\  ___\\   /\\__  _\\\n"
                    + "\\ \\ \\/ \".\\ \\  \\ \\  __ \\  \\ \\ \\____  \\ \\ \\____  \\ \\  __\\   \\/_/\\ \\/\n"
                    + " \\ \\__/\".~\\_\\  \\ \\_\\ \\_\\  \\ \\_____\\  \\ \\_____\\  \\ \\_____\\    \\ \\_\\\n"
                    + "  \\/_/   \\/_/   \\/_/\\/_/   \\/_____/   \\/_____/   \\/_____/     \\/_/";
        System.out.println(logo);

        printLine();
        System.out.println("Hello! Welcome to the WalletCLI Application!");
        System.out.println("What can I do for you?");
        printLine();
    }

    //@@author

    /**
     * Prints the goodbye message when the user exits the program.
     */
    public void byeMsg() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prompts the user for input.
     *
     * @return The input of the user.
     */
    public String readLine() {
        return sc.nextLine();
    }

    /**
     * Prints the separator lines for UI.
     */
    public void printLine() {
        System.out.println("_______________________________________________________________________________");
    }

    //@@author kyang96

    /**
     * Displays the expense list in table format.
     */
    public static void printExpenseTable(ArrayList<Expense> expenseList) {
        System.out.println("-----------------------------------------------------"
                + "---------------------------------------------------\n"
                + "|  ID  |              Description                 |"
                + "  Category  |    Date    |    Amount    | Recurring |\n"
                + "|---------------------------------------------------------------"
                + "---------------------------------------|");
        double total = 0;
        for (Expense e : expenseList) {
            if (e.isRecurring()) {
                System.out.printf("| %-4d | %-40s | %-10s | %-10s |  $%-9.2f  |  %-7s  |\n", e.getId(),
                        e.getDescription(), e.getCategory(), e.getDate(), e.getAmount(), e.getRecFrequency());
            } else {
                System.out.printf("| %-4d | %-40s | %-10s | %-10s |  $%-9.2f  |  %-7s  |\n",
                        e.getId(), e.getDescription(), e.getCategory(), e.getDate(), e.getAmount(), "No");

            }
            total += e.getAmount();
        }
        System.out.println("-----------------------------------------------------"
                + "---------------------------------------------------");
        System.out.printf("Total amount spent: $%.2f\n", total);
    }

    /**
     * Displays a single expense in table format.
     *
     * @param e Expense to be displayed.
     */
    public static void printExpense(Expense e) {
        System.out.println("-----------------------------------------------------"
                + "---------------------------------------------------\n"
                + "|  ID  |              Description                 |"
                + "  Category  |    Date    |   Amount   | Recurring |\n"
                + "|---------------------------------------------------------------"
                + "---------------------------------------|");
        if (e.isRecurring()) {
            System.out.printf("| %-4d | %-40s | %-10s | %-10s |  $%-9.2f  |  %-7s  |\n", e.getId(),
                    e.getDescription(), e.getCategory(), e.getDate(), e.getAmount(), e.getRecFrequency());
        } else {
            System.out.printf("| %-4d | %-40s | %-10s | %-10s |  $%-9.2f  |  %-7s  |\n",
                    e.getId(), e.getDescription(), e.getCategory(), e.getDate(), e.getAmount(), "No");
        }
        System.out.println("-----------------------------------------------------"
                + "---------------------------------------------------");
    }

    /**
     * Displays a bar chart for percentage of total over budget.
     */
    public void drawBarChart(double total, double budget) {
        int maxLength = 20;
        int barLength = 20;
        if (budget != 0 && budget > total) {
            barLength = (int) (total / budget * maxLength);
        }
        double percentage = total / budget * 100;
        String bar = "";
        for (int i = 0; i < barLength; i++) {
            bar += '#';
        }
        for (int i = barLength; i < maxLength; i++) {
            bar += ' ';
        }
        System.out.printf("You have spent %.1f%% of your budget.\n", percentage);
        System.out.print("[" + bar + "] ");
        System.out.printf("$%.2f/$%.2f\n", total, budget);
    }

    //@@author matthewng1996
    public void drawPieChart(ArrayList<Expense> expenseList) {
        ChartThread chartThread = new ChartThread(expenseList);
        System.out.println("Please wait while we draw the pie chart...");
    }

    //@@author Xdecosee

    /**
     * Displays the contact list in table format.
     */
    public static void printContactTable(ArrayList<Contact> contactList) {
        ArrayList<Contact> contactListCopy = contactList;
        String dash = "-";
        String lineBreak = new String(new char[100]).replace("\0", dash);
        String headerBreak = new String(new char[98]).replace("\0", dash);
        System.out.println(lineBreak);
        System.out.printf("| %-4s | %-20s | %-20s | %-43s |\n", "ID", "Name", "Phone", "Detail");
        System.out.println("|" + headerBreak + "|");
        for (Contact c : contactListCopy) {
            String id = Integer.toString(c.getId()).trim();
            String name = c.getName();
            String phone = c.getPhoneNum();
            String detail = c.getDetail();

            if (phone == null) {
                phone = "";
            }

            if (detail == null) {
                detail = "";
            }

            System.out.printf("| %-4s | %-20s | %-20s | %-43s |\n", id, name, phone, detail);
        }
        System.out.println(lineBreak);
    }

    /**
     * Displays a contact in table format.
     */
    public static void printContact(Contact c) {
        String dash = "-";
        String lineBreak = new String(new char[100]).replace("\0", dash);
        String headerBreak = new String(new char[98]).replace("\0", dash);
        System.out.println(lineBreak);
        System.out.printf("| %-4s | %-20s | %-20s | %-43s |\n", "ID", "Name", "Phone", "Detail");
        System.out.println("|" + headerBreak + "|");

        String id = Integer.toString(c.getId()).trim();
        String name = c.getName();
        String phone = c.getPhoneNum();
        String detail = c.getDetail();

        if (phone == null) {
            phone = "";
        }

        if (detail == null) {
            detail = "";
        }

        System.out.printf("| %-4s | %-20s | %-20s | %-43s |\n", id, name, phone, detail);
        System.out.println(lineBreak);
    }
    //@@author

    /**
     * Displays the loan list in table format.
     */
    public static void printLoanTable(ArrayList<Loan> loanList) {
        //@@author A0171206R
        System.out.println(ListCommand.MESSAGE_LIST_LOANS);
        printLoanTableHeaders();
        for (Loan loan : loanList) {
            printLoanRow(loan);
        }
        printLoanTableClose();
        //@@author
    }

    /**
     * Default headers for Loan table.
     */
    public static void printLoanTableHeaders() {
        //@@author A0171206R
        System.out.println("--------------------------------------------------------"
                + "-------------------------------------------------------"
                + "-------------------------------------\n"
                + "|  ID  |  Settled  |              Description                 |  Amount  |    Date    |   "
                + "Borrow/Lend   |    Contact Name    |    Contact Number   |\n"
                + "|-----------------------------------------------------"
                + "---------------------------------------------------------"
                + "------------------------------------|");
        //@@author
    }

    /**
     * Prints the specified Loan object in table format.
     *
     * @param loan The Loan object.
     */
    public static void printLoanRow(Loan loan) {
        //@@author A0171206R
        if (!loan.getIsLend() && !loan.getIsSettled()) {
            System.out.printf("| %-4d |  %-7s  | %-40s | $%-7.2f | %-10s |   %-11s   | %-18s | %-19s |\n",
                    loan.getId(), "No", loan.getDescription(), loan.getAmount(), loan.getDate(), "Borrow from",
                    loan.getPerson().getName(), loan.getPerson().getPhoneNum());
        } else if (!loan.getIsLend() && loan.getIsSettled()) {
            System.out.printf("| %-4d |  %-7s  | %-40s | $%-7.2f | %-10s |   %-11s   | %-18s | %-19s |\n",
                    loan.getId(), "Yes", loan.getDescription(), loan.getAmount(), loan.getDate(), "Borrow from",
                    loan.getPerson().getName(), loan.getPerson().getPhoneNum());
        } else if (loan.getIsLend() && !loan.getIsSettled()) {
            System.out.printf("| %-4d |  %-7s  | %-40s | $%-7.2f | %-10s |   %-11s   | %-18s | %-19s |\n",
                    loan.getId(), "No", loan.getDescription(), loan.getAmount(), loan.getDate(), "Lend to",
                    loan.getPerson().getName(), loan.getPerson().getPhoneNum());
        } else if (loan.getIsLend() && loan.getIsSettled()) {
            System.out.printf("| %-4d |  %-7s  | %-40s | $%-7.2f | %-10s |   %-11s   | %-18s | %-19s |\n",
                    loan.getId(), "Yes", loan.getDescription(), loan.getAmount(), loan.getDate(), "Lend to",
                    loan.getPerson().getName(), loan.getPerson().getPhoneNum());
        }
        //@@author
    }

    /**
     * Prints line to close of the Loans table.
     */
    public static void printLoanTableClose() {
        //@@author A0171206R
        System.out.println("----------------------------------------"
                + "---------------------------------------------------"
                + "--------------------------------------------------------");
        //@@author
    }

    /**
     * Prints an error message with the given content.
     *
     * @param exceptionMessage The specifics of the error.
     */
    public static void printError(String exceptionMessage) {
        System.out.println("OOPS!!! " + exceptionMessage);
    }

    //@@author Xdecosee
    /**
     * Show User a list of available help sections.
     *
     * @param helpList list of available help sections.
     */
    public void showHelp(ArrayList<Help> helpList) {

        int index = 1;

        System.out.println(MESSAGE_AVAILABLE_SECTIONS);

        for (Help h : helpList) {
            System.out.println(index + "." + h.getChoice());
            index += 1;
        }

        System.out.println();
        System.out.println(MESSAGE_NOTE);
        System.out.println(MESSAGE_INDICATOR_REQUIRED);
        System.out.println(MESSAGE_INDICATOR_OPTIONAL);
        System.out.println();
        System.out.println(MESSAGE_CHOOSE_SECTION);
    }
}
