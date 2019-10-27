package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.Hustler;

public class InventoryAnomaly extends DetectAnomaly{

    @Override
    public boolean detect(String[] userInput) {
        int index;
        try {
            index = Integer.parseInt(userInput[1]);
        } catch (NumberFormatException e) {
            System.out.println("Please input a number here!");
            return true;
        }
        try {
            Hustler.inventory.getList().get(index - 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please input a index that exist inside the shop list!");
            return true;
        }
        return false;
    }
}
