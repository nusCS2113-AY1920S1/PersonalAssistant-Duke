package wallet.ui;

import wallet.logic.LogicManager;
import wallet.model.contact.Contact;
import wallet.model.record.Expense;
import wallet.model.record.Loan;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    /**
     * Scanner object used for reading input from user.
     */
    private Scanner sc;

    /**
     * Constructs a new ui.Ui object.
     */
    public Ui() {
        sc = new Scanner(System.in);
    }

    /**
     * Prints the welcome message of the program.
     */
    public void welcomeMsg() {
        String logo = "__             __       _   _         \n"
                + "\\ \\    __     / /      | | | |        _\n"
                + " \\ \\  /  \\   / /___,__ | | | | ______| |_    \n"
                + "  \\ \\/ /\\  \\/ /  [] | || | | |  []_\\_  __|\n"
                + "   \\__/  \\___/ \\__,_|_||_| |_|\\___/  |_|\n";
        System.out.println("Hello from\n" + logo);

        printLine();
        System.out.println("Hello! Welcome to the WalletCLi Application!");
        System.out.println("What can I do for you?");
        printLine();
    }

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

    /**
     * Displays the expense list in table format.
     */
    public static void printExpenseTable() {
        ArrayList<Expense> expenseList = LogicManager.getWallet().getExpenseList().getExpenseList();
        System.out.println("Here are the expenses in your list:");
        System.out.println("-----------------------------------------------------"
                + "-----------------------------------------------\n"
                + "|  ID  |              Description                 |"
                + " Category |    Date    |   Amount   | Recurring |\n"
                + "|-------------------------------------------------------------"
                + "-------------------------------------|");
        double total = 0;
        for (Expense e : expenseList) {
            if (e.isRecurring()) {
                System.out.printf("| %-4d | %-40s | %-8s | %-10s |  $%-7.2f  |  %-7s  |\n", e.getId(),
                        e.getDescription(), e.getCategory(), e.getDate(), e.getAmount(), e.getRecFrequency());
            } else {
                System.out.printf("| %-4d | %-40s | %-8s | %-10s |  $%-7.2f  |  %-7s  |\n",
                        e.getId(), e.getDescription(), e.getCategory(), e.getDate(), e.getAmount(), "No");

            }
            total += e.getAmount();
        }
        System.out.println("-----------------------------------------------------"
                + "-----------------------------------------------");
        System.out.println("Total amount spent: $" + total);
    }

    /**
     * Displays the contact list in table format.
     */
    public static void printContactTable() {
        ArrayList<Contact> contactListCopy = LogicManager.getWallet().getContactList().getContactList();
        String dash = "-";
        String lineBreak = dash.repeat(100);
        String headerBreak = dash.repeat(98);
        System.out.println("Here are the contacts in your list:");
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
     * Displays the loan list in table format.
     */
    public static void printLoanTable() {

        ArrayList<Loan> LoanList = LogicManager.getWallet().getLoanList().getLoanList();
        System.out.println("Here are the loans in your list:");
        System.out.println("--------------------------------------------------------" +
                "-------------------------------------------------------" +
                "------------------------------------\n"
                + "|  ID  |              Description                 |  Amount  |    Date    |" +
                "  Settled  |    Borrow/Lend   |    Contact Name    |   Contact Number  |\n"
                + "|-----------------------------------------------------" +
                "--------------------------------------------------------" +
                "------------------------------------|");
        double total = 0;
        for (Loan l : LoanList) {
            if (!l.getIsLend() && !l.getIsSettled()) {
                System.out.printf("| %-4d | %-40s | $%-7.2f | %-10s |  %-7s  |   %-12s   | %-20s | %-15s |\n", l.getId(),
                        l.getDescription(), l.getAmount(), l.getDate(), "No", "Borrow from",
                        l.getPerson().getName(), l.getPerson().getPhoneNum());
            } else if (!l.getIsLend() && l.getIsSettled()) {
                System.out.printf("| %-4d | %-40s | $%-7.2f | %-10s |  %-7s  |   %-12s   | %-20s | %-15s |\n", l.getId(),
                        l.getDescription(), l.getAmount(), l.getDate(), "Yes", "Borrow from",
                        l.getPerson().getName(), l.getPerson().getPhoneNum());
            } else if (l.getIsLend() && !l.getIsSettled()) {
                System.out.printf("| %-4d | %-40s | $%-7.2f | %-10s |  %-7s  |   %-12s   | %-20s | %-15s |\n", l.getId(),
                        l.getDescription(), l.getAmount(), l.getDate(), "No", "Lend to",
                        l.getPerson().getName(), l.getPerson().getPhoneNum());
            } else if (l.getIsLend() && l.getIsSettled()) {
                System.out.printf("| %-4d | %-40s | $%-7.2f | %-10s |  %-7s  |   %-12s   | %-20s | %-15s |\n", l.getId(),
                        l.getDescription(), l.getAmount(), l.getDate(), "Yes", "Lend to",
                        l.getPerson().getName(), l.getPerson().getPhoneNum());
            }
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
        /*ArrayList<Loan> LoanListCopy = LogicManager.getWallet().getLoanList().getLoanList();
        String dash = "-";
        String lineBreak = dash.repeat(100);
        String headerBreak = dash.repeat(98);
        System.out.println("Here are the loans in your list:");
        System.out.println(lineBreak);
        System.out.printf("| %-4s | %-40s | $%-7.2f | %-10s | %-12s | %-20s | %-20s |\n", "ID", "Description", "Amount", "Date", "Borrow/Lend", "Contact Name", "Contact Number");
        System.out.println("|" + headerBreak + "|");
        for (Loan l : LoanListCopy) {
            String id = Integer.toString(l.getId()).trim();
            String description = l.getDescription();
            String amount = Double.toString(l.getAmount()).trim();
            String detail = c.getDetail();

            if (phone == null) {
                phone = "";
            }

            if (detail == null) {
                detail = "";
            }

            System.out.printf("| %-4s | %-20s | %-20s | %-43s |\n", id, name, phone, detail);

        }

        System.out.println(lineBreak);*/
    }
}
