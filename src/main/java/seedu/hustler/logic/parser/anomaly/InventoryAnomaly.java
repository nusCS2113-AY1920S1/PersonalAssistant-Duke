package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;

public class InventoryAnomaly extends DetectAnomaly{

    @Override
    public void detect(String[] userInput) throws CommandLineException {
        int index = 0;
        try {
            index = Integer.parseInt(userInput[1]);
            Hustler.inventory.get(index - 1);
            throw new CommandLineException("Please input a number here!");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CommandLineException("Please input a index that exist inside the shop list!");
        }
    }
}
