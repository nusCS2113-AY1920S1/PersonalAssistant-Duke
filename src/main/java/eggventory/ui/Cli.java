package eggventory.ui;

import java.util.Scanner;

/**
 * Manages the UI of Eggventory.
 * Prints intro and exit messages, and the standard newline.
 */

public class Cli {

    private Scanner in;

    public Cli() {
        this.in = new Scanner(System.in);
    }

    /**
     * Prints eggventory introduction message.
     */
    public void printIntro() {
        String logo = "  _      __    __                     __         ____         _   __         __               \n"
                + " | | /| / /__ / /______  __ _  ___   / /____    / __/__ ____ | | / /__ ___  / /____  ______ __\n"
                + " | |/ |/ / -_) / __/ _ \\/  ' \\/ -_) / __/ _ \\  / _// _ `/ _ `/ |/ / -_) _ \\/ __/ _ \\/"
                + " __/ // /\n"
                + " |__/|__/\\__/_/\\__/\\___/_/_/_/\\__/  \\__/\\___/ /___/\\_, /\\_, /|___/\\__/_//_/\\__/\\___/_/"
                + "  \\_, / \n"
                + "                                                  /___//___/                           /___/  \n";

        System.out.print(logo);
        print("Hello! I'm Humpty Dumpty\n" + "What can I do for you?");
    }

    /**
     * Primary function that handles printing to the CLI.
     *
     * @param printString String to print (passed in from external objects accessing UI)
     */
    public String print(String printString) {
        String output;
        output = addIndent() + addLine() + "\n";

        String[] linesToPrint = printString.split("\n", 0);
        for (int i = 0; i < linesToPrint.length; i++) {
            output += (addIndent() + linesToPrint[i]) + "\n";
        }
        output += addIndent() + addLine() + "\n";
        System.out.print(output);
        return output;
    }

    //    public PrintType printCommand(PrintType printType, String ... statement) {
    //        String output;
    //        switch(printType){
    //            case SUCCESS_ADD_COMMAND:
    //                output = (addIndent() + "Nice! I have successfully added the stock: StockType: " + statement[0]);
    //                System.out.println(output);
    //                return PrintType.SUCCESS_ADD_COMMAND;
    //            break;
    //            case FAIL_ADD_COMMAND:
    //                output = (addIndent() + "Sorry! There seems to be an error: StockType" + statement[0] );
    //                System.out.println(output);
    //                break;
    //            default:
    //                output = "Nothing done";
    //        }
    //    }

    /**
     * Prints error message to CLI.
     */
    public void printError(Exception e) {
        print("Parser error: \n" + e);
    }

    /**
     * Prints the EggVentory exit message.
     */
    public void printExitMessage() {
        print("Bye! Your stonks are safe with me!");
    }

    public String read() {
        return in.nextLine();
    }

    protected static String addIndent() {
        return "        ";
    }

    /**
     * Prints the standard newline.
     */
    protected String addLine() {
        return "____________________________________________________________";
    }
}
