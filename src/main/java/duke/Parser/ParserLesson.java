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
     * Constructor for ParserLesson.
     *
     * @throws FileNotFoundException
     * @throws ParseException
     */
    public ParserLesson() throws FileNotFoundException, ParseException {
        Ui ui = new Ui();
        lessonStorage = new Storage(
            ".\\src\\main\\java\\duke\\data\\lessons.txt");
        lesson = new Lesson(lessonStorage.loadLesson());
        ui.lessonHeading(lesson);
    }

    public void runLesson() {
        Scanner myLessonScan = new Scanner(System.in);
        System.out.println("Please enter the date of the day "
            + "in this format: dd/MM/yyyy");

        String lessonDate = myLessonScan.next();

        while (isRunning) {
            try {
                int executeType = myLessonScan.nextInt();
                myLessonScan.nextLine();  // This line you have
                // to add (It consumes the \n character)
                switch (executeType) {
                case 1:
                    System.out.print(lesson.viewLesson(lessonDate));
                    break;

                case 2:
                    System.out.println("To add a lesson to "
                        + lessonDate + ", enter the lesson.");
                    String myLesson = myLessonScan.nextLine();
                    System.out.println(
                        lesson.addLesson(
                            lessonDate, myLesson, lessonStorage));
                    break;

                case INDEX_THREE:
                    System.out.println("To delete a lesson from "
                        + lessonDate + ", enter the lesson.");
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
                    System.out.println(
                        "You have quit the lesson of the day.");

                default:
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Please enter the full command.");
            } catch (ParseException e) {
                System.out.println(
                    "Please enter the details in the correct format.");
            }
        }
    }

}
