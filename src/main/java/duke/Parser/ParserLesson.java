package duke.Parser;

import duke.Ui;
import duke.data.Storage;
import duke.module.Lesson;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

public class ParserLesson {
    /**
     * Constants to represent the index 3.
     */
    private final int INDEX_THREE = 3;
    /**
     * Constants to represent the index 4.
     */
    private final int INDEX_FOUR = 4;
    /**
     * Constants to represent the index 5.
     */
    private final int INDEX_FIVE = 5;
    /**
     * Constants to represent the index 6.
     */
    private final int INDEX_SIX = 6;
    /**
     * Constants to represent the index 7.
     */
    private final int INDEX_SEVEN = 7;
    /**
     * Constants to represent the index 10.
     */
    private final int INDEX_TEN = 10;
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
    private Ui ui;
    /**
     * The scanner object responsible for taking in user input.
     */
    private Scanner myLessonScan;
    /**
     * Constructor for ParserLesson.
     *
     * @throws FileNotFoundException
     * @throws ParseException
     */
    public ParserLesson() throws FileNotFoundException, ParseException {
        ui = new Ui();
        lessonStorage = new Storage(
            ".\\src\\main\\java\\duke\\data\\lessons.txt");
        lesson = new Lesson(lessonStorage.loadLesson());
        myLessonScan = new Scanner(System.in);
    }

    public void runLesson() {
        ui.showLessonPromptDate();
        String lessonDate = myLessonScan.next();

        while (isRunning) {
            try {
                ui.showLessonAllActions(lessonDate);
                int executeType = myLessonScan.nextInt();
                myLessonScan.nextLine();  // This line you have
                // to add (It consumes the \n character)
                switch (executeType) {
                case 1:
                    System.out.print(lesson.viewLesson(lessonDate));
                    break;

                case 2:
                    ui.showLessonPromptAddLesson(lessonDate);
                    String myLesson = myLessonScan.nextLine();
                    System.out.println(
                        lesson.addLesson(
                            lessonDate, myLesson, lessonStorage));
                    break;

                case INDEX_THREE:
                    ui.showLessonPromptDeleteLesson(lessonDate);
                    String message = myLessonScan.nextLine();
                    System.out.println(
                        lesson.removeLesson(
                            lessonDate, message, lessonStorage));
                    break;

                case INDEX_FOUR:
                    System.out.println(lesson.removeAllLesson(
                        lessonDate, lessonStorage));
                    break;

                case INDEX_FIVE:
                    isRunning = false;
                    ui.showQuitLesson();
                default:
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                ui.showFullCommand();
            } catch (ParseException e) {
                ui.showCorrectFormat();
            }
        }
    }

}
