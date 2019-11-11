package rims.core;

import rims.command.Command;
import rims.command.AddCommand;
import rims.command.ReserveCommand;
import rims.command.DeleteCommand;
import rims.command.CalendarCommand;
import rims.command.CloseCommand;
import rims.command.HelpCommand;
import rims.command.ListCommand;
import rims.command.ReturnCommand;
import rims.command.StatsCommand;
import rims.command.UndoCommand;
import rims.command.TagCommand;
import rims.command.ViewDeadlinesCommand;

import rims.exception.RimsException;

import rims.resource.Resource;
import rims.resource.Reservation;
import rims.resource.ReservationList;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.text.DateFormat;

//@@author rabhijit
/**
 * This class takes in a String of input from the Ui, and depending on the
 * content of the input, parses it into a unique executable command that will
 * carry out the tasks required for that input.
 */
public class Parser {
    Ui ui;
    ResourceList resources;
    Command prevCommand;

    /**
     * Constructor for the Parser.
     *
     * @param ui        An instance of the user interface class.
     * @param resources An instance of the resource list.
     */
    public Parser(Ui ui, ResourceList resources) {
        this.ui = ui;
        this.resources = resources;
    }

    //@@author hin1
    /**
     * Saves the last executed command that modified data
     * in Parser.
     * @param c Previous command that modified data in ResourceList.
     */
    public void setPrevCommand(Command c) {
        if (c.canModifyData()) {
            prevCommand = c; 
        }
    }

    //@@author rabhijit
    /**
     * Converts a 'natural date' (just a day and date) into a String version of a
     * date, in the format DD/MM/YYYY HHmm by finding the next date of the requested
     * day.
     *
     * @param day  the day whose next date is to be obtained.
     * @param time the time to be appended to the date obtained above.
     * @return a String version of the requested date, in DD/MM/YYYY HHmm format.
     * @throws RimsException if the day requested is not one of the 7 days of the
     *                       week.
     */
    public String convertNaturalDate(String day, String time) throws RimsException {
        Date todayDate = new Date(System.currentTimeMillis());
        String stringDate = null;
        boolean validDay = false;
        for (int i = 0; i < 8; i++) {
            if (new SimpleDateFormat("EEEEE").format(todayDate).equals(day)
                && !(i == 0 && (new SimpleDateFormat("HHmm").format(todayDate).compareTo(time) > 0))) {
                validDay = true;
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy HHmm");
                stringDate = format.format(todayDate);
                stringDate = stringDate.substring(0, stringDate.length() - 4);
                stringDate += time;
                break;
            } else {
                Calendar cal = Calendar.getInstance();
                cal.setTime(todayDate);
                cal.add(Calendar.DATE, 1);
                todayDate = cal.getTime();
            }
        }
        if (!validDay || stringDate == null) {
            throw new RimsException("Please enter a valid day / time.");
        } else {
            return stringDate;
        }
    }

    /**
     * Checks if an entered date is in the required format. Throws a DukeException otherwise.
     * @param date the date entered by the user
     * @throws RimsException if the format of the entered date is invalid.
     */
    protected void checkParsableDate(String date) throws RimsException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
            formatter.setLenient(false);
            Date dateValue = formatter.parse(date);
        } catch (ParseException e) {
            throw new RimsException("Please specify a valid date using the following format: dd/MM/yyyy HHmm");
        }
    }

    /**
     * Checks if a date, in String format, entered by the user is parsable into a Date object.
     * @param date the String inputted by the user.
     * @return the same String inputted by the user, if it is indeed parsable into a Date object.
     * @throws RimsException if the String is not parsable into a Date object.
     */
    public String parseDate(String date) throws RimsException {
        if (date.length() < 15) {
            if (!(date.contains(" "))) {
                throw new RimsException("Please enter a valid day / time.");
            }
            String[] splitDate = date.split(" "); // check for error
            date = convertNaturalDate(splitDate[0], splitDate[1]);
            return date;
        }
        checkParsableDate(date);
        return date;

    }

    /**
     * Checks if a String inputted by the user is a valid positive integer.
     * @param input the String inputted by the user.
     * @return the integer conversion of the String, if it does represent a valid positive integer.
     * @throws RimsException if the String does not represent a valid positive integer.
     */
    public int parseInt(String input) throws RimsException {
        try {
            int parsedInt = Integer.parseInt(input);
            if (parsedInt < 0) {
                throw new RimsException("Please specify an integer value that is valid & non-negative!");
            }
            return parsedInt;
        } catch (NumberFormatException e) {
            throw new RimsException("Please specify an integer value that is valid & non-negative!");
        }
    }

    /**
     * Parses the input obtained by the Ui from the user into an executable command.
     *
     * @param input the input obtained from the user by the Ui.
     * @return a Command that can be executed to carry out the necessary tasks
     * @throws RimsException if the input is in a wrong format or does not make
     *                       sense.
     */
    public Command parseInput(String input) throws RimsException, ParseException {
        input = input.trim();
        String[] words = input.split(" ");
        Command c;

        if (input.equals("bye") && words.length == 1) {
            c = new CloseCommand();
        } else if (input.equals("list") && words.length == 1) {
            c = new ListCommand();
        } else if (words[0].equals("list") && words.length > 1) {
            c = listParser(input, words);
        //@@author aarushisingh1
        } else if (input.equals("deadlines") && words.length == 1) {
            c = new ViewDeadlinesCommand();
        //@@author rabhijit
        } else if (input.equals("help") && words.length == 1) {
            c = new HelpCommand();
        } else if (input.equals("calendar")) {
            c = new CalendarCommand(resources, ui);
        } else if (input.equals("calendar+")) {
            c = new CalendarCommand(resources, ui, "+");
        } else if (input.equals("calendar-")) {
            c = new CalendarCommand(resources, ui, "-");
        } else if (words[0].equals("add")) {
            c = addParser(input, words);
        } else if (words[0].equals("delete")) {
            c = deleteParser(input, words);
        } else if (words[0].equals("loan")) {
            c = loanParser(input, words);
        } else if (words[0].equals("reserve")) {
            c = reserveParser(input, words);
        } else if (words[0].equals("return") && input.contains(" /id")) {
            c = returnParser(input, words);
        } else if (input.equals("undo")) {
            c = new UndoCommand(prevCommand);
        } else if ((words[0].equals("stats") && words.length > 1)) {
            c = statsParser(input, words);
        } else if(words[0].equals("tag")){
            c = tagParser(input, words);
        }else {
            throw new RimsException("Please enter a recognizable command!");
        }
        return c;
    }

    /**
     * Parses user input into the parameters for a ListCommand.
     * @param input the input obtained from the user by the Ui.
     * @param words the input from the user, delimited by spaces into an array of individual words.
     * @return a ListCommand, if the input can be parsed into the required parameters.
     * @throws RimsException if the input cannot be parsed into the required parameters for a ListCommand.
     */
    protected Command listParser(String input, String[] words) throws RimsException {
        int paramTypeIndex = input.indexOf("/");
        int paramIndex = paramTypeIndex + 5;
        if (paramTypeIndex == -1 || paramIndex > input.length() || paramIndex + 1 > input.length()) {
            throw new RimsException("Please specify the parameter you want to view a detailed list of.");
        }
        String paramType = input.substring(paramTypeIndex + 1, paramIndex);
        if (!(paramType.equals("date") || paramType.equals("room") || paramType.equals("item") || paramType.equals("tag "))) {
            throw new RimsException("Invalid list parameter! Please specify '/date', '/room' "
                    + "'/item', or 'tag' to view a detailed list.");
        }
        String param = input.substring(paramIndex + 1).trim();
        if (paramType.equals("date")) {
            parseDate(param);
        }
        return new ListCommand(paramType, param);
    }

    /**
     * Parses user input into the parameters for an AddCommand.
     * @param input the input obtained from the user by the Ui.
     * @param words the input from the user, delimited by spaces into an array of individual words.
     * @return an AddCommand, if the input can be parsed into the required parameters.
     * @throws RimsException if the input cannot be parsed into the required parameters for an AddCommand.
     */
    protected Command addParser(String input, String[] words) throws RimsException {
        if (!(words.length > 1)) {
            throw new RimsException("Please specify the resource to add to your inventory.");
        }
        if (words[1].equals("/item")) {
            int itemIndex = input.indexOf("/item") + 6;
            int qtyIndex = input.indexOf(" /qty");
            if (itemIndex > input.length()) {
                throw new RimsException("Please specify the name of the item to add to your inventory.");
            }
            if (qtyIndex == -1) {
                throw new RimsException("Please specify the quantity of item to add to your inventory.");
            }
            if (itemIndex > qtyIndex) {
                throw new RimsException("Please specify the item to add to your inventory.");
            }
            String item = input.substring(itemIndex, qtyIndex);
            if (item.trim().isEmpty()) {
                throw new RimsException("Please specify the item to add to your inventory.");
            }
            String itemname = item.trim();
            char[] charArray = itemname.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                if (charArray[i] == ',') {
                    throw new RimsException("Please do not enter ',' in your input!");
                }
            }
            int qty = parseInt(input.replaceFirst("add /item " + item + " /qty ", "").trim());
            if (qty == 0) {
                throw new RimsException("Please use a valid integer value above zero!");
            }
            if (qty > 100) {
                throw new RimsException("You can only add up to 100 items at a time!");
            }
            return new AddCommand(itemname, qty);
        } else if (words[1].equals("/room")) {
            if (input.contains("/qty")) {
                throw new RimsException("Rooms do not require quantity!");
            }
            int roomIndex = input.indexOf("/room") + 6;
            if (roomIndex > input.length()) {
                throw new RimsException("Please specify the room to add to your inventory.");
            }
            String room = input.substring(roomIndex);
            return new AddCommand(room.trim());
        } else {
            throw new RimsException("Please choose a room or item to add to your inventory.");
        }
    }

    /**
     * Parses user input into the parameters for a DeleteCommand.
     * @param input the input obtained from the user by the Ui.
     * @param words the input from the user, delimited by spaces into an array of individual words.
     * @return a DeleteCommand, if the input can be parsed into the required parameters.
     * @throws RimsException if the input cannot be parsed into the required parameters for a DeleteCommand.
     */
    protected Command deleteParser(String input, String[] words) throws RimsException {
        if (!(words.length > 1)) {
            throw new RimsException("Please specify the resource to delete from your inventory.");
        }
        if (words[1].equals("/item")) {
            int itemIndex = input.indexOf("/item") + 6;
            if (itemIndex > input.length()) {
                throw new RimsException("Please specify the item to delete from your inventory.");
            }
            String itemName = input.substring(itemIndex);
            return new DeleteCommand(itemName.trim(), "item");
        } else if (words[1].equals("/room")) {
            int roomIndex = input.indexOf("/room") + 6;
            if (roomIndex > input.length()) {
                throw new RimsException("Please specify the room to delete from your inventory.");
            }
            String roomName = input.substring(roomIndex).trim();
            return new DeleteCommand(roomName, "room");
        } else {
            throw new RimsException("Please choose a room or item to delete from your inventory.");
        }
    }

    /**
     * Parses user input into the parameters for a LoanCommand.
     * @param input the input obtained from the user by the Ui.
     * @param words the input from the user, delimited by spaces into an array of individual words.
     * @return a LoanCommand, if the input can be parsed into the required parameters.
     * @throws RimsException if the input cannot be parsed into the required parameters for a LoanCommand.
     */
    protected Command loanParser(String input, String[] words) throws RimsException {
        if (!(words.length > 1)) {
            throw new RimsException("Please specify the resource to be loaned out.");
        }
        if (words[1].equals("/item")) {
            int itemIndex = input.indexOf("/item") + 6;
            int qtyIndex = input.indexOf(" /qty");
            if (qtyIndex == -1) {
                throw new RimsException("Please specify the quantity of item to be loaned out.");
            }
            if (itemIndex > qtyIndex) {
                throw new RimsException("Please specify the name of the item to be loaned out.");
            }
            String itemName = input.substring(itemIndex, qtyIndex).trim();
            if (itemName.isEmpty()) {
                throw new RimsException("Please specify the name of the item to be loaned out.");
            }
            if (!resources.isItem(itemName)) {
                throw new RimsException("This item does not exist in your inventory!");
            }
            int idIndex = input.indexOf(" /id");
            if (idIndex == -1) {
                throw new RimsException("Please specify the ID of the borrower of this item.");
            }
            if (qtyIndex + 6 > idIndex) {
                throw new RimsException("Please specify the quantity of this item to be loaned out.");
            }
            int qty = parseInt(input.substring(qtyIndex + 6, idIndex).trim());
            if (qty == 0) {
                throw new RimsException("Please specify a valid integer value above zero!");
            }
            int byIndex = input.indexOf(" /by");
            if (byIndex == -1) {
                throw new RimsException("Please specify the date by which the item is to be returned.");
            }
            int userId = parseInt(input.substring(idIndex + 5, byIndex).trim());
            String dateTill = parseDate(input.substring(byIndex + 5).trim());
            return new ReserveCommand(itemName, qty, dateTill, userId);
        } else if (words[1].equals("/room")) {
            int roomIndex = input.indexOf("/room") + 6;
            int idIndex = input.indexOf(" /id");
            if (idIndex == -1) {
                throw new RimsException("Please specify the ID of the borrower of this room.");
            }
            if (roomIndex > idIndex) {
                throw new RimsException("Please specify the name of the room to be loaned out.");
            }
            String roomName = input.substring(roomIndex, idIndex).trim();
            if (roomName.isEmpty()) {
                throw new RimsException("Please specify the name of the room to be loaned out.");
            }
            if (!resources.isRoom(roomName)) {
                throw new RimsException("This room does not exist in your inventory!");
            }
            int byIndex = input.indexOf(" /by");
            if (byIndex == -1) {
                throw new RimsException("Please specify the date by which the room is to be returned.");
            }
            if (idIndex + 5 > byIndex) {
                throw new RimsException("Please specify the ID of the borrower of this room.");
            }
            int userId = parseInt(input.substring(idIndex + 5, byIndex).trim());
            if (byIndex + 5 > input.length()) {
                throw new RimsException("Please specify the date by which the room is to be returned.");
            }
            String dateTill = parseDate(input.substring(byIndex + 5).trim());
            return new ReserveCommand(roomName, dateTill, userId);
        } else {
            throw new RimsException("Please choose an item or room to loan out.");
        }
    }

    /**
     * Parses user input into the parameters for a ReserveCommand.
     * @param input the input obtained from the user by the Ui.
     * @param words the input from the user, delimited by spaces into an array of individual words.
     * @return a ReserveCommand, if the input can be parsed into the required parameters.
     * @throws RimsException if the input cannot be parsed into the required parameters for a ReserveCommand.
     */
    protected Command reserveParser(String input, String[] words) throws RimsException {
        if (!(words.length > 1)) {
            throw new RimsException("Please specify the resource to be reserved.");
        }
        if (words[1].equals("/item")) {
            int itemIndex = input.indexOf("/item") + 6;
            int qtyIndex = input.indexOf(" /qty");
            if (qtyIndex == -1) {
                throw new RimsException("Please specify the quantity of item to be reserved.");
            }
            if (itemIndex > qtyIndex) {
                throw new RimsException("Please specify the name of the item to be reserved.");
            }
            String itemName = input.substring(itemIndex, qtyIndex).trim();
            if (itemName.isEmpty()) {
                throw new RimsException("Please specify the name of the item to be loaned out.");
            }
            if (!resources.isItem(itemName)) {
                throw new RimsException("This item does not exist in your inventory!");
            }
            int idIndex = input.indexOf(" /id");
            if (idIndex == -1) {
                throw new RimsException("Please specify the ID of the borrower of this item.");
            }
            if (qtyIndex + 6 > idIndex) {
                throw new RimsException("Please specify the quantity of this item to be loaned out.");
            }
            int qty = parseInt(input.substring(qtyIndex + 6, idIndex).trim());
            if (qty <= 0) {
                throw new RimsException("Please specify a valid integer value above zero!");
            }
            int fromIndex = input.indexOf(" /from");
            if (fromIndex == -1) {
                throw new RimsException("Please specify the date from which the item is to be reserved.");
            }
            if (idIndex + 5 > fromIndex) {
                throw new RimsException("Please specify the ID of the borrower of this item.");
            }
            int userId = parseInt(input.substring(idIndex + 5, fromIndex).trim());
            int byIndex = input.indexOf(" /by");
            if (byIndex == -1 || byIndex + 5 > input.length()) {
                throw new RimsException("Please specify the date by which the item is to be returned.");
            }
            String dateFrom = parseDate(input.substring(fromIndex + 7, byIndex).trim());
            String dateTill = parseDate(input.substring(byIndex + 5).trim());
            return new ReserveCommand(itemName, qty, dateFrom, dateTill, userId);
        } else if (words[1].equals("/room")) {
            int roomIndex = input.indexOf("/room") + 6;
            int idIndex = input.indexOf(" /id");
            if (idIndex == -1) {
                throw new RimsException("Please specify the ID of the borrower of this room.");
            }
            if (roomIndex > idIndex) {
                throw new RimsException("Please specify the name of the room to be reserved.");
            }
            String roomName = input.substring(roomIndex, idIndex);
            if (roomName.isEmpty()) {
                throw new RimsException("Please specify the name of the room to be reserved.");
            }
            if (!resources.isRoom(roomName)) {
                throw new RimsException("This room does not exist in your inventory!");
            }
            int fromIndex = input.indexOf(" /from");
            if (fromIndex == -1) {
                throw new RimsException("Please specify the date from which the room is to be reserved.");
            }
            if (idIndex + 5 > fromIndex) {
                throw new RimsException("Please specify the ID of the borrower of this room.");
            }
            int userId = parseInt(input.substring(idIndex + 5, fromIndex));
            int byIndex = input.indexOf(" /by");
            if (byIndex == -1 || byIndex + 5 > input.length()) {
                throw new RimsException("Please specify the date by which the room is to be returned.");
            }
            String dateFrom = parseDate(input.substring(fromIndex + 7, byIndex).trim());
            String dateTill = parseDate(input.substring(byIndex + 5).trim());
            return new ReserveCommand(roomName, dateFrom, dateTill, userId);
        } else {
            throw new RimsException("Please choose an item or room to reserve.");
        }
    }

    /**
     * Parses user input into the parameters for a ReturnCommand.
     * @param input the input obtained from the user by the Ui.
     * @param words the input from the user, delimited by spaces into an array of individual words.
     * @return a ReturnCommand, if the input can be parsed into the required parameters.
     * @throws RimsException if the input cannot be parsed into the required parameters for a ReturnCommand.
     */
    protected Command returnParser(String input, String[] words) throws RimsException {
        if (words[1].equals("/id")) {
            int idIndex = input.indexOf(" /id") + 5;
            if (idIndex > input.length()) {
                throw new RimsException("Please specify the borrower's user ID.");
            }
            int userId = parseInt(input.substring(idIndex).trim());
            ReservationList userReservations = resources.getUserBookings(userId);
            if (userReservations.isEmpty()) {
                throw new RimsException("User " + userId + " has not made any bookings yet!");
            }
            ui.printLine();
            for (int i = 0; i < userReservations.size(); i++) {
                Reservation thisReservation = userReservations.getReservationByIndex(i);
                Resource borrowedResource = resources.getResourceById(thisReservation.getResourceId());
                ui.print(borrowedResource.toString());
                ui.print("\t" + userReservations.getReservationByIndex(i).toString());
            }
            ArrayList<Integer> resourcesToReturn = new ArrayList<Integer>();
            ArrayList<Integer> reservationsToCancel = new ArrayList<Integer>();
            String stringReservations = ui.getInput(
                "Enter the reservation ID(s) (separated by a space for multiple IDs) "
                + "that you wish to return / cancel:").trim();
            if (stringReservations.isEmpty()) {
                throw new RimsException("Please specify the IDs of the reservations you wish to end!");
            }
            String[] splitStringReservations = stringReservations.split(" ");
            for (int j = 0; j < splitStringReservations.length; j++) {
                int thisReservationId = parseInt(splitStringReservations[j]);
                resourcesToReturn.add(userReservations.getReservationById(thisReservationId).getResourceId());
                reservationsToCancel.add(thisReservationId);
            }
            return new ReturnCommand(userId, resourcesToReturn, reservationsToCancel);
        } else {
            throw new RimsException("Please follow the correct format for the return command.");
        }
    }

    /**
     * Parses user input into the parameters for a StatsCommand.
     * @param input the input obtained from the user by the Ui.
     * @param words the input from the user, delimited by spaces into an array of individual words.
     * @return a StatsCommand, if the input can be parsed into the required parameters.
     * @throws RimsException if the input cannot be parsed into the required parameters for a StatsCommand.
     */
    protected Command statsParser(String input, String[] words) throws RimsException, ParseException {
        int dateFromIndex = input.indexOf(" /from");
        int dateTillIndex = input.indexOf(" /till");
        if (dateFromIndex + 7 > dateTillIndex) {
            throw new RimsException("Please specify the date for which you want to view statistics.");
        }
        String dateFrom = parseDate(input.substring(dateFromIndex + 7, dateTillIndex).trim());
        String dateTill = parseDate(input.substring(dateTillIndex + 7).trim());
        return new StatsCommand(dateFrom, dateTill);
    }

    /**
     * Parses user input into the parameters for a ListCommand.
     * @param input the input obtained from the user by the Ui.
     * @param words the input from the user, delimited by spaces into an array of individual words.
     * @return a ListCommand, if the input can be parsed into the required parameters.
     * @throws RimsException if the input cannot be parsed into the required parameters for a ListCommand.
     */
    protected Command tagParser(String input, String[] words) throws RimsException {
        if (!(words.length > 1)) {
            throw new RimsException("Please specify the resource to be tagged.");
        }
        int paramTypeIndex = input.indexOf("/resource") + 10;
        int paramIndex = input.indexOf("/tag");
        String resourceName = input.substring(paramTypeIndex, paramIndex).trim();
        if (paramIndex == -1) {
            throw new RimsException("Please specify the tag name of the resource.");
        }
        if (paramTypeIndex > paramIndex) {
            throw new RimsException("Please specify the name of the item to be tagged.");
        }
        String tagName = words[words.length-1];
        if (tagName.isEmpty()) {
            throw new RimsException("Please specify the name of the resource to be tagged out.");
        }
        if (!resources.isItem(resourceName) && !resources.isRoom(resourceName)) {
            throw new RimsException("This resource does not exist in your inventory!");
        }
        return new TagCommand(tagName, resourceName);
    }

}