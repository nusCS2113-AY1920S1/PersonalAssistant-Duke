package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;

public class BuyAnomaly extends DetectAnomaly {

    private final String OUT_OF_BOUNDS_MSG = "Please input the correct index! Recheck the shop size by typing \"/shop\"";
    private final String WRONG_FORMAT_MSG = "Please input a proper format! Type \"/buy <index>\"";

    @Override
    public boolean detect(String[] userInput) throws CommandLineException {
        try {
           int index = Integer.parseInt(userInput[1]);
           if (index <= 0 || index > Hustler.shopList.size()) {
               throw new CommandLineException(OUT_OF_BOUNDS_MSG);
           }
        } catch (NumberFormatException e) {
            throw new CommandLineException(WRONG_FORMAT_MSG);
        }
        return false;
    }
}
