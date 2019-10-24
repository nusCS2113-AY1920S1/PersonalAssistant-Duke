package Parser;
/*
import Commands.AddCommand;
import Commands.Command;
import Interface.*;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddParse extends Parse {
    private static String[] split;
    private static String[] split1;
    private static String[] split2;
    private static String fullCommand;
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

    public AddParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command execute(LookupTable LT, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        if (fullCommand.trim().substring(0, 5).equals("add/d")) {//deadline
            try {
                String activity = fullCommand.trim().substring(5);
                split = activity.split("/by");
                if (split[0].trim().isEmpty()) {
                    throw new DukeException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                }
                String weekDate = "";
                split2 = split[1].trim().split(" ");
                weekDate = split2[0];
                if (weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                        || weekDate.equalsIgnoreCase("week") || weekDate.equalsIgnoreCase("recess")) {
                    weekDate = split[1].substring(0, split[1].length() - 4); // week x day y
                    String time = split[1].substring(split[1].length() - 4); // time E.g 0300
                    weekDate = LT.getDate(weekDate) + " " + time;
                } else {
                    weekDate = split[1];
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
                Date date = formatter.parse(weekDate);
                SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy");
                String dateString = dateFormat.format(date);
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                String timeString = timeFormat.format(date);
                return new AddCommand(new Deadline(split[0].trim(), dateString, timeString));
            } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                LOGGER.log(Level.INFO, e.toString(), e);
                throw new DukeException(" OOPS!!! Please enter deadline as follows:\n" +
                        "add/d mod_code name_of_event /by dd/MM/yyyy HHmm\n" +
                        "or add/d mod_code name_of_event /by week x day HHmm\n");
            }
        } else if (fullCommand.trim().substring(0, 5).equals("add/e")) {
            try { //add/e module_code description /at date from time to time
                String activity = fullCommand.trim().substring(5);
                split = activity.split("/at"); //split[0] is " module_code description", split[1] is "date /from time /to time"
                if (split[0].trim().isEmpty()) {
                    throw new DukeException("\u2639" + " OOPS!!! The description of a event cannot be empty.");
                }
                split1 = split[1].split("/from"); //split1[0] is "date", split1[1] is "time to time"
                String weekDate = "";
                split2 = split1[0].trim().split(" ");
                weekDate = split2[0];
                if (weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                        || weekDate.equalsIgnoreCase("week") || weekDate.equalsIgnoreCase("recess")) {
                    weekDate = LT.getDate(split1[0].trim());
                } else {
                    weekDate = split1[0].trim();
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //format date
                Date date = formatter.parse(weekDate.trim());
                split2 = split1[1].split("/to"); //split2[0] is (start) "time", split2[1] is (end) "time"
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
                LOGGER.log(Level.INFO, e.toString(), e);
                throw new DukeException("OOPS!!! Please enter event as follows:\n" +
                        "add/e modCode name_of_event /at dd/MM/yyyy from HHmm to HHmm\n" +
                        "For example: add/e CS1231 project meeting /at 1/1/2020 /from 1500 /to 1700");
            }
        }
    }
}
*/