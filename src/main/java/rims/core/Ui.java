package rims.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import rims.reserve.Reservation;
import rims.resource.Resource;

public class Ui {
    protected Scanner inputScanner;
    protected String input;
    protected int intInput;
    public static String line = "____________________________________________________________________________________________________________________________________________";
    public static String hash = "********************************************************************************************************************************************";
    public static String tab = "\t";

    public Ui() {
        inputScanner = new Scanner(System.in);
        welcomeWithLogo();
    }

    public String getInput() {
        input = inputScanner.nextLine();
        return input;
    }

    public int getIntegerInput() {

        intInput = inputScanner.nextInt();
        return intInput;
    }

    public void printLine() {
        System.out.println(tab + line);
    }

    public void ErrorPrint(String Error) {
        System.out.println(tab + hash);
        System.out.println(tab + Error);
        System.out.println(tab + hash);
    }

    public void print(String input) {
        System.out.println(tab + input);
    }

    public void printArray(ArrayList<String> inputs) {
        for (int i = 0; i < inputs.size(); i++) {
            System.out.println("\t" + inputs.get(i));
        }
    }

    public void printResourceArray(ArrayList<Resource> resources){
        for (int i = 0; i < resources.size(); i ++ ){
            System.out.println("\t" + resources.get(i).toString());
        }
    }

    public void printReservationArray(ArrayList<Reservation>  reservations) {
        for (int i = 0; i < reservations.size(); i++) {
            System.out.println("\t" + reservations.get(i).toString());
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

    /**
     * Prints a welcome message when Duke is started up.
     */
    public void welcomeWithLogo() {
        printLogo();
        formattedPrintArray(new ArrayList<String>(Arrays.asList("Hello. I am RIM.", "Resource & Inventory Management",
                "The facilities and logistics management system.", "What can I do for you?")));
    }

    public void welcome() {
        formattedPrintArray(new ArrayList<String>(Arrays.asList("Hello. I am RIM.", "Resource & Inventory Management",
                "The facilities and logistics management system.", "What can I do for you?")));
    }

    public void Home(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("add - add new resource to inventory");
        list.add("delete - delete existing resource from inventory");
        list.add("reserve - add new reservation request");
        list.add("return - return/cancel reservation");
        list.add("list - see all resources and current reservations");
        
        printLine();
        formattedPrintArray(list);
        printLine();
    }
    public void printLogo() {
        String logo = "\n" + "          _____                    _____                    _____          \n"
                + "         /\\    \\                  /\\    \\                  /\\    \\         \n"
                + "        /::\\    \\                /::\\    \\                /::\\____\\        \n"
                + "       /::::\\    \\               \\:::\\    \\              /::::|   |        \n"
                + "      /::::::\\    \\               \\:::\\    \\            /:::::|   |        \n"
                + "     /:::/\\:::\\    \\               \\:::\\    \\          /::::::|   |        \n"
                + "    /:::/__\\:::\\    \\               \\:::\\    \\        /:::/|::|   |        \n"
                + "   /::::\\   \\:::\\    \\              /::::\\    \\      /:::/ |::|   |        \n"
                + "  /::::::\\   \\:::\\    \\    ____    /::::::\\    \\    /:::/  |::|___|______  \n"
                + " /:::/\\:::\\   \\:::\\____\\  /\\   \\  /:::/\\:::\\    \\  /:::/   |::::::::\\    \\ \n"
                + "/:::/  \\:::\\   \\:::|    |/::\\   \\/:::/  \\:::\\____\\/:::/    |:::::::::\\____\\\n"
                + "\\::/   |::::\\  /:::|____|\\:::\\  /:::/    \\::/    /\\::/    / ~~~~~/:::/    /\n"
                + " \\/____|:::::\\/:::/    /  \\:::\\/:::/    / \\/____/  \\/____/      /:::/    / \n"
                + "       |:::::::::/    /    \\::::::/    /                       /:::/    /  \n"
                + "       |::|\\::::/    /      \\::::/____/                       /:::/    /   \n"
                + "       |::| \\::/____/        \\:::\\    \\                      /:::/    /    \n"
                + "       |::|  ~|               \\:::\\    \\                    /:::/    /     \n"
                + "       |::|   |                \\:::\\    \\                  /:::/    /      \n"
                + "       \\::|   |                 \\:::\\____\\                /:::/    /       \n"
                + "        \\:|   |                  \\::/    /                \\::/    /        \n"
                + "         \\|___|                   \\/____/                  \\/____/         \n"
                + "                                                      ";
        System.out.println(logo);
    }
}