package owlmoney.ui;

/**
 * Ui handles messages shown to the user of this application.
 * It does not need to be instantiated with any tasks; these are passed
 * as arguments to its methods.
 */

public class Ui {

    private static final String ITEMNO = "Item No.";
    private static final String DESCRIPTION = "Description";
    private static final String AMOUNT = "Amount";
    private static final String DATE = "Date";
    private static final String CATEGORY = "Category";
    private static final String TRANSACTIONNO = "Transaction No.";
    private static final String TRANSTYPE = "transaction";
    private static final String ITEMTYPE = "item";
    private static final String CARDNAME = "Card Name";
    private static final String MONLIMIT = "Monthly Limit";
    private static final String REMLIMIT = "Remaining Limit";
    private static final String REBATE = "Rebate";
    private static final String ACCNAME = "Account Name";
    private static final String ACCTYPE = "Account Type";
    private static final String CURRAMOUNT = "Current Amount";
    private static final String INCOME = "Income";
    private static final String GOALNAME = "To Accomplish";
    private static final String GOALAMOUNT = "Amount to save";
    private static final String GOALDATE = "To be achieved by";
    private static final String BONDNAME = "Bond Name";
    private static final String BONDRATE = "Rate";
    private static final String BONDDATE = "Date of Purchased";
    private static final String BONDYEAR = "Number of Years";

    /**
     * Prints a message line to the user.
     *
     * @param msg The message to print.
     */
    public void printMessage(String msg) {
        System.out.println(msg);
    }

    /**
     * Prints the transaction header.
     */
    public void printTransactionHeader(String type) {
        if (ITEMTYPE.equals(type)) {
            System.out.printf("%-20s %-55s %-15s %-20s %-20s %n", ITEMNO, DESCRIPTION, AMOUNT, DATE, CATEGORY);
        } else if (TRANSTYPE.equals(type)) {
            System.out.printf("%-20s %-55s %-15s %-20s %-20s %n", TRANSACTIONNO, DESCRIPTION, AMOUNT, DATE, CATEGORY);
        }
        System.out.println("-----------------------------------------------------------------------------" +
                "----------------------------------------------------");
    }

    /**
     * Prints the transaction being specified.
     *
     * @param num         Represents the numbering of the transaction.
     * @param description Describes the listed transaction.
     * @param amount      Represents the amount spent for the listed transaction.
     * @param date        Represents the date entered for the listed transaction.
     * @param category    Represents the category which the listed transaction falls under.
     */
    public void printTransaction(int num, String description, String amount, String date,
            String category) {
        System.out.printf("%-20s %-50s %-15s %-20s %-20s %n", num, description, amount, date, category);
    }

    /**
     * Prints the card header.
     */
    public void printCardHeader() {
        System.out.printf("%-20s %-30s %-15s %-20s %-15s %n", ITEMNO, CARDNAME, MONLIMIT, REMLIMIT, REBATE);

        System.out.println("-----------------------------------------------------------------------------" +
                "----------------------------------------------------");
    }

    /**
     * Prints the card details being specified.
     *
     * @param num      Represents the numbering of the card.
     * @param name     Represents the name of the card.
     * @param monLimit Represents the monthly limit of the card.
     * @param remLimit Represents the remaining card limit left for the card.
     * @param rebate   Represents the card rebate in percentage.
     */
    public void printCard(int num, String name, String monLimit, String remLimit, String rebate) {
        System.out.printf("%-20s %-30s %-15s %-20s %-15s %n", num, name, monLimit, remLimit, rebate);
    }

    /**
     * Prints bank header.
     */
    public void printBankHeader() {
        System.out.printf("%-20s %-35s %-15s %-15s %-15s %n", ITEMNO, ACCNAME, ACCTYPE, CURRAMOUNT, INCOME);

        System.out.println("-----------------------------------------------------------------------------" +
                "----------------------------------------------------");
    }

    /**
     * Prints the investment account details being specified.
     *
     * @param num    Represents the numbering of the investment account.
     * @param name   Represents the name of the investment account.
     * @param type   Represents the account type.
     * @param amount Represents the current amount in the listed investment account.
     */
    public void printInvestment(int num, String name, String type, String amount) {
        System.out.printf("%-20s %-35s %-15s %-15s %-15s %n", num, name, type, amount, "Not Applicable");
    }

    /**
     * Prints the saving account details being specified.
     *
     * @param num    Represents the numbering of the saving account.
     * @param name   Represents the name of the saving account.
     * @param type   Represents the account type.
     * @param amount Represents the current amount in the listed saving account.
     * @param income Represents the income in the listed saving account.
     */
    public void printSaving(int num, String name, String type, String amount, String income) {
        System.out.printf("%-20s %-35s %-15s %-15s %-15s %n", num, name, type, amount, income);
    }

    /**
     * Prints goal header.
     */
    public void printGoalHeader() {
        System.out.printf("%-20s %-15s %-20s %-20s %n", ITEMNO, GOALNAME, GOALAMOUNT, GOALDATE);

        System.out.println("-----------------------------------------------------------------------------" +
                "----------------------------------------------------");
    }

    /**
     * Prints the goal details being specified.
     *
     * @param num    Represents the numbering of the goal.
     * @param name   Represents the goal name
     * @param amount Represents the amount to save up in the goal.
     * @param date   Represents the date to accomplish the goal.
     */
    public void printGoal(int num, String name, String amount, String date) {
        System.out.printf("%-20s %-15s %-20s %-20s %n", num, name, amount, date);
    }

    /**
     * Prints bond header.
     */
    public void printBondHeader() {
        System.out.printf("%-20s %-30s %-15s %-10s %-20s %-10s %n", ITEMNO, BONDNAME, AMOUNT, BONDRATE, BONDDATE,
                BONDYEAR);

        System.out.println("-----------------------------------------------------------------------------" +
                "----------------------------------------------------");
    }

    /**
     * Prints the bond details being specified.
     *
     * @param num    Represents the numbering of the bond.
     * @param name   Represents the bond name.
     * @param amount Represents the amount of the bond.
     * @param rate   Represents the rate for the bond.
     * @param date   Represents the date of purchase for the bond.
     * @param year   Represents the year for the bond.
     */
    public void printBond(int num, String name, String amount, String rate, String date, int year) {
        System.out.printf("%-20s %-30s %-15s %-10s %-20s %-10s %n", num, name, amount, rate, date, year);
    }

    /**
     * Prints the divider to separate the section of the output.
     */
    public void printDivider() {
        System.out.println("-----------------------------------------------------------------------------" +
                "----------------------------------------------------");
    }

    /**
     * Prints a greeting message to the user, which happens at startup.
     */
    public void greet(String username) {
        printMessage("Welcome " + username + "!");
        printMessage("Hello! I'm OwlMoney");
        printMessage("What can I do for you?");
    }

    /**
     * Prompts the user for a username on first instance of running this program.
     */
    public void firstTimeRun() {
        printMessage("Please enter a username:");
    }

    /**
     * Prints an error message with the given content.
     *
     * @param exceptionMessage The specifics of the error.
     */
    public void printError(String exceptionMessage) {
        printMessage("☹ OOPS!!! " + exceptionMessage);
    }


}
