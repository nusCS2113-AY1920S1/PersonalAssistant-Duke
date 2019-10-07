import java.util.*;
import java.io.*;
import java.text.*;

public class Ui {
    protected Scanner inputScanner;
    protected String input;
    public static String line = "____________________________________________________________________________________________________________________________________________";
    public static String tab = "\t";

    public Ui() {
        inputScanner = new Scanner(System.in);
        welcome();
    }

    public String getInput() {
        input = inputScanner.nextLine();
        return input;
    }

    public void printLine() {
        System.out.println(tab + line);
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
    public void welcome() {
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
        formattedPrintArray(new ArrayList<String>(Arrays.asList("Hello. I am RIM.", "Resource & Inventory Management",
                "The facilities and logistics management system.", "What can I do for you?")));
    }


}