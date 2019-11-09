package Commands;

import Commons.DukeConstants;
import Commons.LookupTable;
import Commons.Storage;
import Commons.UserInteraction;
import Commons.WeekList;
import Tasks.Assignment;
import Tasks.TaskList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class WeekCommand extends Command {
    private static final String NO_FIELD = "void";
    private static final String twelveHourTimeFormatSeparator = ":";
    private static final String twelveHourTimeHourBoundary = "12";
    private static final String twelveHourTimeAMPostFix = "AM";
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

    public WeekCommand(String fullCommand) {
        fullCommand = fullCommand.trim();
        this.week = fullCommand;
    }

    private ArrayList<String> generateDateDay(String date, LookupTable lookupTable) {
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        ArrayList<String> temp = new ArrayList<>();
        for (String day : days) {
            String dateOut = day + DukeConstants.STRING_SPACE_SPLIT_KEYWORD
                    + lookupTable.getValue(date + DukeConstants.STRING_SPACE_SPLIT_KEYWORD + day);
            temp.add(dateOut);
        }
        return temp;
    }

    private ArrayList<String> checkIfExist(HashMap<String, ArrayList<Assignment>> map, ArrayList<String> dates) {
        ArrayList<String> newDates = new ArrayList<>();
        for (String s: dates) {
            if (map.containsKey(s) == true) {
                newDates.add(s);
            }
        }
        return newDates;
    }

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
                String[] spilt = strDate.split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD, 2);
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
        String[] leftTimeSplit = leftSplit[0].split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD);
        String right = rhs.getText().replaceFirst(textStart, "");
        String[] rightSplit = right.split(newLine,2);
        String[] rightTimeSplit = rightSplit[0].split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD);

        if (leftTimeSplit[1].equals(twelveHourTimeAMPostFix) && rightTimeSplit[1].equals(twelveHourTimeAMPostFix)) {
            String[]leftTimeSplitHourMinute = leftTimeSplit[0].split(twelveHourTimeFormatSeparator);
            String[]rightTimeSplitHourMinute = rightTimeSplit[0].split(twelveHourTimeFormatSeparator);
            if (leftTimeSplitHourMinute[0].equals(twelveHourTimeHourBoundary)
                    && rightTimeSplitHourMinute[0].equals(twelveHourTimeHourBoundary)) {
                return leftTimeSplitHourMinute[1].compareTo(rightTimeSplitHourMinute[1]);
            } else if (leftTimeSplitHourMinute[0].equals(twelveHourTimeHourBoundary)) {
                return -1;
            } else if (rightTimeSplitHourMinute[0].equals(twelveHourTimeHourBoundary)) {
                return 1;
            } else {
                return leftTimeSplit[0].compareTo(rightTimeSplit[0]);
            }
        } else if (leftTimeSplit[1].equals(twelveHourTimeAMPostFix)) {
            return -1;
        } else if (rightTimeSplit[1].equals(twelveHourTimeAMPostFix)) {
            return 1;
        } else {
            String[]leftTimeSplitHourMinute = leftTimeSplit[0].split(twelveHourTimeFormatSeparator);
            String[]rightTimeSplitHourMinute = rightTimeSplit[0].split(twelveHourTimeFormatSeparator);
            if (leftTimeSplitHourMinute[0].equals(twelveHourTimeHourBoundary)
                    && rightTimeSplitHourMinute[0].equals(twelveHourTimeHourBoundary)) {
                return leftTimeSplitHourMinute[1].compareTo(rightTimeSplitHourMinute[1]);
            } else if (leftTimeSplitHourMinute[0].equals(twelveHourTimeHourBoundary)) {
                return -1;
            } else if (rightTimeSplitHourMinute[0].equals(twelveHourTimeHourBoundary)) {
                return 1;
            } else {
                return leftTimeSplit[0].compareTo(rightTimeSplit[0]);
            }
        }
    }

    public static WeekList getWeekList() {
        return weekList;
    }

    @Override
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage) {
        setListView(lookupTable, events);
        sortList();
        weekList = new WeekList(monList, tueList, wedList, thuList, friList, satList, sunList);
        return NO_FIELD;
    }
}