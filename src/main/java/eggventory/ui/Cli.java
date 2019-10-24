//@@author Raghav-B

package eggventory.ui;

import java.util.Scanner;

/**
 * Manages the CLI of Eggventory.
 * Prints intro and exit messages, and the standard newline.
 */
public class Cli extends Ui {

    private Scanner in;

    public Cli() {
        this.in = new Scanner(System.in);
    }

    /**
     * Starts the REPL loop.
     * @param runMethod Function passed in for REPL loop.
     */
    public void initialize(Runnable runMethod) {
        printIntro();

        while (true) {
            runMethod.run();
        }
    }

    /**
     * Reads input from stdio.
     * @return Returns String to be used by Parser in REPL loop.
     */
    public String read() {
        return in.nextLine();
    }

    /**
     * Primary function that handles printing to the CLI.
     *
     * @param printString String to print (passed in from external objects accessing UI)
     */
    public String print(String printString) {
        String output = printFormatter(printString);
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
}
