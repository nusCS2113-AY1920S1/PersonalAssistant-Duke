package Interface;
import Commands.*;
import JavaFx.AlertBox;
import JavaFx.MainWindow;
import Tasks.*;
import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Deals with the input of the user and tries to understand the
 * user's input with fixed commands.
 */
public class Parser {
    private static String[] arr;
    private static String[] arr1;
    private static String[] arr2;
    private static String[] arr3;
    private static String[] arr4;
    private static LookupTable LT;

    static {
        try {
            LT = new LookupTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method breaks apart the user's input and tries to make sense with it.
     * @param fullCommand The user's input
     * @return This returns a Command object based on user's input
     * @throws DukeException On invalid input or when wrong input format is entered
     */
    public static Command parse(String fullCommand) throws DukeException {
        try {
            if (fullCommand.trim().equals("bye")) {
                return new ByeCommand();
            } else if (fullCommand.trim().substring(0, 4).equals("list")) {
                try {
                    String list = fullCommand.trim().substring(5);
                    if (list.trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! Please do not leave name of list blank.");
                    } else if (list.trim().equals("todo")) {
                        return new ListCommand(list);
                    } else if (list.trim().equals("event")) {
                        return new ListCommand(list);
                    } else if (list.trim().equals("deadline")) {
                        return new ListCommand(list);
                    } else {
                        throw new DukeException("\u2639" + " OOPS!!! Please enter name of list");
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    throw new DukeException("OOPS!!! Please enter list as follows:\n" +
                            "list name_of_list_to_view\n" +
                            "For example: list todo");
                }
            } else if (fullCommand.trim().substring(0, 4).equals("done")) {
                try {
                    arr = fullCommand.split(" ");
                    int index = Integer.parseInt(arr[1]) - 1;
                    return new DoneCommand(index);
                } catch (NumberFormatException e) {
                    throw new DukeException("\u2639" + " OOPS!!! Please enter a valid task number.");
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DukeException("\u2639" + " OOPS!!! Please do not leave task number blank.");
                }
            } else if (fullCommand.trim().substring(0, 5).equals("add/e")) {
                try { //add/e module_code description /at date from time to time
                    String activity = fullCommand.trim().substring(5);
                    arr = activity.split("/at"); //arr[0] is " module_code description", arr[1] is "date from time to time"
                    if (arr[0].trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The description of a event cannot be empty.");
                    }
                    arr1 = arr[1].split("from"); //arr1[0] is "date", arr1[1] is "time to time"
                    String weekDate ="";
                    arr2 = arr1[0].trim().split(" ");
                    weekDate = arr2[0];
                    if(weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                            || weekDate.equalsIgnoreCase("week")|| weekDate.equalsIgnoreCase("recess")){
                        weekDate = LT.getDate(arr1[0].trim());
                    }else{
                        weekDate = arr1[0].trim();
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //format date
                    Date date = formatter.parse(weekDate.trim());
                    arr2 = arr1[1].split("to"); //arr2[0] is (start) "time", arr2[1] is (end) "time"
                    SimpleDateFormat formatter1 = new SimpleDateFormat("HHmm"); //format time
                    Date startTime = formatter1.parse(arr2[0].trim());
                    Date endTime = formatter1.parse(arr2[1].trim());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                    String dateString = dateFormat.format(date);
                    String startTimeString = timeFormat.format(startTime);
                    String endTimeString = timeFormat.format(endTime);
                    return new AddCommand(new Event(arr[0].trim(), dateString, startTimeString, endTimeString));
                } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                    throw new DukeException("OOPS!!! Please enter event as follows:\n" +
                            "add/e modCode name_of_event /at dd/MM/yyyy from HHmm to HHmm\n" +
                            "For example: add/e CS1231 project meeting /at 1/1/2020 from 1500 to 1700");
                }
            } else if (fullCommand.trim().substring(0,7).equals("recur/e")) {
                try {
                    String activity = fullCommand.trim().substring(7);
                    String startWeekDate;
                    String endWeekDate;
                    arr = activity.split("/start"); //arr[0] is " module_code description", arr[1] is "date to date from time to time"
                    if (arr[0].trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The description of a event cannot be empty.");
                    }
                    arr1 = arr[1].split("from"); //arr1[0] is "date to date" or "week X mon to week X mon", arr1[1] is "time to time"
                    arr3 = arr1[0].split("to"); //arr3[0] is (start) "date", arr3[1] is (end) "date"
                    arr4 = arr3[0].trim().split(" "); //split the start date
                    //recess week mon / week 3 mon / exam week mon / reading week tue
                    startWeekDate = arr4[0].trim();
                    if (startWeekDate.equalsIgnoreCase("reading") || startWeekDate.equalsIgnoreCase("exam")
                            || startWeekDate.equalsIgnoreCase("week") || startWeekDate.equalsIgnoreCase("recess")) {
                        startWeekDate = LT.getDate(arr3[0].trim());
                    } else {
                        startWeekDate = arr3[0].trim();
                    }
                    arr4 = arr3[1].trim().split(" "); //split the end date
                    endWeekDate = arr4[0].trim();
                    if (endWeekDate.equalsIgnoreCase("reading") || endWeekDate.equalsIgnoreCase("exam")
                            || endWeekDate.equalsIgnoreCase("week") || endWeekDate.equalsIgnoreCase("recess")) {
                        endWeekDate = LT.getDate(arr3[1].trim());
                    } else {
                        endWeekDate = arr3[1].trim();
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //format date
                    Date startDate = formatter.parse(startWeekDate);
                    Date endDate = formatter.parse(endWeekDate);
                    arr2 = arr1[1].split("to"); //arr2[0] is (start) "time", arr2[1] is (end) "time"
                    SimpleDateFormat formatter1 = new SimpleDateFormat("HHmm"); //format time
                    Date startTime = formatter1.parse(arr2[0].trim());
                    Date endTime = formatter1.parse(arr2[1].trim());
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                    String startTimeString = timeFormat.format(startTime);
                    String endTimeString = timeFormat.format(endTime);
                    return new RecurringCommand(arr[0].trim(),startDate, endDate, startTimeString, endTimeString);
                } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                    throw new DukeException("OOPS!!! Please enter recurring event as follows:\n" +
                            "recur/e modCode name_of_event /start dd/MM/yyyy to dd/MM/yyyy from HHmm to HHmm\n" +
                            "For example: recur/e CS1231 project meeting /start 1/10/2019 to 15/11/2019 from 1500 to 1700");
                }
            }else if(fullCommand.trim().substring(0,8).equals("delete/e")){
                try { //add/e module_code description /at date from time to time
                    String activity = fullCommand.trim().substring(8);
                    arr = activity.split("/at"); //arr[0] is " module_code description", arr[1] is "date from time to time"
                    if (arr[0].trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The description of a event cannot be empty.");
                    }
                    arr1 = arr[1].split("from"); //arr1[0] is "date", arr1[1] is "time to time"
                    String weekDate ="";
                    arr2 = arr1[0].trim().split(" ");
                    weekDate = arr2[0];
                    if(weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                            ||weekDate.equalsIgnoreCase("week")|| weekDate.equalsIgnoreCase("recess")){
                        weekDate = LT.getDate(arr1[0].trim());
                    }else{
                        weekDate = arr1[0].trim();
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //format date
                    Date date = formatter.parse(weekDate.trim());
                    arr2 = arr1[1].split("to"); //arr2[0] is (start) "time", arr2[1] is (end) "time"
                    SimpleDateFormat formatter1 = new SimpleDateFormat("HHmm"); //format time
                    Date startTime = formatter1.parse(arr2[0].trim());
                    Date endTime = formatter1.parse(arr2[1].trim());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                    String dateString = dateFormat.format(date);
                    String startTimeString = timeFormat.format(startTime);
                    String endTimeString = timeFormat.format(endTime);
                    return new DeleteCommand("event",new Event(arr[0].trim(), dateString, startTimeString, endTimeString));
                } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                    throw new DukeException("OOPS!!! Please enter in the format as follows:\n" +
                            "delete/e mod_code name_of_event /at dd/MM/yyyy from HHmm to HHmm\n" +
                            "or delete/e mod_code name_of_event /at week x day from HHmm to HHmm\n");
                }
            } else if (fullCommand.trim().substring(0,6).equals("remind")) {
                return new RemindCommand();
            } else if (fullCommand.trim().substring(0,8).equals("delete/d")) {
                try {
                    String activity = fullCommand.trim().substring(8);
                    arr = activity.split("/by");
                    if (arr[0].trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                    }
                    String weekDate ="";
                    arr2 = arr[1].trim().split(" ");
                    weekDate = arr2[0];
                    if(weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                            || weekDate.equalsIgnoreCase("week")|| weekDate.equalsIgnoreCase("recess")){
                        weekDate = arr[1].substring(0,arr[1].length()- 4); // week x day y
                        String time = arr[1].substring(arr[1].length()- 4); // time E.g 0300
                        weekDate = LT.getDate(weekDate) + " " + time;
                    }else{
                        weekDate = arr[1];
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
                    Date date = formatter.parse(weekDate);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
                    String dateString = dateFormat.format(date);
                    return new DeleteCommand("deadline",new Deadline(arr[0].trim(), dateString));

                } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                    throw new DukeException("OOPS!!! Please enter in the format as follows:\n" +
                            "delete/d mod_code name_of_event /by dd/MM/yyyy HHmm\n" +
                            "or delete/d mod_code name_of_event /by week x day HHmm\n");
                }
            } else if (fullCommand.trim().substring(0, 5).equals("add/d")) {//deadline
                try {
                    String activity = fullCommand.trim().substring(5);
                    arr = activity.split("/by");
                    if (arr[0].trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                    }
                    String weekDate ="";
                    arr2 = arr[1].trim().split(" ");
                    weekDate = arr2[0];
                    if(weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                        || weekDate.equalsIgnoreCase("week")|| weekDate.equalsIgnoreCase("recess")){
                        weekDate = arr[1].substring(0,arr[1].length()- 4); // week x day y
                        String time = arr[1].substring(arr[1].length()- 4); // time E.g 0300
                        weekDate = LT.getDate(weekDate) + " " + time;
                    }else{
                        weekDate = arr[1];
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
                    Date date = formatter.parse(weekDate);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
                    String dateString = dateFormat.format(date);
                    return new AddCommand(new Deadline(arr[0].trim(), dateString));
                } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                    throw new DukeException(" OOPS!!! Please enter deadline as follows:\n" +
                            "add/d mod_code name_of_event /by dd/MM/yyyy HHmm\n" +
                            "or add/d mod_code name_of_event /by week x day HHmm\n");
                }
            } else if (fullCommand.trim().substring(0,6).equals("snooze")) {
                try {
                    String activity = fullCommand.trim().substring(6);
                    arr = activity.split("/to");
                    arr1 = arr[0].trim().split(" ");
                    int index = Integer.parseInt(arr1[1]) - 1;
                    if (arr1[1].isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The index of a snooze cannot be empty.");
                    }
                    if (arr1[0].contains("deadline")) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
                        Date date = formatter.parse(arr[1].trim());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
                        String dateString = dateFormat.format(date);
                        return new SnoozeCommand(index, dateString, dateString, dateString);
                    } else {
                        arr2 = arr[1].trim().split("to");
                        arr3 = arr2[0].trim().split(" ");
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = formatter.parse(arr3[0].trim());
                        SimpleDateFormat formatter1 = new SimpleDateFormat("HHmm");
                        Date startTime = formatter1.parse(arr3[1].trim());
                        Date endTime = formatter1.parse(arr2[1].trim());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                        String dateString = dateFormat.format(date);
                        String startTimeString = timeFormat.format(startTime);
                        String endTimeString = timeFormat.format(endTime);
                        return new SnoozeCommand(index, dateString, startTimeString, endTimeString);
                    }
                } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                    throw new DukeException(" OOPS!!! Please enter snooze as follows respectively:\n" +
                            "To snooze deadlines: snooze deadline index /to dd/MM/yyyy HHmm\n" +
                            "To snooze events: snooze event index /to dd/MM/yyyy HHmm to HHmm\n" +
                            "For example: snooze event 2 /to 2/12/2019 1800 to 1900");
                }
            } else if (fullCommand.contains("(from") && fullCommand.contains("to")) {
                try {
                    boolean isValid = true;
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();
                    String currentDate = formatter.format(date);
                    int index = fullCommand.indexOf("(from");
                    String taskDescription = fullCommand.substring(0, index);
                    fullCommand = fullCommand.replace(taskDescription, "");
                    fullCommand = fullCommand.replace("(from", "").trim();
                    String[] startAndEndDate = fullCommand.split(" to ", 2);
                    String startDate = startAndEndDate[0];
                    String endDate = startAndEndDate[1].replace(")", "").trim();
                    Date beginDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
                    Date newCurrentDate = new SimpleDateFormat("dd/MM/yyyy").parse(currentDate);

                    if (beginDate.compareTo(newCurrentDate) < 0) { //date is wrong
                        AlertBox.display("Warning message", "Invalid date", "Please enter another valid date",
                                Alert.AlertType.WARNING);
                        isValid = false;
                    }
                    System.out.println("value of isValid: " + isValid);
                    System.out.println("start date: " + startDate + " Current date: " + currentDate);
                    return new DoWithinPeriodTasksCommand(taskDescription, startDate, endDate, isValid);
                } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                    throw new DukeException(" OOPS!!! Please enter Do Within Period Task as follows:\n" +
                            " 'Task Description' '(from DD/MM/yyyy to DD/MM/yyyy)'");
                }
            } else {
                throw new DukeException("\u2639" + " OOPS!!! I'm sorry, but I don't know what that means :-(");
            }

        } catch (StringIndexOutOfBoundsException e) {
            throw new DukeException("\u2639" + " OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
