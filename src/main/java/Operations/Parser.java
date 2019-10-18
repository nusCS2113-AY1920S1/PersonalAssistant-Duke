package Operations;

import CustomExceptions.RoomShareException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import Enums.ExceptionType;
import Enums.ReplyType;
import Enums.TimeUnit;

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
        String command = scanner.next().toLowerCase().trim();
        return command;
    }

    public String getCommandLine() {
        String command = scanner.nextLine().toLowerCase().trim();
        return command;
    }

    /**
     * Returns the index number requested by the user for commands like 'snooze'
     * @return index Index the user wishes to perform operations on.
     */
    public Integer getIndex() {
        String temp = scanner.nextLine().trim();
        int index = Integer.parseInt(temp) - 1;
        return index;
    }

    /**
     * Return a single index number or a range of index number requested by users for command 'done' and 'delete'
     * @return a single index or a range of index
     */
    public int[] getIndexRange() {
        String temp[] = scanner.nextLine().trim().split("-",2);
        int[] index;
        if (temp.length == 1) {
            index = new int[]{Integer.parseInt(temp[0].trim()) - 1};
        }
        else
            index = new int[]{Integer.parseInt(temp[0].trim()) - 1, Integer.parseInt(temp[1].trim()) - 1};
        return index;
    }

    /**
     * Returns a Date object from a raw date that is stored as a String.
     * If the format of the input string is unacceptable, will throw a DukeException and will not return anything.
     * @param by Input String containing the date information.
     * @return A Date object containing the appropriately formatted date.
     * @throws RoomShareException If by is not in dd/MM/yyyy HH:mm format
     */
    public Date formatDate(String by) throws RoomShareException {
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(by);
        } catch (ParseException e) {
            throw new RoomShareException(ExceptionType.wrongFormat);
        }
    }

    /**
     * Returns the keyword to be searched for.
     * @return key A string of the keyword to be searched for
     */
    public String getKey() {
        String key = scanner.nextLine().trim();
        return key;
    }

    /**
     * Returns the amount of time the customer request to snooze
     * @return the amount of time the customer request to snooze
     */
    public int getAmount(){
        String temp = scanner.next().trim();
        return Integer.parseInt(temp);
    }

    /**
     * Returns the unit of time the customer request to snooze
     * @return the unit of time the customer request to snooze
     */
    public TimeUnit getTimeUnit(){
        String temp = scanner.next();
        return TimeUnit.valueOf(temp);
    }

    /**
     * Returns the index of the task and priority the user wants to set it to
     * @return the index and priority of the task the user wants to set
     */
    public String[] getPriority() {
        return scanner.nextLine().trim().split(" ", 2);
    }

}
