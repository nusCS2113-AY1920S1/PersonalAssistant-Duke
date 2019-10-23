package duke.Parser;
import duke.Ui;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class ParserCommand implements IParser {
    /**
     * Declaring type ManageStudentsParser.
     */
    private ParserManageStudents parserManageStudents = new ParserManageStudents();
    /**
     * Declaring ParserTrainingPLan type.
     */
    private ParserTrainingPlan parserTrainingPlan = new ParserTrainingPlan();

    /**
     * Parse the respective command.
     * @param input command.
     * @throws FileNotFoundException throws exception.
     */
    public void parseCommand(final String input) {
        try {
            Ui ui = new Ui();
            Scanner sc = new Scanner(System.in);
            switch (input) {
                case "1":
                    // Schedule
                    ui.trainingScheduleHeading();
                    break;
                case "2":
                    ui.manageStudentsHeading();
                    String studentsInput = sc.nextLine();
                    parserManageStudents.parseCommand(studentsInput);
                    break;
                case "3":
                    ui.trainingProgramHeading();
                    String trainingInput = sc.nextLine();
                    parserTrainingPlan.parseCommand(trainingInput);
                    break;

                default:
                    System.out.println("\u2639 OOPS!!! I'm sorry,"
                            + "but I don't know what that means :-(");
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
