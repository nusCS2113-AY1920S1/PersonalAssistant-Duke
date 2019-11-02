package duke.parser;

import duke.command.AddCommand;
import duke.command.DeleteCommand;
import duke.command.ExitCommand;
import duke.command.ViewCommand;
import duke.view.CliView;
import duke.data.Storage;
import duke.models.Goal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class ParserGoal {
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
    private CliView cliView;
    /**
     * The scanner object responsible for taking in user input.
     */
    private Scanner myGoalScan;
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
     * Constructor for ParserGoal.
     *
     * @throws FileNotFoundException if file does not exist
     * @throws ParseException        if user input is not in the correct format
     */

    public ParserGoal() throws IOException, ParseException {
        cliView = new CliView();
        goalStorage = new Storage(
            ".\\src\\main\\java\\duke\\data\\goals.txt");
        goal = new Goal(goalStorage.loadGoal());
        myGoalScan = new Scanner(System.in);
    }

    //@@author nottherealedmund
    /**
     * Method to run when entering goal of the day.
     */
    public void runGoal() {
        cliView.showGoalPromptDate();
        String goalDate = myGoalScan.next();

        while (isRunning) {
            try {
                cliView.showGoalAllActions(goalDate);
                int executeType = myGoalScan.nextInt();
                myGoalScan.nextLine();  // This line you have
                // to add (It consumes the \n character)
                switch (executeType) {
                case 1:
                    viewCommand.viewGoal(goal, goalDate);
                    break;

                case 2:
                    addCommand.addGoal(goal, goalStorage, goalDate);
                    break;

                case indexThree:
                    deleteCommand.deleteGoal(goal, goalStorage, goalDate);
                    break;

                case indexFour:
                    deleteCommand.deleteAllGoals(goal, goalStorage, goalDate);
                    break;

                case indexFive:
                    cliView.showQuitGoal();
                    isRunning = exitCommand.exitGoal();
                    break;
                default:
                    cliView.showDontKnow();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                cliView.showFullCommand();
            } catch (ParseException e) {
                cliView.showCorrectFormat();
            }
        }
    }

}

