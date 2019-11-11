package commands;

import commons.DukeConstants;
import commons.LookupTable;
import commons.Storage;
import commons.UserInteraction;
import tasks.Assignment;
import tasks.TaskList;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents the command which shows the user the recommended workload for next week.
 */
public class ShowWorkloadCommand extends Command {
    private String week;
    private Integer[] counter = {0,0,0,0,0,0,0};
    private LookupTable lookupTable = LookupTable.getInstance();
    private static final int HOURS = 24;
    private static final int MINUTES = 60;
    private static final int SECONDS = 60;
    private static final int MILLISECONDS = 1000;

    /**
     * Show recommended weekly workload.
     * @param week week to see workload
     */
    public ShowWorkloadCommand(String week) {
        this.week = week;
    }

    /**
     * This method checks if selected week is valid.
     * @param workloadWeek Week of workload schedule
     * @param selectedWeek Week of task
     * @return true if week of task is in week of workload schedule, false otherwise
     */
    private boolean isValid(String workloadWeek, String selectedWeek) {
        return selectedWeek.equals(workloadWeek);
    }

    /**
     * This method finds a day to schedule the deadline task.
     * @param workloadMap HashMap of workload
     * @param limit limit of days to be considered
     * @return Day to be scheduled in integer format
     * @throws ParseException when the user input the date in the wrong format
     */
    private int findBestDay(HashMap<String, ArrayList<Assignment>> workloadMap, Integer limit) throws ParseException {
        for (Map.Entry<String, ArrayList<Assignment>> date: workloadMap.entrySet()) {
            Date tempDate = DukeConstants.EVENT_DATE_INPUT_FORMAT.parse(date.getKey());
            String tempDay = DukeConstants.DAY_FORMAT.format(tempDate);
            int index = dayToInt(tempDay);
            counter[index - 1]++;
        }
        int num = findMinimum(counter, limit) + 1;
        return num;
    }

    /**
     * This method finds the day with the least number of tasks.
     * @param array Integer array to serve as counter
     * @param limit limit of days to be considered
     * @return index of the day in the array
     */
    private int findMinimum(Integer[] array, Integer limit) {
        int index = 0;
        Integer min = array[index];
        for (int i = 1; i < limit; i++) {
            if (array[i] < min) {
                min = array[i];
                index = i;
            }
        }
        return index;
    }

    /**
     * This method converts day to integer.
     * @param day Day of task
     * @return Integer of day of task
     */
    private int dayToInt(String day) {
        switch (day) {
        case "Mon":
            return 1;
        case "Tue":
            return 2;
        case "Wed":
            return 3;
        case "Thu":
            return 4;
        case "Fri":
            return 5;
        case "Sat":
            return 6;
        default:
            return 7;
        }
    }

    /**
     * Executes to get the workload for next week when requested by user.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the list message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display the workload message
     * @throws ParseException on array index out of bound
     */
    @Override
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage)
            throws ParseException {
        String workloadWeek = lookupTable.getValue(week);
        HashMap<String, HashMap<String, ArrayList<Assignment>>> eventMap = events.getMap();
        HashMap<String, HashMap<String, ArrayList<Assignment>>> deadlineMap = deadlines.getMap();
        HashMap<String, ArrayList<Assignment>> workloadMap = new HashMap<>();

        for (Map.Entry<String, HashMap<String, ArrayList<Assignment>>> eventModule: eventMap.entrySet()) {
            HashMap<String, ArrayList<Assignment>> eventModuleValue = eventModule.getValue();
            for (Map.Entry<String, ArrayList<Assignment>> eventItem: eventModuleValue.entrySet()) {
                String[] strDayDate = eventItem.getKey().trim().split(DukeConstants.BLANK_SPACE);
                String strDate = strDayDate[1].trim();
                String selectedWeek = lookupTable.getValue(strDate);
                if (selectedWeek.equals(workloadWeek)) {
                    workloadMap.put(strDate, eventItem.getValue());
                }
            }
        }
        for (Map.Entry<String, HashMap<String, ArrayList<Assignment>>> deadlineModule: deadlineMap.entrySet()) {
            HashMap<String, ArrayList<Assignment>> deadlineModuleValue = deadlineModule.getValue();
            for (Map.Entry<String, ArrayList<Assignment>> deadlineItem: deadlineModuleValue.entrySet()) {
                String[] strDayDate = deadlineItem.getKey().trim().split(DukeConstants.BLANK_SPACE);
                String strDate = strDayDate[1].trim();
                String selectedWeek = lookupTable.getValue(strDate);
                if (isValid(workloadWeek, selectedWeek)) {
                    Date tempDate = DukeConstants.EVENT_DATE_INPUT_FORMAT.parse(strDate);
                    String tempDay = DukeConstants.DAY_FORMAT.format(tempDate);
                    int limit = dayToInt(tempDay);
                    int freeDay = findBestDay(workloadMap, limit - 1);
                    int bestDay = dayToInt(tempDay) - freeDay;
                    tempDate = new Date(tempDate.getTime() - bestDay * HOURS * MINUTES * SECONDS * MILLISECONDS);
                    String newDate = DukeConstants.EVENT_DATE_INPUT_FORMAT.format(tempDate).trim();
                    if (workloadMap.containsKey(newDate)) {
                        for (int i = 0; i < deadlineItem.getValue().size(); i++) {
                            workloadMap.get(newDate).add(deadlineItem.getValue().get(i));
                        }
                    } else {
                        workloadMap.put(newDate, deadlineItem.getValue());
                    }
                }
            }
        }

        TreeMap<String, ArrayList<Assignment>> sortedWorkLoadMap = new TreeMap<>();
        if (!workloadMap.isEmpty()) {
            sortedWorkLoadMap.putAll(workloadMap);
        }
        return ui.showWorkload(sortedWorkLoadMap, workloadWeek);
    }
}
