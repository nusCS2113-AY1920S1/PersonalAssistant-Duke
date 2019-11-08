package duke.parser;

import duke.view.CliView;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

/**
 * This is the parser for the first command.
 * @author danisheddie
 */
public class ParserCommand implements IParser {
    /**
     * Declaring type ManageStudentsParser.
     */
    private ParserManageStudents parserManageStudents
            = new ParserManageStudents();

    /**
     * Parse the respective command.
     *
     * @param input command.
     * @throws FileNotFoundException throws exception.
     */
    public void parseCommand(final String input) {
        try {
            CliView cliView = new CliView();
            Scanner sc = new Scanner(System.in);
            final String scheduleMenu = "1";
            switch (input) {
            case scheduleMenu:
                ParserSchedule parserSchedule = new ParserSchedule();
                parserSchedule.parseCommand();
                break;
            case "2":
                ParserManageStudents parserManageStudents
                        = new ParserManageStudents();
                cliView.manageStudentsHeading();
                //String studentsInput = sc.nextLine();
                parserManageStudents.parseCommand();
                break;
            case "3":
                ParserTrainingPlan parserTrainingPlan
                        = new ParserTrainingPlan();
                parserTrainingPlan.parseCommand("plan");
                break;
            default:
                System.out.println("OOPS!!! I'm sorry,"
                        + "but I don't know what that means :-(");
                break;
            }
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
