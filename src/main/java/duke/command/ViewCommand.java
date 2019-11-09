package duke.command;

import duke.models.Goal;
import duke.models.Lesson;
import duke.view.CliView;

import java.text.ParseException;

/**
 * Represents a subclass of Command class which is responsible
 * for the viewing of all types of items.
 */
public class ViewCommand {
    /**
     * The ui object responsible for showing things to the user.
     */
    private CliView cliView = new CliView();

    //@@author nottherealedmund
    /**
     * The method to view all goals of the day.
     */
    public void viewGoal(Goal goal, String goalDate) throws ParseException {
        goal.showGoalLine();
        System.out.print(goal.viewGoal(goalDate));
        goal.showGoalLine();
    }

    //@@author nottherealedmund
    /**
     * The method to view all lessons of the day.
     */
    public void viewLesson(Lesson lesson, String lessonDate) throws ParseException {
        lesson.showLessonLine();
        System.out.print(lesson.viewLesson(lessonDate));
        lesson.showLessonLine();
    }
}
