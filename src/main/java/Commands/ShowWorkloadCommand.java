package Commands;

import Commons.LookupTable;
import Commons.Storage;
import Commons.UserInteraction;
import Tasks.Assignment;
import Tasks.TaskList;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;


public class ShowWorkloadCommand extends Command {
    private String week;
    private Integer[] counter = {0,0,0,0,0,0,0};
    private LookupTable lookupTable = LookupTable.getInstance();
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dayFormatter = new SimpleDateFormat("E");

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
            Date tempDate = dateFormatter.parse(date.getKey());
            String tempDay = dayFormatter.format(tempDate);
            Integer index = dayToInt(tempDay);
            counter[index - 1]++;
        }
        Integer num = findMinimum(counter, limit) + 1;
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
     * This method converts day to integer format.
     * @param day Day of task
     * @return Integer format of day of task
     */
    private int dayToInt(String day) {
        if (day.equals("Mon")) {
            return 1;
        } else if (day.equals("Tue")) {
            return 2;
        } else if (day.equals("Wed")) {
            return 3;
        } else if (day.equals("Thu")) {
            return 4;
        } else if (day.equals("Fri")) {
            return 5;
        } else if (day.equals("Sat")) {
            return 6;
        } else {
            return 7;
        }
    }

    @Override
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage) throws ParseException, FileNotFoundException {
        String workloadWeek = lookupTable.getValue(week);
        HashMap<String, HashMap<String, ArrayList<Assignment>>> eventMap = events.getMap();
        HashMap<String, HashMap<String, ArrayList<Assignment>>> deadlineMap = deadlines.getMap();
        HashMap<String, ArrayList<Assignment>> workloadMap = new HashMap<>();

        for (Map.Entry<String, HashMap<String, ArrayList<Assignment>>> eventModule: eventMap.entrySet()) {
            HashMap<String, ArrayList<Assignment>> eventModuleValue = eventModule.getValue();
            for (Map.Entry<String, ArrayList<Assignment>> eventItem: eventModuleValue.entrySet()) {
                String[] strDayDate = eventItem.getKey().trim().split(" ");
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
                String[] strDayDate = deadlineItem.getKey().trim().split(" ");
                String strDate = strDayDate[1].trim();
                String selectedWeek = lookupTable.getValue(strDate);
                if (isValid(workloadWeek, selectedWeek)) {
                    Date tempDate = dateFormatter.parse(strDate);
                    String tempDay = dayFormatter.format(tempDate);
                    int limit = dayToInt(tempDay);
                    int freeDay = findBestDay(workloadMap, limit - 1);
                    int bestDay = dayToInt(tempDay) - freeDay;
                    tempDate = new Date(tempDate.getTime() - bestDay * 24 * 60 * 60 * 1000);
                    String newDate = dateFormatter.format(tempDate).trim();
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
