package duke.Parser;
import duke.Ui;

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
     * Parse the respective command.
     * @param input command.
     */
    public void parseCommand(final String input) {
        try {
            Ui ui = new Ui();
            Scanner sc = new Scanner(System.in);
            switch (input) {
                case "1":
                    final int returnLessonOption = 3;
                    final int returnMonthlyOption = 3;
                    // Schedule
                    ui.trainingScheduleHeading();
                    int executeType = sc.nextInt();
                    sc.nextLine();  // This line you have
                    // to add (It consumes the \n character)
                    if (executeType == 1) {
                        ui.dailyScheduleHeading();
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
                        ui.showCorrectFormat();
                    }
                    break;
                case "2":
                    ui.manageStudentsHeading();
                    String studentsInput = sc.nextLine();
                    parserManageStudents.parseCommand(studentsInput);
                    break;
                case "3":
                    final int maxTrainingPlanOptions = 3;
                    ui.trainingProgramHeading();
                    executeType = sc.nextInt();
                    sc.nextLine();
                    ParserTrainingPlan parserTrainingPlan = new
                            ParserTrainingPlan();
                    if (executeType == 1) {
                        ui.planListHeading();
                        parserTrainingPlan.parseCommand("plan view");
                    } else if (executeType == 2) {
                        ui.createPlanHeading();
                        String intensity = sc.nextLine();
                        parserTrainingPlan.parseCommand("plan new "
                                + intensity);
                    } else if (executeType == maxTrainingPlanOptions) {
                        ui.editPlanHeading();
                    } else {
                        ui.showCorrectPlanHeading();
                    }
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
