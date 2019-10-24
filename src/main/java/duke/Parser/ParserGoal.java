package duke.Parser;

import duke.Ui;
import duke.data.Storage;
import duke.module.Goal;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

public class ParserGoal {
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
     * The goal that has been loaded.
     */
    private Goal goal;
    /**
     * The storage of the goal being accessed.
     */
    private Storage goalStorage;
    /**
     * The ui object responsible for showing things to the user.
     */
    private Ui ui;
    /**
     * The scanner object responsible for taking in user input.
     */
    private Scanner myGoalScan;
    /**
     * Constructor for ParserGoal.
     *
     * @throws FileNotFoundException
     * @throws ParseException
     */
    public ParserGoal() throws FileNotFoundException, ParseException {
        ui = new Ui();
        goalStorage = new Storage(
            ".\\src\\main\\java\\duke\\data\\goals.txt");
        goal = new Goal(goalStorage.loadGoal());
        myGoalScan = new Scanner(System.in);
    }

    public void runGoal() {
        ui.showGoalPromptDate();
        String goalDate = myGoalScan.next();

        while (isRunning) {
            try {
                ui.showGoalAllActions(goalDate);
                int executeType = myGoalScan.nextInt();
                myGoalScan.nextLine();  // This line you have
                // to add (It consumes the \n character)
                switch (executeType) {
                case 1:
                    System.out.print(goal.viewGoal(goalDate));
                    break;

                case 2:
                    ui.showGoalPromptAddGoal(goalDate);
                    String myGoal = myGoalScan.nextLine();
                    System.out.println(
                        goal.addGoal(
                            goalDate, myGoal, goalStorage));
                    break;

                case INDEX_THREE:
                    ui.showGoalPromptDeleteGoal(goalDate);
                    String message = myGoalScan.nextLine();
                    System.out.println(
                        goal.removeGoal(
                            goalDate, message, goalStorage));
                    break;

                case INDEX_FOUR:
                    System.out.println(goal.removeAllGoal(
                        goalDate, goalStorage));
                    break;

                case INDEX_FIVE:
                    isRunning = false;
                    ui.showQuitGoal();
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

