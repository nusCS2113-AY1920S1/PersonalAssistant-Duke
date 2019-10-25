package rims.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import rims.resource.Reservation;
import rims.resource.ReservationList;
import rims.resource.Resource;

public class Ui {
    protected Scanner inputScanner;
    protected String input;
    protected int intInput;
    protected String line = "____________________________________________________________________________________________________________________________________________";
    protected String dash = "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ";
    protected String hash = "********************************************************************************************************************************************";
    protected String tab = "\t";
    protected ArrayList<String> welcomeMsg = new ArrayList<String>(Arrays.asList("Welcome to RIMS, your Resource & Inventory Management System.",
                                                                                 "How can I help you?"));
    protected ArrayList<String> commands = new ArrayList<String>(Arrays.asList("add - add a new resource to inventory",
                                                                               "delete - delete an existing resource from inventory",
                                                                               "loan - loan out an item from now till your desired future date",
                                                                               "reserve - reserve an item between two future dates",
                                                                               "return - return a loan or reservation",
                                                                               "list - see all resources and current reservations"));

    public Ui() {
        inputScanner = new Scanner(System.in);
        welcome();
    }

    public String getInput() {
        input = inputScanner.nextLine();
        return input;
    }

    public String getInput(String question) {
        formattedPrint(question);
        input = inputScanner.nextLine();
        return input;
    }

    public int getIntegerInput() {
        intInput = inputScanner.nextInt();
        return intInput;
    }

    public int getIntegerInput(String question) {
        formattedPrint(question);
        intInput = inputScanner.nextInt();
        return intInput;
    }

    public void printLine() {
        System.out.println(tab + line);
    }

    public void printDash() {
        System.out.println(tab + dash);
    }

    public void print(String input) {
        System.out.println(tab + input);
    }

    public void printArray(ArrayList<String> inputs) {
        for (int i = 0; i < inputs.size(); i++) {
            System.out.println("\t" + inputs.get(i));
        }
    }

    public void printEmptyLine() {
        System.out.println();
    }

    public void formattedPrintArray(ArrayList<String> inputs) {
        printLine();
        printArray(inputs);
        printLine();
    }

    public void formattedPrint(String input) {
        printLine();
        print(input);
        printLine();
    }

    public void farewell() {
        formattedPrint("Bye. Hope to see you again soon!");
    }

    public void welcome() {
        printLogo();
        formattedPrintArray(welcomeMsg);
        formattedPrintArray(commands);
    }

    public void printLogo() {
        String logo = "\n" +
                tab + "          _____                    _____                    _____                    _____          \n" +
                tab + "         /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\         \n" +
                tab + "        /::\\    \\                /::\\    \\                /::\\____\\                /::\\    \\        \n" +
                tab + "       /::::\\    \\               \\:::\\    \\              /::::|   |               /::::\\    \\       \n" +
                tab + "      /::::::\\    \\               \\:::\\    \\            /:::::|   |              /::::::\\    \\      \n" +
                tab + "     /:::/\\:::\\    \\               \\:::\\    \\          /::::::|   |             /:::/\\:::\\    \\     \n" +
                tab + "    /:::/__\\:::\\    \\               \\:::\\    \\        /:::/|::|   |            /:::/__\\:::\\    \\    \n" +
                tab + "   /::::\\   \\:::\\    \\              /::::\\    \\      /:::/ |::|   |            \\:::\\   \\:::\\    \\   \n" +
                tab + "  /::::::\\   \\:::\\    \\    ____    /::::::\\    \\    /:::/  |::|___|______    ___\\:::\\   \\:::\\    \\  \n" +
                tab + " /:::/\\:::\\   \\:::\\____\\  /\\   \\  /:::/\\:::\\    \\  /:::/   |::::::::\\    \\  /\\   \\:::\\   \\:::\\    \\ \n" +
                tab + "/:::/  \\:::\\   \\:::|    |/::\\   \\/:::/  \\:::\\____\\/:::/    |:::::::::\\____\\/::\\   \\:::\\   \\:::\\____\\\n" +
                tab + "\\::/   |::::\\  /:::|____|\\:::\\  /:::/    \\::/    /\\::/    / ~~~~~/:::/    /\\:::\\   \\:::\\   \\::/    /\n" +
                tab + " \\/____|:::::\\/:::/    /  \\:::\\/:::/    / \\/____/  \\/____/      /:::/    /  \\:::\\   \\:::\\   \\/____/ \n" +
                tab + "       |:::::::::/    /    \\::::::/    /                       /:::/    /    \\:::\\   \\:::\\    \\     \n" +
                tab + "       |::|\\::::/    /      \\::::/____/                       /:::/    /      \\:::\\   \\:::\\____\\    \n" +
                tab + "       |::| \\::/____/        \\:::\\    \\                      /:::/    /        \\:::\\  /:::/    /    \n" +
                tab + "       |::|  ~|               \\:::\\    \\                    /:::/    /          \\:::\\/:::/    /     \n" +
                tab + "       |::|   |                \\:::\\    \\                  /:::/    /            \\::::::/    /      \n" +
                tab + "       \\::|   |                 \\:::\\____\\                /:::/    /              \\::::/    /       \n" +
                tab + "        \\:|   |                  \\::/    /                \\::/    /                \\::/    /        \n" +
                tab + "         \\|___|                   \\/____/                  \\/____/                  \\/____/         \n" +
                tab + "                                                                                                    \n";
        System.out.println(logo);
    }

}