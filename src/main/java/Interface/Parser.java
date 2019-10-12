package Interface;
import Commands.*;
import JavaFx.AlertBox;
import Tasks.*;
import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private static LookupTable LT;
    static {
        try {
            LT = new LookupTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parser() throws IOException, ParseException {
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
            } else if (fullCommand.trim().substring(0, 4).equals("find")) {
                try {
                    String key = fullCommand.trim().substring(5);
                    if (key.trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! Please do not leave the keyword blank.");
                    } else {
                        return new FindCommand(key);
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    throw new DukeException("\u2639" + " OOPS!!! Please enter keyword.");
                }
            } else if (fullCommand.trim().substring(0, 4).equals("todo")) {
                String activity = fullCommand.trim().substring(4).trim();
                if (activity.isEmpty()) {
                    throw new DukeException("\u2639" + " OOPS!!! The description of a todo cannot be empty.");
                } else {
                    return new AddCommand(new Todo(activity));
                }
            } else if (fullCommand.trim().substring(0, 5).equals("add/e")) {
                try { //add/e module_code description /at date from time to time
                    String activity = fullCommand.trim().substring(5);
                    arr = activity.split("/at"); //arr[0] is " module_code description", arr[1] is "date from time to time"
                    if (arr[0].trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The description of a event cannot be empty.");
                    }
//                    arr3 = arr[0].split(" ");
//                    String modCode = arr3[0];
                    arr1 = arr[1].split("from"); //arr1[0] is "date", arr1[1] is "time to time"
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //format date
                    Date date = formatter.parse(arr1[0].trim());
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
                            "event name_of_event /at dd/MM/yyyy from HHmm to HHmm\n" +
                            "For example: event project meeting /at 1/1/2020 from 1500 to 1700");
                }
            } else if (fullCommand.trim().substring(0,6).equals("remind")) {
                return new RemindCommand();
            } else if (fullCommand.trim().substring(0, 6).equals("delete")) {
                try {
                    arr = fullCommand.trim().substring(7).split(" ");
                    String list = arr[0].trim();
                    int index = Integer.parseInt(arr[1]) - 1;
                    if (arr[0].trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The name of list cannot be empty.");
                    } else if (!list.trim().equals("todo") && !list.trim().equals("event") && !list.trim().equals("deadline")) {
                        throw new DukeException("OOPS!!! Please enter delete as follows:\n" +
                                "delete name_of_list task_number\n" +
                                "For example: delete todo 1");
                    }
                    return new DeleteCommand(index, arr[0]);
                } catch (NumberFormatException e) {
                    throw new DukeException("\u2639" + " OOPS!!! Please enter a valid task number.");
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DukeException("\u2639" + " OOPS!!! Please do not leave task number or list name blank.");
                }
            } else if (fullCommand.trim().substring(0, 5).equals("add/d")) {//deadline
                try {
                    String activity = fullCommand.trim().substring(5);
                    arr = activity.split("/by");
                    if (arr[0].trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                    }
                    arr3 = arr[0].split(" ");
                    String modCode = arr3[0];
                    String des = arr[0].substring(6).trim();
                    String weekdate = arr[1].substring(0,arr[1].length()- 4); // week x day y
                    String time = arr[1].substring(arr[1].length()- 4); // time E.g 0300
                    weekdate = LT.getDate(weekdate) + " " + time;
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
                    Date date = formatter.parse(weekdate);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
                    String dateString = dateFormat.format(date);
                    return new AddCommand(new Deadline(des, dateString,modCode));
                } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                    throw new DukeException(" OOPS!!! Please enter deadline as follows:\n" +
                            "deadline name_of_activity /by dd/MM/yyyy HHmm\n" +
                            "For example: deadline return book /by 2/12/2019 1800");
                }
            } else if(fullCommand.trim().contains("when is the nearest day in which I have a ") && fullCommand.trim().contains(" hour free slot?")) {
                try {
                    String duration = fullCommand;
                    String type = "event";
                    duration = duration.replaceFirst("when is the nearest day in which I have a ", "");
                    duration = duration.replaceFirst(" hour free slot", "");
                    duration = duration.substring(0, duration.indexOf('?'));

                    return new FindEarliestFreeTimesCommand(duration, type);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DukeException(" OOPS!!! Please enter find free time as follows:\n" +
                            " when is the nearest day in which I have a X hour free slot?\n" +
                            "For example:  when is the nearest day in which I have a 4.5 hour free slot?");
                }
            } else if (fullCommand.equals("show schedule")) {
                return new ViewSchedulesCommand();
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
            } else if (!(fullCommand.startsWith("todo") || fullCommand.startsWith("add/d") || fullCommand.startsWith("add/e")) &&
                    fullCommand.contains("(needs ") && fullCommand.endsWith(" hours)")) {
                try{
                    int index;
                    String type;
                    if (fullCommand.endsWith(" hours)")) {
                        index = fullCommand.indexOf(" hours)");
                        type = "event";

                    } else {
                        index = fullCommand.indexOf(" days)");
                        type = "todo";
                    }
                    fullCommand = fullCommand.substring(0,index);
                    index = fullCommand.indexOf("(needs ");
                    String taskDescription = fullCommand.substring(0, index).trim();
                    String duration = fullCommand.substring(index+7).trim();
                    return new FixedDurationTasksCommand(taskDescription, duration, type);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DukeException(" OOPS!!! Please enter Fixed Duration Task as follows:\n" +
                            "'Task Description' ' (needs x hours)'\n" +
                            "reading the sales report (needs 2 hours)");
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
