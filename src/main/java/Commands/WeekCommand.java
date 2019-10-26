package Commands;

import Interface.LookupTable;
import Interface.Storage;
import Interface.Ui;
import Interface.Week;
import Tasks.Task;
import Tasks.TaskList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeekCommand extends Command {
    private static LookupTable LT;
    private static final Logger LOGGER = Logger.getLogger(WeekCommand.class.getName());
    static {
        try {
            LT = new LookupTable();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
    private Integer week;
    private final ObservableList<Text> monList = FXCollections.observableArrayList();
    private final ObservableList<Text> tueList = FXCollections.observableArrayList();
    private final ObservableList<Text> wedList = FXCollections.observableArrayList();
    private final ObservableList<Text> thuList = FXCollections.observableArrayList();
    private final ObservableList<Text> friList = FXCollections.observableArrayList();
    private final ObservableList<Text> satList = FXCollections.observableArrayList();
    private final ObservableList<Text> sunList = FXCollections.observableArrayList();
    public static Week weekList = new Week();

    public WeekCommand(Integer week) {
        this.week = week;
    }

    /**
     * This method generates data in day GridPane ListViews based on the week selected
     */
    public void setListView(LookupTable LT, TaskList eventsList) {
        for(Map.Entry<String, HashMap<String, ArrayList<Task>>> module: eventsList.getMap().entrySet()) {
            HashMap<String, ArrayList<Task>> moduleValue = module.getValue();
            for(Map.Entry<String, ArrayList<Task>> item: moduleValue.entrySet()) {
                String strDate = item.getKey();
                String[] spilt = strDate.split(" ", 2);
                String selectedWeek = LT.getValue(spilt[1]);

//                String[] spiltSelectedWeek = selectedWeek.split(" ");
//                Integer intSelectedWeek = Integer.parseInt(spiltSelectedWeek[1]);
//                if(intSelectedWeek == week) {
                if((selectedWeek).equals("Week " + week.toString())) {
                    ArrayList<Task> data = item.getValue(); // each item in data has the contents
                    for(Task task: data){
                        Text toShow = new Text(task.toShow() + task.getModCode() + "\n" + task.getDescription());
                        toShow.setFont(Font.font(10));
                        if (task.getStatus()){
                            toShow.setFill(Color.GAINSBORO);
                            toShow.setStrikethrough(true);
                        }
                        //toShow.wrappingWidthProperty().bind(monEventView.widthProperty().subtract(20));
                        String day = spilt[0];
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
                }
//                else if(intSelectedWeek> week) {
//                    break;
//                }
            }
        }
        if(monList.size() != 0 ) monList.sort(WeekCommand::compareByTime);
        if(tueList.size() != 0 ) tueList.sort(WeekCommand::compareByTime);
        if(wedList.size() != 0 ) wedList.sort(WeekCommand::compareByTime);
        if(thuList.size() != 0 ) thuList.sort(WeekCommand::compareByTime);
        if(friList.size() != 0 ) friList.sort(WeekCommand::compareByTime);
        if(satList.size() != 0 ) satList.sort(WeekCommand::compareByTime);
        if(sunList.size() != 0 ) sunList.sort(WeekCommand::compareByTime);
        weekList = new Week(monList, tueList, wedList, thuList, friList, satList, sunList);
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
        } else {
            return leftSplit[0].compareTo(rightSplit[0]);
        }
    }

    public static Week getWeekList(){
        return weekList;
    }


    @Override
    public String execute(LookupTable LT, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        setListView(LT, events);
        return "";
    }
}
