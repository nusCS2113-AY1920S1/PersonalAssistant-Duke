package rims.core;

import rims.command.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;

import java.io.*;
import java.text.*;
import java.time.*;
import java.time.format.*;

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

    public int getIntegerInput(){
        
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

    public void welcome(){
        formattedPrintArray(new ArrayList<String>(Arrays.asList("Hello. I am RIM.", "Resource & Inventory Management",
                "The facilities and logistics management system.", "What can I do for you?")));        
    }
    public void printLogo() {
        String logo = "\n" +
                "          _____                    _____                    _____                    _____          \n" +
                "         /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\         \n" +
                "        /::\\    \\                /::\\    \\                /::\\____\\                /::\\    \\        \n" +
                "       /::::\\    \\               \\:::\\    \\              /::::|   |               /::::\\    \\       \n" +
                "      /::::::\\    \\               \\:::\\    \\            /:::::|   |              /::::::\\    \\      \n" +
                "     /:::/\\:::\\    \\               \\:::\\    \\          /::::::|   |             /:::/\\:::\\    \\     \n" +
                "    /:::/__\\:::\\    \\               \\:::\\    \\        /:::/|::|   |            /:::/__\\:::\\    \\    \n" +
                "   /::::\\   \\:::\\    \\              /::::\\    \\      /:::/ |::|   |            \\:::\\   \\:::\\    \\   \n" +
                "  /::::::\\   \\:::\\    \\    ____    /::::::\\    \\    /:::/  |::|___|______    ___\\:::\\   \\:::\\    \\  \n" +
                " /:::/\\:::\\   \\:::\\____\\  /\\   \\  /:::/\\:::\\    \\  /:::/   |::::::::\\    \\  /\\   \\:::\\   \\:::\\    \\ \n" +
                "/:::/  \\:::\\   \\:::|    |/::\\   \\/:::/  \\:::\\____\\/:::/    |:::::::::\\____\\/::\\   \\:::\\   \\:::\\____\\\n" +
                "\\::/   |::::\\  /:::|____|\\:::\\  /:::/    \\::/    /\\::/    / ~~~~~/:::/    /\\:::\\   \\:::\\   \\::/    /\n" +
                " \\/____|:::::\\/:::/    /  \\:::\\/:::/    / \\/____/  \\/____/      /:::/    /  \\:::\\   \\:::\\   \\/____/ \n" +
                "       |:::::::::/    /    \\::::::/    /                       /:::/    /    \\:::\\   \\:::\\    \\     \n" +
                "       |::|\\::::/    /      \\::::/____/                       /:::/    /      \\:::\\   \\:::\\____\\    \n" +
                "       |::| \\::/____/        \\:::\\    \\                      /:::/    /        \\:::\\  /:::/    /    \n" +
                "       |::|  ~|               \\:::\\    \\                    /:::/    /          \\:::\\/:::/    /     \n" +
                "       |::|   |                \\:::\\    \\                  /:::/    /            \\::::::/    /      \n" +
                "       \\::|   |                 \\:::\\____\\                /:::/    /              \\::::/    /       \n" +
                "        \\:|   |                  \\::/    /                \\::/    /                \\::/    /        \n" +
                "         \\|___|                   \\/____/                  \\/____/                  \\/____/         \n" +
                "                                                                                                    \n";
        System.out.println(logo);
    }
}