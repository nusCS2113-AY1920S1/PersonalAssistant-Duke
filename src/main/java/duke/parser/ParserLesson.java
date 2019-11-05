package duke.parser;

import duke.command.AddCommand;
import duke.command.DeleteCommand;
import duke.command.ExitCommand;
import duke.command.ViewCommand;
import duke.view.CliView;
import duke.data.Storage;
import duke.models.Lesson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class ParserLesson {
    /**
     * Constants to represent the index 3.
     */
    private final int indexThree = 3;
    /**
     * Constants to represent the index 4.
     */
    private final int indexFour = 4;
    /**
     * Constants to represent the index 5.
     */
    private final int indexFive = 5;
    /**
     * Constants to represent the index 6.
     */
    private final int indexSix = 6;
    /**
     * Constants to represent the index 7.
     */
    private final int indexSeven = 7;
    /**
     * Constants to represent the index 10.
     */
    private final int indexTen = 10;
    /**
     * Boolean status to check if the class can exit.
     */
    private boolean isRunning = true;
    /**
     * The lesson that has been loaded.
     */
    private Lesson lesson;
    /**
     * The storage of the lesson being accessed.
     */
    private Storage lessonStorage;
    /**
     * The ui object responsible for showing things to the user.
     */
    private CliView cliView;
    /**
     * The scanner object responsible for taking in user input.
     */
    private Scanner myLessonScan;
    /**
     * Represents the AddCommand class.
     */
    private AddCommand addCommand = new AddCommand();
    /**
     * Represents the DeleteCommand class.
     */
    private DeleteCommand deleteCommand = new DeleteCommand();
    /**
     * Represents the ViewCommand class.
     */
    private ViewCommand viewCommand = new ViewCommand();
    /**
     * Represents the ExitCommand class.
     */
    private ExitCommand exitCommand = new ExitCommand();
    //@@author nottherealedmund
    /**
     * Constructor for ParserLesson.
     *
     * @throws FileNotFoundException if file does not exist
     * @throws ParseException        if user input is not in the correct format
     */

    public ParserLesson() throws IOException, ParseException {
        cliView = new CliView();
        lessonStorage = new Storage(
            ".\\src\\main\\java\\duke\\data\\lessons.txt");
        lesson = new Lesson(lessonStorage.loadLesson());
        myLessonScan = new Scanner(System.in);
    }

    //@@author nottherealedmund
    /**
     * Method to run when entering lesson of the day.
     */
    public void runLesson() {
        cliView.showLessonPromptDate();
        String lessonDate = myLessonScan.next();

        while (isRunning) {
            try {
                cliView.showLessonAllActions(lessonDate);
                int executeType = myLessonScan.nextInt();
                myLessonScan.nextLine();  // This line you have
                // to add (It consumes the \n character)
                switch (executeType) {
                case 1:
                    viewCommand.viewLesson(lesson, lessonDate);
                    break;

                case 2:
                    addCommand.addLesson(lesson, lessonStorage, lessonDate);
                    break;

                case indexThree:
                    deleteCommand.deleteLesson(
                        lesson, lessonStorage, lessonDate);
                    break;

                case indexFour:
                    deleteCommand.deleteAllLessons(
                        lesson, lessonStorage, lessonDate);
                    break;

                case indexFive:
                    cliView.showQuitLesson();
                    isRunning = exitCommand.exitLesson();
                    break;
                default:
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                cliView.showFullCommand();
            } catch (ParseException e) {
                cliView.showCorrectFormat();
            }
        }
    }

}
