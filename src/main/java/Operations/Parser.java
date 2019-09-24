package Operations;

import CustomExceptions.DukeException;
import Enums.TimeType;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * A class for handling all parsing for Duke. Makes sure that inputs by the user
 * are properly formatted as parameters for other classes.
 */
public class Parser {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructor for the Parser object
     */
    public Parser() {
    }

    /**
     * Returns the command that the user has given Duke.
     * @return command The command given by the user to Duke.
     */
    public String getCommand() {
        String command = scanner.next().toLowerCase();
        return command;
    }

    /**
     * Returns the index number requested by the user for commands like 'done' and 'deleted'
     * @return index Index the user wishes to perform operations on.
     */
    public Integer getIndex() {
        String temp = scanner.next().trim();
        int index = Integer.parseInt(temp);
        return index;
    }

    /**
     * Returns the description the user inputs for tasks. Will not accept empty descriptions
     * @return temp The description that the user has specified for the task. Cannot be null.
     * @throws DukeException If temp is empty.
     */
    public String getDescription() throws DukeException {
        String temp = scanner.nextLine().trim();
        if (temp.equals("")) {
            throw new DukeException();
        }
        return temp;
    }

    /**
     * Returns an array of strings that stores the raw description and date Strings to be stored in Duke.
     * @return array An array of String containing the description and date information. Index 0 stores the description,
     *               index 1 stores the date String.
     */
    public String[] getDescriptionWithDate() {
        String[] array = scanner.nextLine().trim().split("/", 2);
        return array;
    }

    /**
     * Returns a Date object from a raw date that is stored as a String.
     * If the format of the input string is unacceptable, will throw a DukeException and will not return anything.
     * @param by Input String containing the date information.
     * @return A Date object containing the appropriately formatted date.
     * @throws DukeException If by is not in dd/MM/yyyy HH:mm format
     */
    public Date formatDate(String by) throws DukeException{
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(by);
        } catch (ParseException e) {
            throw new DukeException();
        }
    }

    /**
     * Returns the keyword to be searched for.
     * @return key A string of the keyword to be searched for
     */
    public String getKey() {
        String key = scanner.nextLine().toLowerCase();
        return key;
    }

    /**
     * Returns the amount of time
     * @return
     */
    public int getAmount(){
        String temp = scanner.next().trim();
        return Integer.parseInt(temp);
    }

    public TimeType getTimeType(){
        String temp = scanner.next();
        return TimeType.valueOf(temp);
    }
}
