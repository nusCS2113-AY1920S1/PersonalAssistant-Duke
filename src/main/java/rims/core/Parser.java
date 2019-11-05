package rims.core;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

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
import rims.command.ViewDeadlinesCommand;

import rims.exception.RimsException;
import rims.core.Ui;
import rims.core.ResourceList;
import rims.resource.Resource;
import rims.resource.Reservation;
import rims.resource.ReservationList;

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

    /**
     * Saves the last executed command that modified data
     * in Parser.
     * @param c Previous command that modified data in ResourceList.
     */
    public void setPrevCommand(Command c) {
        if (c.canModifyData()) { prevCommand = c; }
    }

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
        for (int i = 0; i < 7; i++) {
            if (new SimpleDateFormat("EEEEE").format(todayDate).equals(day)) {
                validDay = true;
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy HHmm");
                stringDate = format.format(todayDate);
                stringDate = stringDate.substring(0, stringDate.length() - 4);
                stringDate += time;
            } else {
                Calendar cal = Calendar.getInstance();
                cal.setTime(todayDate);
                cal.add(Calendar.DATE, 1);
                todayDate = cal.getTime();
            }
        }
        if (!(validDay) || stringDate == null) {
            throw new RimsException("Please enter a valid day / time.");
        } else {
            return stringDate;
        }
    }

    /**
     * Identifies which command the user wishes to implement, even from a one letter
     * string input.
     * 
     * @param input the first letter of the command or the entire command.
     * @return a String version of the command the user wishes to call.
     */
    // @@author aarushisingh1
    public String friendlierSyntax(String input) {
        String identifier = "";
        if (input.equals("l")) {
            identifier = ui.getInput("Did you mean list?");
            if (identifier.equals("Y")) {
                return "list";
            } else {
                identifier = ui.getInput("Did you mean loan?");
                return "loan";
            }
        } else if (input.equals("loan")) {
            return "loan";
        } else if (input.equals("list")) {
            return "list";
        } else if (input.equals("d")) {
            identifier = ui.getInput("Did you mean delete?");
            if (identifier.equals("Y")) {
                return "delete";
            } else {
                identifier = ui.getInput("Did you mean deadlines?");
                return "deadlines";
            }
        } else if (input.equals("delete")) {
            return "delete";
        } else if (input.equals("deadlines")) {
            return "deadlines";
        } else if (input.equals("r")) {
            identifier = ui.getInput("Did you mean reserve?");
            if (identifier.equals("Y")) {
                return "reserve";
            } else {
                identifier = ui.getInput("Did you mean return?");
                return "return";
            }
        } else if (input.equals("reserve")) {
            return "reserve";
        } else if (input.equals("return")) {
            return "return";
        } else {
            return "not valid";
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
        Command c;
        String[] words = input.split(" ");
        String simplerWord = friendlierSyntax(words[0]);

        if (input.equals("bye") && words.length == 1) {
            c = new CloseCommand();
        } else if (input.equals("list") && words.length == 1) {
            c = new ListCommand();
        // fix for date
        } else if (words[0].equals("list") && words.length == 3) {
            String paramType = words[1].substring(1);
            String param = words[2];
            if (paramType.equals("date") || paramType.equals("room") || paramType.equals("item")) {
                c = new ListCommand(paramType, param);
            } else {
                throw new RimsException("Invalid list parameter! Please specify '/date', '/room' or /item to view a detailed list of a resource.");
            }
        //@@author aarushisingh1
        } else if (input.equals("deadlines") && words.length == 1) {
            c = new ViewDeadlinesCommand();
        } else if (input.equals("help") && words.length == 1) {
            c = new HelpCommand();
            // @@author danielcyc
        } else if (words[0].equals("calendar") && words.length == 1 || words[0].equals("c")) {
            CalendarCommand.printCal(resources, ui);
            c = new ListCommand();
         //@@author hin1
        } else if (words[0].equals("add") || words[0].equals("a")) {
            if (words[1].equals("/item")) {
                int itemIndex = input.indexOf("/item") + 6;
                int qtyIndex = input.indexOf(" /qty");
                String item = input.substring(itemIndex, qtyIndex);
                String qtyString = input.replaceFirst("add /item " + item + " /qty ", "");
                int qty = Integer.parseInt(qtyString);
                c = new AddCommand(item, qty);
            } else if (words[1].equals("/room")) {
                int roomIndex = input.indexOf("/room") + 6;
                String room = input.substring(roomIndex);
                c = new AddCommand(room);
            }
            else {
                throw new RimsException("Please choose a room or item to add to your inventory.");
            }
        } else if (words[0].equals("delete")) {
            if (words[1].equals("/item")) {
                int itemIndex = input.indexOf("/item") + 6;
                String itemName = input.substring(itemIndex);
                c = new DeleteCommand(itemName, "item");
            } else if (words[1].equals("/room")) {
                int roomIndex = input.indexOf("/room") + 6;
                String roomName = input.substring(roomIndex);
                c = new DeleteCommand(roomName, "room");
            } else {
                throw new RimsException("Please choose a room or item to delete from your inventory.");
            }
        //@@author rabhijit
        } else if (words[0].equals("loan")) {
            if (words[1].equals("/item")) {
                int itemIndex = input.indexOf("/item") + 6;
                int qtyIndex = input.indexOf(" /qty");
                String itemName = input.substring(itemIndex, qtyIndex);
                if (!resources.isItem(itemName)) {
                    throw new RimsException("There is no such item!");
                }
                int idIndex = input.indexOf(" /id");
                int qty = Integer.parseInt(input.substring(qtyIndex + 6, idIndex));
                int byIndex = input.indexOf(" /by");
                int userId = Integer.parseInt(input.substring(idIndex + 5, byIndex));
                String dateTill = input.replaceFirst("loan /item " + itemName + " /qty " + qty + " /id " + userId + " /by ", "");
                if (dateTill.length() < 15) {
                    String[] splitDateTill = dateTill.split(" ");
                    dateTill = convertNaturalDate(splitDateTill[0], splitDateTill[1]);
                }
                // get list of items - move to fn?
                ArrayList<Resource> allOfItem = resources.getAllOfResource(itemName);
                for (int i = 0; i < allOfItem.size(); i++) {
                    Resource thisResource = allOfItem.get(i);
                    ReservationList thisResourceReservations = thisResource.getReservations();
                    ui.printDash();
                    ui.print(thisResource.toString() + " (ID: " + thisResource.getResourceId() + ")");
                    if (!thisResourceReservations.isEmpty()) {
                        for (int j = 0; j < thisResourceReservations.size(); j++) {
                            ui.print("\t" + thisResourceReservations.getReservationByIndex(j).toString());
                        }
                    } else {
                        ui.print("No bookings for this resource yet!");
                    }
                }
                ui.printDash();
                ui.printLine();
                c = new ReserveCommand(itemName, qty, dateTill, userId);
            }
            else if (words[1].equals("/room")) {
                int roomIndex = input.indexOf("/room") + 6;
                int idIndex = input.indexOf(" /id");
                String roomName = input.substring(roomIndex, idIndex);
                if (!resources.isRoom(roomName)) {
                    throw new RimsException("There is no such room!");
                }
                int byIndex = input.indexOf(" /by");
                int userId = Integer.parseInt(input.substring(idIndex + 5, byIndex));
                String dateTill = input.replaceFirst("loan /room " + roomName + " /id " + userId + " /by ", "");
                if (dateTill.length() < 15) {
                    String[] splitDateTill = dateTill.split(" ");
                    dateTill = convertNaturalDate(splitDateTill[0], splitDateTill[1]);
                }
                // get list of rooms
                ui.printLine();
                Resource thisResource = resources.getResourceByName(roomName);
                ReservationList thisResourceReservations = thisResource.getReservations();
                ui.print(thisResource.toString() + " (ID: " + thisResource.getResourceId() + ")");
                if (!thisResourceReservations.isEmpty()) {
                    for (int j = 0; j < thisResourceReservations.size(); j++) {
                        ui.print("\t" + thisResourceReservations.getReservationByIndex(j).toString());
                    }
                } else {
                    ui.print("No bookings for this resource yet!");
                }
                ui.printLine();
                c = new ReserveCommand(roomName, dateTill, userId);
            } else {
                throw new RimsException("Please choose an item or room to loan out.");
            }
        } else if (words[0].equals("reserve")) {
            if (words[1].equals("/item")) {
                int itemIndex = input.indexOf("/item") + 6;
                int qtyIndex = input.indexOf(" /qty");
                String itemName = input.substring(itemIndex, qtyIndex);
                int idIndex = input.indexOf(" /id");
                int qty = Integer.parseInt(input.substring(qtyIndex + 6, idIndex));
                int fromIndex = input.indexOf(" /from");
                int userId = Integer.parseInt(input.substring(idIndex + 5, fromIndex));
                int byIndex = input.indexOf(" /by");
                String dateFrom = input.substring(fromIndex + 7, byIndex);
                if (dateFrom.length() < 15) {
                    String[] splitDateFrom = dateFrom.split(" ");
                    dateFrom = convertNaturalDate(splitDateFrom[0], splitDateFrom[1]);
                }
                String dateTill = input.replaceFirst("reserve /item " + itemName + " /qty " + qty + " /id " + userId + " /from " + dateFrom + " /by ", "");
                if (dateTill.length() < 15) {
                    String[] splitDateTill = dateTill.split(" ");
                    dateTill = convertNaturalDate(splitDateTill[0], splitDateTill[1]);
                }
                // get list of items - move to fn?
                ArrayList<Resource> allOfItem = resources.getAllOfResource(itemName);
                for (int i = 0; i < allOfItem.size(); i++) {
                    Resource thisResource = allOfItem.get(i);
                    ReservationList thisResourceReservations = thisResource.getReservations();
                    ui.printDash();
                    ui.print(thisResource.toString() + " (ID: " + thisResource.getResourceId() + ")");
                    if (!thisResourceReservations.isEmpty()) {
                        for (int j = 0; j < thisResourceReservations.size(); j++) {
                            ui.print("\t" + thisResourceReservations.getReservationByIndex(j).toString());
                        }
                    } else {
                        ui.print("No bookings for this resource yet!");
                    }
                }
                ui.printDash();
                ui.printLine();
                c = new ReserveCommand(itemName, qty, dateFrom, dateTill, userId);
            } else if (words[1].equals("/room")) {
                int roomIndex = input.indexOf("/room") + 6;
                int idIndex = input.indexOf(" /id");
                String roomName = input.substring(roomIndex, idIndex);
                if (!resources.isRoom(roomName)) {
                    throw new RimsException("There is no such room!");
                }
                int fromIndex = input.indexOf(" /from");
                int userId = Integer.parseInt(input.substring(idIndex + 5, fromIndex));
                int byIndex = input.indexOf(" /by");
                String dateFrom = input.substring(fromIndex + 7, byIndex);
                if (dateFrom.length() < 15) {
                    String[] splitDateFrom = dateFrom.split(" ");
                    dateFrom = convertNaturalDate(splitDateFrom[0], splitDateFrom[1]);
                }
                String dateTill = input.replaceFirst("reserve /room " + roomName + " /id " + userId + " /from " + dateFrom + " /by ", "");
                if (dateTill.length() < 15) {
                    String[] splitDateTill = dateTill.split(" ");
                    dateTill = convertNaturalDate(splitDateTill[0], splitDateTill[1]);
                }
                // get list of rooms
                ui.printLine();
                Resource thisResource = resources.getResourceByName(roomName);
                ReservationList thisResourceReservations = thisResource.getReservations();
                ui.print(thisResource.toString() + " (ID: " + thisResource.getResourceId() + ")");
                if (!thisResourceReservations.isEmpty()) {
                    for (int j = 0; j < thisResourceReservations.size(); j++) {
                        ui.print("\t" + thisResourceReservations.getReservationByIndex(j).toString());
                    }
                } else {
                    ui.print("No bookings for this resource yet!");
                }
                ui.printLine();
                c = new ReserveCommand(roomName, dateFrom, dateTill, userId);
            } else {
                throw new RimsException("Please choose an item or room to reserve.");
            }
        } else if (words[0].equals("return") && words.length == 3) {
            if (words[1].equals("/id")) {
                int userId = Integer.parseInt(words[2]);
                ReservationList userReservations = resources.getUserBookings(userId);
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
                    + "that you wish to return / cancel:");
                String[] splitStringReservations = stringReservations.split(" ");
                for (int j = 0; j < splitStringReservations.length; j++) {
                    int thisReservationId = Integer.parseInt(splitStringReservations[j]);
                    resourcesToReturn.add(userReservations.getReservationById(thisReservationId).getResourceId());
                    reservationsToCancel.add(thisReservationId);
                }
                c = new ReturnCommand(userId, resourcesToReturn, reservationsToCancel);
            } else {
                throw new RimsException("Please follow the correct format for the return command.");
            }
        } else if (words[0].equals("undo") || words[0].equals("u")) {
            c = new UndoCommand(prevCommand);
        } else if ((words[0].equals("stats") || words[0].equals("s")) && words.length > 1) {
            int dateFromIndex = input.indexOf("/from");
            int dateTillIndex = input.indexOf("/till");
            String dateFrom = input.substring(dateFromIndex + 6, dateTillIndex);
            String dateTill = input.substring(dateTillIndex + 6);
            if (dateFrom.length() < 15) {
                String[] splitDateFrom = dateFrom.split(" ");
                dateFrom = convertNaturalDate(splitDateFrom[0], splitDateFrom[1]);
            }
            String dateTill = ui.getInput(
                "Enter the end date in the format: DD/MM/YYYY HHmm, OR in the format: Tuesday HHmm");
            if (dateFrom.length() < 15) {
                String[] splitDateTill = dateFrom.split(" ");
                dateTill = convertNaturalDate(splitDateTill[0], splitDateTill[1]);
            }
            c = new StatsCommand(dateFrom, dateTill);
        } else {
            throw new RimsException("Please enter a recognizable command!");
        }
        return c;
    }
}