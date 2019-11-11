package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;

public class InventoryAnomaly extends DetectAnomaly {

    private static final String NO_INDEX_MESSAGE = "Please do not leave the index field empty."
        + "Retype \"/equip <index>\" instead.";
    private static final String NOT_NUMBER_MESSAGE = "Please input a proper number. Retype"
        + "\"equip <index>\" instead.";
    private static final String OUT_OF_BOUNDS_MESSAGE = "Please input an index that exists inside"
        + "your inventory." + "Check your items by typing \"/inventory\".";

    @Override
    public void detect(String[] userInput) throws CommandLineException {
        int index = 0;
        if (userInput.length == 1) {
            throw new CommandLineException(NO_INDEX_MESSAGE);
        }
        try {
            index = Integer.parseInt(userInput[1]);
            index--;
            if (index >= Hustler.inventory.getSize() || index < 0) {
                throw new CommandLineException(OUT_OF_BOUNDS_MESSAGE);
            }
        } catch (NumberFormatException e) {
            throw new CommandLineException(NOT_NUMBER_MESSAGE);
        }
    }
}
