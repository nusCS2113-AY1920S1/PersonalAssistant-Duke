package duke.Parser;
import duke.Ui;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

public class ParserCommand implements IParser {
    /**
     * Declaring type ManageStudentsParser.
     */
    private ParserManageStudents parserManageStudents = new ParserManageStudents();

    /**
     * To parse the respective command.
     * @param input command.
     */
    public void parseCommand(final String input) throws FileNotFoundException, ParseException {
        Ui ui = new Ui();
        switch (input) {
            case "1":
                // Schedule
                ui.trainingScheduleHeading();
                ParserLesson parserLesson = new ParserLesson();
                parserLesson.runLesson();
                break;
            case "2":
                //ManageStudents.ManageStudents
                ui.manageStudentsHeading();
                Scanner sc = new Scanner(System.in);
                String nextInput = sc.nextLine();
                parserManageStudents.parseCommand(nextInput);
                break;
            case "3":
                ui.trainingProgramHeading();
                //Training Plan
                break;

            default:
                System.out.println("\u2639 OOPS!!! I'm sorry,"
                    + "but I don't know what that means :-(");
                break;
        }
    }
}
