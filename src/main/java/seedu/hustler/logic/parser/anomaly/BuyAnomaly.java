package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;

/**
 * Detects anomalies in the buy command by the user.
 */
public class BuyAnomaly extends DetectAnomaly {

    /**
     * The message to output if user inputs an out of bounds index.
     */
    private final String OUT_OF_BOUNDS_MSG = "Please input the correct index! Recheck the shop size by typing \"/shop\"";

    /**
     * The message to output if user inputs the wrong format.
     */
    private final String WRONG_FORMAT_MSG = "Please input a proper format! Type \"/buy <index>\"";

    @Override
    public void detect(String[] userInput) throws CommandLineException {
        try {
           int index = Integer.parseInt(userInput[1]);
           if (index <= 0 || index > Hustler.shopList.size()) {
               throw new CommandLineException(OUT_OF_BOUNDS_MSG);
           }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new CommandLineException(WRONG_FORMAT_MSG);
        }
    }
}
