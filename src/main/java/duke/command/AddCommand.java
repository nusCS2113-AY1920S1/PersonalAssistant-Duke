package duke.command;

import duke.data.Storage;
import duke.models.Goal;
import duke.models.Lesson;
import duke.view.CliView;

import java.text.ParseException;
import java.util.Scanner;

/**
 * Represents a subclass of Command class which is responsible
 * for the adding of all types of items.
 */
public class AddCommand {
    /**
     * The ui object responsible for showing things to the user.
     */
    private CliView cliView = new CliView();

    /**
     * The scanner object responsible for taking in user input.
     */
    private Scanner addScan = new Scanner(System.in);

    //@@author nottherealedmund
    /**
     * The method to add a Goal of the day.
     */
    public void addGoal(Goal goal, Storage goalStorage, String goalDate) throws ParseException {
        cliView.showGoalPromptAddGoal(goalDate);
        String myGoal = addScan.nextLine();
        goal.showGoalLine();
        System.out.println(
            goal.addGoal(
                goalDate, myGoal, goalStorage));
        goal.showGoalLine();
    }

    //@@author nottherealedmund
    /**
     * The method to add a Lesson of the day.
     */
    public void addLesson(Lesson lesson, Storage lessonStorage, String lessonDate) throws ParseException {
        cliView.showLessonPromptAddLesson(lessonDate);
        String myLesson = addScan.nextLine();
        lesson.showLessonLine();
        System.out.println(
            lesson.addLesson(
                lessonDate, myLesson, lessonStorage));
        lesson.showLessonLine();
    }

}
