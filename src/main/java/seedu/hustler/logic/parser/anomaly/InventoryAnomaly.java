package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;

public class InventoryAnomaly extends DetectAnomaly{

    @Override
    public void detect(String[] userInput) throws CommandLineException {
        int index = 0;
        try {
            index = Integer.parseInt(userInput[1]);
            index--;
            if(index >= Hustler.inventory.getSize() || index < 0) {
                throw new CommandLineException("Please input a index that exist inside the shop list!");
            }
        } catch (NumberFormatException e) {
            throw new CommandLineException("Please input a number here!");
        }
    }
}
