import java.util.Scanner;

public class UI {

    public UI() { //initialization

    }

    public static void separator() {
        System.out.println("____________________________________________________________");
    }

    public static String inputCommand() { //read input and returns that input to be processed in main
        Scanner input = new Scanner(System.in);

        return input.nextLine();
    }

    public static void byeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
        UI.separator();
    }

    public void welcomeMessage() {

        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        System.out.println("Hello from\n" + logo);
        UI.separator();
        System.out.println("Hello! I'm Duke"); //introduction
        System.out.println("What can I do for you?");
        UI.separator();

    }

}
