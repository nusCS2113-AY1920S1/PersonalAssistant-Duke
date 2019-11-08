package controlpanel;

import java.util.Scanner;

public class Ui {

    private Scanner scanner;
    private static String outputString = "";
    private static String graphContainerString = "";
    private String display =
            " _______      __     __     _         _        __      _    _____    __       _           _\n"
                    + "|   _____/    |  |   |   \\   |  |       /_\\      |  \\   |  |  |  ___|   |  |      /_\\        |  |    \n"
                    + "|  |____      |  |   |     \\|   |      /|  |\\     |    \\|  |   | |        |  |     /|  |\\       |  |\n"
                    + "|   ____/     |  |    |  \\  \\   |    /  |_|  \\   |  \\  \\  |   | |        |  |    /  |_|  \\    |  |\n"
                    + "|  |            |  |    |  | \\    |   |  |   |   |  |  | \\  |    | |__    |  |    | |    |  |    |  |___\n"
                    + "|_|             |_|    |_|   \\_|    |_|    |_|  |_|  \\_|    |____|  |_|    |_|    |_|    |____|\n"
                    + " ______    _        _    ______      _____     _______\n"
                    + "|   ____|   | |    | |   |  ___  |    |  ___|    |__    __|                 .-\"\"\"\"-.\n"
                    + "|  |   __    | |__|  |   | |    | |    | |___          | |                      / -   -  \\\n"
                    + "|  |  |_ |   |  __    |  | |     | |    |___  |        | |                      |  .-. .- |\n"
                    + "|  |__| |   |  |   |  |  | |___|  |    ___|  |        | |                     |  \\o| |o(\n"
                    + "|_____|    |_|   |_|   |______|    |____|        |_|                    \\     ^    \\\n"
                    + "                                                                                     |'.  )--'  /|\n"
                    + "             .-.                                                                   / / '-. .-'`\\ \\   \n"
                    + "           .'   `.                                                                / /'---` `---'\\ \\\n"
                    + "           :g g   :                                                               '.__.       .__.'\n"
                    + "           : o    `.                                                                   `|     |`\n"
                    + "           :         ``.                                                                |     \\\n"
                    + "           :             `.                                                             \\      '--.\n"
                    + "          :  :         .   `.                                                             '.        `\\\n"
                    + "          :   :          ` . `.                                                             '---.    |\n"
                    + "           `.. :            `. ``;                                                             ,__) /\n"
                    + "             `:;             `:'                                                                `..'\n"
                    + "               :              `.               \n"
                    + "                `.              `.....        \n"
                    + "                  `'`'`'`---..,___`;.-'         \n";


    //@@author cctt1014
    public Ui() {
        scanner = new Scanner(System. in);
        outputString = "";
        graphContainerString = "";
    }

    public String showLine() {
        return ("____________________________________________________________\n");
    }

    public void showLoadingError() {
        System.out.println("This is not a valid input from the file!!!");
    }

    public String showError(String message) {
        return ("ERROR: " + message);
    }

    public void appendToOutput(String msg) {
        outputString += msg;
    }

    public String getOutputString() {
        return outputString;
    }

    public void clearOutputString() {
        outputString = "";
    }

    //@@author therealnickcheong
    public String getGraphContainerString() {
        return graphContainerString;
    }

    public void clearGraphContainerString() {
        graphContainerString = "";
    }

    public void appendToGraphContainer(String msg) {
        graphContainerString += msg;
    }

    public String getLogo() {
        return display;
    }
}
