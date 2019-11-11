package commands;

import commons.DukeConstants;
import commons.LookupTable;
import commons.Storage;
import commons.UserInteraction;
import commons.WeekList;
import tasks.Assignment;
import tasks.TaskList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the command to show the week selected by the user.
 */
public class WeekCommand extends Command {
    private static final String TWELVE_HOUR_TIME_FORMAT_HOUR_AND_MINUTE_SEPARATOR = ":";
    private static final String TWELVE_HOUR_TIME_FORMAT_MAXIMUM_HOUR = "12";
    private static final String TWELVE_HOUR_TIME_AM_POST_FIX = "AM";
    private static final String textStart = "Start: ";
    private static final String newLine = "\n";
    private LookupTable lookupTable = LookupTable.getInstance();
    private String week;
    private final ObservableList<Text> monList = FXCollections.observableArrayList();
    private final ObservableList<Text> tueList = FXCollections.observableArrayList();
    private final ObservableList<Text> wedList = FXCollections.observableArrayList();
    private final ObservableList<Text> thuList = FXCollections.observableArrayList();
    private final ObservableList<Text> friList = FXCollections.observableArrayList();
    private final ObservableList<Text> satList = FXCollections.observableArrayList();
    private final ObservableList<Text> sunList = FXCollections.observableArrayList();
    private static WeekList weekList = new WeekList();

    /**
     * Creates a WeekCommand object.
     * @param fullCommand The user's input
     */
    public WeekCommand(String fullCommand) {
        fullCommand = fullCommand.trim();
        this.week = fullCommand;
    }

    /**
     * This method appends the day of the date to the date.
     * @return an ArrayList with the day and dates as a string
     */
    private ArrayList<String> generateDateDay(String date, LookupTable lookupTable) {
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        ArrayList<String> temp = new ArrayList<>();
        for (String day : days) {
            String dateOut = day + DukeConstants.BLANK_SPACE
                    + lookupTable.getValue(date + DukeConstants.BLANK_SPACE + day);
            temp.add(dateOut);
        }
        return temp;
    }

    /**
     * This method checks whether there is assignment the day.
     * @param assignmentList containing the module code and list of assignment
     * @param dates containing all the days and date
     * @return an ArrayList that contains the new days and dates that contain assignment for the new week
     */
    private ArrayList<String> checkIfExist(HashMap<String,
            ArrayList<Assignment>> assignmentList, ArrayList<String> dates) {
        ArrayList<String> newDates = new ArrayList<>();
        for (String date: dates) {
            if (assignmentList.containsKey(date)) {
                newDates.add(date);
            }
        }
        return newDates;
    }

    /**
     * This method updates the list to be shown to user based on their request.
     */
    private void updateList(String day, Text toShow) {
        switch (day) {
        case "Mon":
            monList.add(toShow);
            break;
        case  "Tue":
            tueList.add(toShow);
            break;
        case "Wed":
            wedList.add(toShow);
            break;
        case "Thu":
            thuList.add(toShow);
            break;
        case "Fri":
            friList.add(toShow);
            break;
        case "Sat":
            satList.add(toShow);
            break;
        case "Sun":
            sunList.add(toShow);
            break;
        default:
            break;
        }
    }

    /**
     * This method sort the list of the days by time.
     */
    private void sortList() {
        if (monList.size() != 0) {
            monList.sort(WeekCommand::compareByTime);
        }
        if (tueList.size() != 0) {
            tueList.sort(WeekCommand::compareByTime);
        }
        if (wedList.size() != 0) {
            wedList.sort(WeekCommand::compareByTime);
        }
        if (thuList.size() != 0) {
            thuList.sort(WeekCommand::compareByTime);
        }
        if (friList.size() != 0) {
            friList.sort(WeekCommand::compareByTime);
        }
        if (satList.size() != 0) {
            satList.sort(WeekCommand::compareByTime);
        }
        if (sunList.size() != 0) {
            sunList.sort(WeekCommand::compareByTime);
        }
    }

    /**
     * This method generate the text to be shown to the user.
     */
    private Text generateToShow(Assignment task) {
        Text toShow = new Text(task.toShow() + task.getModCode() + "\n" + task.getDescription());
        toShow.setFont(Font.font(10));
        if (task.getStatus()) {
            toShow.setFill(Color.GAINSBORO);
            toShow.setStrikethrough(true);
        }
        return toShow;
    }

    /**
     * This method generates data in day GridPane ListViews based on the week selected.
     */
    public void setListView(LookupTable lookupTable, TaskList eventsList) {
        ArrayList<String> weekDates = generateDateDay(week, lookupTable);
        for (String module: eventsList.getMap().keySet()) {
            HashMap<String, ArrayList<Assignment>> moduleValue = eventsList.getMap().get(module);
            ArrayList<String> dates = checkIfExist(moduleValue, weekDates);
            for (String strDate : dates) {
                String[] spilt = strDate.split(DukeConstants.BLANK_SPACE, 2);
                ArrayList<Assignment> data = moduleValue.get(strDate);
                for (Assignment task: data) {
                    Text toShow = generateToShow(task);
                    String day = spilt[0];
                    updateList(day, toShow);
                }
            }
        }
    }

    /**
     * This method creates a comparator for a 12 hour time in the format 07:00 AM to be sorted by timeline.
     * @param lhs First item compared
     * @param rhs Second item compared
     * @return The result of the comparison
     */
    private static int compareByTime(Text lhs, Text rhs) {
        String left = lhs.getText().replaceFirst(textStart, "");
        String[] leftSplit = left.split(newLine,2);
        String[] leftTimeSplit = leftSplit[0].split(DukeConstants.BLANK_SPACE);
        String right = rhs.getText().replaceFirst(textStart, "");
        String[] rightSplit = right.split(newLine,2);
        String[] rightTimeSplit = rightSplit[0].split(DukeConstants.BLANK_SPACE);

        if (leftTimeSplit[1].equals(TWELVE_HOUR_TIME_AM_POST_FIX)
                && rightTimeSplit[1].equals(TWELVE_HOUR_TIME_AM_POST_FIX)) {
            String[]leftTimeSplitHourMinute
                    = leftTimeSplit[0].split(TWELVE_HOUR_TIME_FORMAT_HOUR_AND_MINUTE_SEPARATOR);
            String[]rightTimeSplitHourMinute
                    = rightTimeSplit[0].split(TWELVE_HOUR_TIME_FORMAT_HOUR_AND_MINUTE_SEPARATOR);
            if (leftTimeSplitHourMinute[0].equals(TWELVE_HOUR_TIME_FORMAT_MAXIMUM_HOUR)
                    && rightTimeSplitHourMinute[0].equals(TWELVE_HOUR_TIME_FORMAT_MAXIMUM_HOUR)) {
                return leftTimeSplitHourMinute[1].compareTo(rightTimeSplitHourMinute[1]);
            } else if (leftTimeSplitHourMinute[0].equals(TWELVE_HOUR_TIME_FORMAT_MAXIMUM_HOUR)) {
                return -1;
            } else if (rightTimeSplitHourMinute[0].equals(TWELVE_HOUR_TIME_FORMAT_MAXIMUM_HOUR)) {
                return 1;
            } else {
                return leftTimeSplit[0].compareTo(rightTimeSplit[0]);
            }
        } else if (leftTimeSplit[1].equals(TWELVE_HOUR_TIME_AM_POST_FIX)) {
            return -1;
        } else if (rightTimeSplit[1].equals(TWELVE_HOUR_TIME_AM_POST_FIX)) {
            return 1;
        } else {
            String[]leftTimeSplitHourMinute
                    = leftTimeSplit[0].split(TWELVE_HOUR_TIME_FORMAT_HOUR_AND_MINUTE_SEPARATOR);
            String[]rightTimeSplitHourMinute
                    = rightTimeSplit[0].split(TWELVE_HOUR_TIME_FORMAT_HOUR_AND_MINUTE_SEPARATOR);
            if (leftTimeSplitHourMinute[0].equals(TWELVE_HOUR_TIME_FORMAT_MAXIMUM_HOUR)
                    && rightTimeSplitHourMinute[0].equals(TWELVE_HOUR_TIME_FORMAT_MAXIMUM_HOUR)) {
                return leftTimeSplitHourMinute[1].compareTo(rightTimeSplitHourMinute[1]);
            } else if (leftTimeSplitHourMinute[0].equals(TWELVE_HOUR_TIME_FORMAT_MAXIMUM_HOUR)) {
                return -1;
            } else if (rightTimeSplitHourMinute[0].equals(TWELVE_HOUR_TIME_FORMAT_MAXIMUM_HOUR)) {
                return 1;
            } else {
                return leftTimeSplit[0].compareTo(rightTimeSplit[0]);
            }
        }
    }

    public static WeekList getWeekList() {
        return weekList;
    }

    /**
     * Executes showing of the week list requested by the user.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the done task message
     * @param storage The Storage object to access file to load or save the tasks
     */
    @Override
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage) {
        setListView(lookupTable, events);
        sortList();
        weekList = new WeekList(monList, tueList, wedList, thuList, friList, satList, sunList);
        return DukeConstants.NO_FIELD;
    }
}