package duke.parser;

import duke.view.CliView;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;


public class ParserCommand implements IParser {

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
