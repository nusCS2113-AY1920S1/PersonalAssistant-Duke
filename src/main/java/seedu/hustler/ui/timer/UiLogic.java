package seedu.hustler.ui.timer;

/**
 * A class with functions that help process outputs, making them more readable before printing.
 */
public class UiLogic {
    /**
     * Takes in the integers to be printed and pads them with '0's where applicable.
     * @param timeArray the hours, minutes and seconds to be printed.
     * @return the padded integers hours, minutes and seconds, to be printed.
     */
    public static String padOutput(int[] timeArray) {
        return ((timeArray[2] < 10 ? "0" : "") + timeArray[2] + "hrs "
                + (timeArray[1] < 10 ? "0" : "") + timeArray[1] + "min "
                + (timeArray[0] < 10 ? "0" : "") + timeArray[0] + "sec");
    }
}
