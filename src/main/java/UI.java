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

}
