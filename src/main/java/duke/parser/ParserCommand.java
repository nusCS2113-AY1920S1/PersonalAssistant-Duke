package duke.parser;
import duke.CLIView;

import java.io.FileNotFoundException;

import java.text.ParseException;
import java.util.Scanner;

public class ParserCommand implements IParser {
    /**
     * Declaring type ManageStudentsParser.
     */
    private ParserManageStudents parserManageStudents
        = new ParserManageStudents();
    /**
     * Declaring ParserTrainingPLan type.
     */
    private ParserTrainingPlan parserTrainingPlan
        = new ParserTrainingPlan();

    /**
     * Parse the respective command.
     * @param input command.
     * @throws FileNotFoundException throws exception.
     */
    public void parseCommand(final String input) {
        try {
            CLIView cliView = new CLIView();
            Scanner sc = new Scanner(System.in);
            switch (input) {
            case "1":
                final int returnLessonOption = 3;
                final int returnMonthlyOption = 3;
                final int theNumberThree = 3;
                // Schedule
                cliView.trainingScheduleHeading();
                int executeType = sc.nextInt();
                sc.nextLine();  // This line you have
                // to add (It consumes the \n character)
                if (executeType == 1) {
                    cliView.dailyScheduleHeading();
                    int dailyType = sc.nextInt();
                    sc.nextLine();  // This line you have
                    // to add (It consumes the \n character)
                    if (dailyType == 1) {
                        ParserSchedule parserSchedule =
                            new ParserSchedule();
                        parserSchedule.dailySchedule();
                    } else if (dailyType == 2) {
                        ParserGoal parserGoal = new ParserGoal();
                        parserGoal.runGoal();
                    } else if (dailyType == returnLessonOption) {
                        ParserLesson parserLesson = new ParserLesson();
                        parserLesson.runLesson();
                    }
                } else if (executeType == 2) {
                    ParserSchedule parserSchedule = new ParserSchedule();
                    parserSchedule.weeklySchedule();
                } else if (executeType == returnMonthlyOption) {
                    ParserSchedule parserSchedule = new ParserSchedule();
                    parserSchedule.monthlySchedule();
                } else {
                    cliView.showCorrectFormat();
                }
                break;
            case "2":
                cliView.manageStudentsHeading();
                String studentsInput = sc.nextLine();
                parserManageStudents.parseCommand(studentsInput);
                break;
            case "3":
                cliView.trainingProgramHeading();
                String trainingInput = sc.nextLine();
                parserTrainingPlan.parseCommand(trainingInput);
                break;
            default:
                System.out.println("\u2639 OOPS!!! I'm sorry,"
                        + "but I don't know what that means :-(");
                break;
            }
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }
}
