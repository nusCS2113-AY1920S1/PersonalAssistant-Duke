package Commands;

import Commons.*;
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
    private static final Logger LOGGER = Logger.getLogger(WeekCommand.class.getName());
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

    private ArrayList<String> generateDateDay(String date, LookupTable LT){
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        ArrayList<String> temp = new ArrayList<>();
        for (String day : days) {
            String dateOut = day + " " + LT.getValue(date + " " + day);
            temp.add(dateOut);
        }
        return temp;
    }

    private ArrayList<String> checkIfExist(HashMap<String, ArrayList<Assignment>> map, ArrayList<String> dates) {
        ArrayList<String> newDates = new ArrayList<>();
        for(String s: dates){
            if(map.containsKey(s) == true) newDates.add(s);
        }
        return newDates;
    }

    private void updateList(String day, Text toShow){
        switch (day){
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
        }
    }

    private void sortList() {
        if(monList.size() != 0 ) monList.sort(WeekCommand::compareByTime);
        if(tueList.size() != 0 ) tueList.sort(WeekCommand::compareByTime);
        if(wedList.size() != 0 ) wedList.sort(WeekCommand::compareByTime);
        if(thuList.size() != 0 ) thuList.sort(WeekCommand::compareByTime);
        if(friList.size() != 0 ) friList.sort(WeekCommand::compareByTime);
        if(satList.size() != 0 ) satList.sort(WeekCommand::compareByTime);
        if(sunList.size() != 0 ) sunList.sort(WeekCommand::compareByTime);
    }

    private Text generateToShow(Assignment task) {
        Text toShow = new Text(task.toShow() + task.getModCode() + "\n" + task.getDescription());
        toShow.setFont(Font.font(10));
        if (task.getStatus()){
            toShow.setFill(Color.GAINSBORO);
            toShow.setStrikethrough(true);
        }
        return toShow;
    }

    /**
     * This method generates data in day GridPane ListViews based on the week selected
     */
    public void setListView(LookupTable LT, TaskList eventsList) {
        ArrayList<String> weekDates = generateDateDay(week, LT);
        for(String module: eventsList.getMap().keySet()) {
            HashMap<String, ArrayList<Assignment>> moduleValue = eventsList.getMap().get(module);
            ArrayList<String> dates = checkIfExist(moduleValue, weekDates);
            for(String strDate : dates) {
                String[] spilt = strDate.split(" ", 2);
                ArrayList<Assignment> data = moduleValue.get(strDate);
                for(Assignment task: data){
                    Text toShow = generateToShow(task);
                    String day = spilt[0];
                    updateList(day, toShow);
                }
            }
        }
    }

    /**
     * This method creates a comparator for a 12 hour time to be sorted by timeline.
     * @param lhs First item compared
     * @param rhs Second item compared
     * @return The result of the comparison
     */
    private static int compareByTime(Text lhs, Text rhs) {
        String left = lhs.getText().replaceFirst("Start: ", "");
        String[] leftSplit = left.split("\n",2);
        String[] leftTimeSplit = leftSplit[0].split(" ");
        String right = rhs.getText().replaceFirst("Start: ", "");
        String[] rightSplit = right.split("\n",2);
        String[] rightTimeSplit = rightSplit[0].split(" ");

        if(leftTimeSplit[1].equals("AM") && rightTimeSplit[1].equals("AM")){
            String[]leftTimeSplitHourMinute = leftTimeSplit[0].split(":");
            String[]rightTimeSplitHourMinute = rightTimeSplit[0].split(":");
            if(leftTimeSplitHourMinute[0].equals("12") && rightTimeSplitHourMinute[0].equals("12")) {
                return leftTimeSplitHourMinute[1].compareTo(rightTimeSplitHourMinute[1]);
            } else if(leftTimeSplitHourMinute[0].equals("12")) {
                return -1;
            } else if (rightTimeSplitHourMinute[0].equals("12")) {
                return 1;
            } else {
                return leftTimeSplit[0].compareTo(rightTimeSplit[0]);
            }
        } else if (leftTimeSplit[1].equals("AM")) {
            return -1;
        } else if (rightTimeSplit[1].equals("AM")) {
            return 1;
        } else {//PM PM situation
            //return leftSplit[0].compareTo(rightSplit[0]);
            String[]leftTimeSplitHourMinute = leftTimeSplit[0].split(":");
            String[]rightTimeSplitHourMinute = rightTimeSplit[0].split(":");
            if(leftTimeSplitHourMinute[0].equals("12") && rightTimeSplitHourMinute[0].equals("12")) {
                return leftTimeSplitHourMinute[1].compareTo(rightTimeSplitHourMinute[1]);
            } else if(leftTimeSplitHourMinute[0].equals("12")) {
                return -1;
            } else if (rightTimeSplitHourMinute[0].equals("12")) {
                return 1;
            } else {
                return leftTimeSplit[0].compareTo(rightTimeSplit[0]);
            }
        }
    }

    public static WeekList getWeekList(){
        return weekList;
    }

    @Override
    public String execute(LookupTable LT, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
//        String intWeek = week.replaceFirst("Week", "");
//        intWeek = intWeek.trim();
//        Integer duration = Integer.parseInt(intWeek);
//        if(duration < 1 || duration > 13) return ui.showWeeksInvalidEntry();
        setListView(LT, events);
        sortList();
        weekList = new WeekList(monList, tueList, wedList, thuList, friList, satList, sunList);
        return "";
    }
}