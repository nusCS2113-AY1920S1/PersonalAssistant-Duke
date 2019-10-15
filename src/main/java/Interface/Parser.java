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
    private static String[] split;
    private static String[] split1;
    private static String[] split2;
    private static String[] split3;
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
                    split = fullCommand.split(" ");
                    int index = Integer.parseInt(split[1]) - 1;
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
                    split = activity.split("/at"); //split[0] is " module_code description", split[1] is "date from time to time"
                    if (split[0].trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The description of a event cannot be empty.");
                    }
                    split1 = split[1].split("from"); //split1[0] is "date", split1[1] is "time to time"
                    String weekDate ="";
                    split2 = split1[0].trim().split(" ");
                    weekDate = split2[0];
                    if(weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                            || weekDate.equalsIgnoreCase("week") || weekDate.equalsIgnoreCase("recess")){
                        weekDate = LT.getDate(split1[0].trim());
                    }else{
                        weekDate = split1[0].trim();
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //format date
                    Date date = formatter.parse(weekDate.trim());
                    split2 = split1[1].split("to"); //split2[0] is (start) "time", split2[1] is (end) "time"
                    SimpleDateFormat formatter1 = new SimpleDateFormat("HHmm"); //format time
                    Date startTime = formatter1.parse(split2[0].trim());
                    Date endTime = formatter1.parse(split2[1].trim());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                    String dateString = dateFormat.format(date);
                    String startTimeString = timeFormat.format(startTime);
                    String endTimeString = timeFormat.format(endTime);
                    return new AddCommand(new Event(split[0].trim(), dateString, startTimeString, endTimeString));
                } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                    throw new DukeException("OOPS!!! Please enter event as follows:\n" +
                            "event name_of_event /at dd/MM/yyyy from HHmm to HHmm\n" +
                            "For example: event project meeting /at 1/1/2020 from 1500 to 1700");
                }
            }else if(fullCommand.trim().substring(0,8).equals("delete/e")){
                try { //add/e module_code description /at date from time to time
                    String activity = fullCommand.trim().substring(8);
                    split = activity.split("/at"); //split[0] is " module_code description", split[1] is "date from time to time"
                    if (split[0].trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The description of a event cannot be empty.");
                    }
                    split1 = split[1].split("from"); //split1[0] is "date", split1[1] is "time to time"
                    String weekDate ="";
                    split2 = split1[0].trim().split(" ");
                    weekDate = split2[0];
                    if(weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                            || weekDate.equalsIgnoreCase("week") || weekDate.equalsIgnoreCase("recess")){
                        weekDate = LT.getDate(split1[0].trim());
                    }else{
                        weekDate = split1[0].trim();
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //format date
                    Date date = formatter.parse(weekDate.trim());
                    split2 = split1[1].split("to"); //split2[0] is (start) "time", split2[1] is (end) "time"
                    SimpleDateFormat formatter1 = new SimpleDateFormat("HHmm"); //format time
                    Date startTime = formatter1.parse(split2[0].trim());
                    Date endTime = formatter1.parse(split2[1].trim());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                    String dateString = dateFormat.format(date);
                    String startTimeString = timeFormat.format(startTime);
                    String endTimeString = timeFormat.format(endTime);
                    return new DeleteCommand("event",new Event(split[0].trim(), dateString, startTimeString, endTimeString));
                } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                    throw new DukeException("OOPS!!! Please enter event as follows:\n" +
                            "event name_of_event /at dd/MM/yyyy from HHmm to HHmm\n" +
                            "For example: event project meeting /at 1/1/2020 from 1500 to 1700");
                }
            } else if (fullCommand.trim().substring(0,6).equals("remind")) {
                try {
                    boolean set = false;
                    String description = "";
                    String activity = fullCommand.trim().substring(6);
                    split = activity.trim().split("/by");
                    if(split[0].contains("/set")){
                        description = split[0].substring(4).trim();
                        if (description.isEmpty()) {
                            throw new DukeException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                        }
                        set = true;
                    } else {
                        description = split[0].substring(3).trim();
                        if (description.isEmpty()) {
                            throw new DukeException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                        }
                    }
                    split1 = split[1].trim().split("/to");
                    String weekDate = "";
                    String reminderDate = "";
                    split2 = split1[0].trim().split(" ");
                    weekDate = split2[0];
                    split3 = split1[1].trim().split(" ");
                    reminderDate = split3[0];
                    if (weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                            || weekDate.equalsIgnoreCase("week") || weekDate.equalsIgnoreCase("recess")
                            || reminderDate.equalsIgnoreCase("reading") || reminderDate.equalsIgnoreCase("exam")
                            || reminderDate.equalsIgnoreCase("week") || reminderDate.equalsIgnoreCase("recess")) {
                        weekDate = split1[0].substring(0,split1[0].length()- 4);
                        reminderDate = split1[1].substring(0,split1[1].length()- 4);
                        String time = split1[0].substring(split1[0].length()- 4);
                        weekDate = LT.getDate(weekDate) + " " + time;
                        time = split1[1].substring(split1[1].length()- 4);
                        reminderDate = LT.getDate(reminderDate) + " " + time;
                    }else{
                        weekDate = split1[0];
                        reminderDate = split1[1];
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
                    Date dateOfTask = formatter.parse(weekDate);
                    Date dateOfReminder = formatter.parse(reminderDate);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
                    String dateString = dateFormat.format(dateOfTask);
                    return new RemindCommand(new Deadline(description, dateString), dateOfReminder, set);
                } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                    throw new DukeException("OOPS!!! Please enter remind as follows:\n" +
                            "remind/(set/rm) mod_code description /by week n.o day time /to week n.o day time\n" +
                            "For example: remind/set cs2100 hand in homework /by week 9 fri 1500 /to week 9 thu 1500");
                }
            } else if (fullCommand.trim().substring(0,8).equals("delete/d")) {
                try {
                    String activity = fullCommand.trim().substring(8);
                    split = activity.split("/by");
                    if (split[0].trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                    }
                    String weekDate ="";
                    split2 = split[1].trim().split(" ");
                    weekDate = split2[0];
                    if(weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                            || weekDate.equalsIgnoreCase("week") || weekDate.equalsIgnoreCase("recess")){
                        weekDate = split[1].substring(0,split[1].length()- 4); // week x day y
                        String time = split[1].substring(split[1].length()- 4); // time E.g 0300
                        weekDate = LT.getDate(weekDate) + " " + time;
                    }else{
                        weekDate = split[1];
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
                    Date date = formatter.parse(weekDate);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
                    String dateString = dateFormat.format(date);
                    return new DeleteCommand("deadline",new Deadline(split[0].substring(6).trim(), dateString));

                } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                    throw new DukeException("\u2639" + " OOPS!!! The format input is wrong!");
                }
            } else if (fullCommand.trim().substring(0, 5).equals("add/d")) {//deadline
                try {
                    String activity = fullCommand.trim().substring(5);
                    split = activity.split("/by");
                    if (split[0].trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                    }
                    String weekDate ="";
                    split2 = split[1].trim().split(" ");
                    weekDate = split2[0];
                    if(weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                        || weekDate.equalsIgnoreCase("week") || weekDate.equalsIgnoreCase("recess")){
                        weekDate = split[1].substring(0,split[1].length()- 4); // week x day y
                        String time = split[1].substring(split[1].length()- 4); // time E.g 0300
                        weekDate = LT.getDate(weekDate) + " " + time;
                    }else{
                        weekDate = split[1];
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
                    Date date = formatter.parse(weekDate);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
                    String dateString = dateFormat.format(date);
                    return new AddCommand(new Deadline(split[0].trim(), dateString));
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
                    split = activity.split("/to");
                    split1 = split[0].trim().split(" ");
                    int index = Integer.parseInt(split1[1]) - 1;
                    if (split1[1].isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The index of a snooze cannot be empty.");
                    }
                    if (split1[0].contains("deadline")) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
                        Date date = formatter.parse(split[1].trim());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
                        String dateString = dateFormat.format(date);
                        return new SnoozeCommand(index, dateString, dateString, dateString);
                    } else {
                        split2 = split[1].trim().split("to");
                        split3 = split2[0].trim().split(" ");
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = formatter.parse(split3[0].trim());
                        SimpleDateFormat formatter1 = new SimpleDateFormat("HHmm");
                        Date startTime = formatter1.parse(split3[1].trim());
                        Date endTime = formatter1.parse(split2[1].trim());
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
