import java.util.Scanner;
import Data.Parser;

public class Ui {

    public void welcome() {
        System.out.println("Hello! I'm Duke\n" +
                "What can I do for you?");
    }

    public void goodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void readCommand(String input) {
        Parser parser = new Parser();
        parser.parseInput(input);
    }

}
