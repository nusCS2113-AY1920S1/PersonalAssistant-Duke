package duke.data;
import java.util.Scanner;

public class ParseCommand implements IParser {
    /**
     * Declaring type ManageStudentsParser.
     */
    private ManageStudentsParser manageStudentsParser = new ManageStudentsParser();

    /**
     * To parse the respective command.
     * @param input command.
     */
    public void parseCommand(final String input) {
        switch (input) {
            case "1":
                // Schedule
                break;
            case "2":
                //ManageStudents.ManageStudents
                System.out.println("Manage Students: ");
                Scanner sc = new Scanner(System.in);
                String nextInput = sc.nextLine();
                manageStudentsParser.parseCommand(nextInput);
                break;
            case "3":
                //Training Plan
                break;
                
            default:
                System.out.println("\u2639 OOPS!!! I'm sorry,"
                    + "but I don't know what that means :-(");
                break;
        }
    }
}
