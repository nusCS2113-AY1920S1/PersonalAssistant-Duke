import java.util.Scanner;

public class Ui {
    private static final String horizontalLine = "\t____________________________________________________________";

    /**
     * print out Duke logo and welcome message.
     */
    public static void welcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(logo);
        print("Hello! I'm Duke\nWhat can I do for you?");
    }

    /***<p>
     * This is a method to print the message Duke said, automatically add
     * horizontal lines and indentations to the original message and print them.
     * A sample output is:
     *     ____________________________________________________________
     *      Hello! I'm Duke
     *      What can I do for you?
     *     ____________________________________________________________</p>
     * @param toPrint the message need to be printed
     */
    public static void print(String toPrint) {
        System.out.println(horizontalLine);
        toPrint = "\t" + toPrint;
        for (int i = 0;i < toPrint.length();i++) {
            if (toPrint.charAt(i) == '\n') {
                toPrint = toPrint.substring(0, i + 1) + "\t" + toPrint.substring(i + 1, toPrint.length());
            }
        }
        System.out.println(toPrint);
        System.out.println(horizontalLine);
    }

    public static String readLine(Scanner in) {
        return in.nextLine();
    }
}
