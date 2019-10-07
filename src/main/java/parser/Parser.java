package parser;

import dolla.Time;
import dolla.Ui;
import dolla.command.AddDeadlineCommand;
import dolla.command.AddEventCommand;
import dolla.command.Command;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public abstract class Parser {

    protected LocalDateTime date;
    protected String description;
    protected String inputLine;

    public Parser(String inputLine) {
        this.inputLine = inputLine;
    }

    public abstract Command handleInput(String inputLine);

    /**
     * Returns true if the method runs without running into any error.
     * <p>
     *     This method splits and correctly assigns the task description and time from the given input.
     * </p>
     * <p>
     *     If the incorrect format is given in the input, the corresponding alert will be printed, and
     *     the method will then return false.
     * </p>
     * @return true if method runs successfully.
     * @see AddDeadlineCommand
     * @see AddEventCommand
     */
    public void splitDescTime() throws Exception {
        String[] data = inputLine.split(" /on "); // data[0] os description, data[1] is the time
        String dateString = (data[1].split("/tag"))[0];
        try {
            date = Time.readDateTime(dateString);
            description = data[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            Ui.printMsg("Please add '/at <date>' after your task to specify the entry date.");
            throw new Exception("missing date");
        }  catch (DateTimeParseException e) {
            Ui.printDateTimeFormatError();
            throw new Exception("invalid date");
        }
    }

    /**
     * Returns a double variable from the specified string.
     * <p>
     *     Returns 0 if the specified string is not of a number.
     * </p>
     * <p>
     *     Mainly used for using the specified string for calculations in the command.
     *     IE. Accessing arrays.
     * </p>
     * @param str String (of number) to be converted into integer type.
     * @return Integer type of the specified string.
     */
    public double stringToDouble(String str) {
        double newDouble = 0.0;
        try {
            newDouble = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            Ui.printInvalidNumberError(str);
        }
        return newDouble;
    }
}
