package task;

import exception.DukeException;
import list.DegreeList;
import storage.Storage;

import java.io.File;
import java.util.*;
/*
@@author woblek
 */
/**
 * DegreeTask stores an arraylist of tasklists relevant for each degree programme
 * It adds all relevant tasks related to a particular degree, and puts them into the user's tasklist
 * it removes all tasks from the user's tasklist related to a removed degree
 */
public class DegreeTask extends Task {
    public static ArrayList<TaskList> fullDegreeTasklist = new ArrayList<TaskList>();

    //this map relates all the engineering programmes with an arbitrary integer
    private static final Map<String, Integer> degreeMap;
    static {
        Map<String, Integer> aMap = new HashMap<>();
        aMap.put("bme", 0);
        aMap.put("che", 1);
        aMap.put("cive", 2);
        aMap.put("come", 3);
        aMap.put("elece", 4);
        aMap.put("enve", 5);
        aMap.put("ise", 6);
        aMap.put("me", 7);
        aMap.put("mse", 8);
        degreeMap = Collections.unmodifiableMap(aMap);
    }


    /**
     *
     * @throws DukeException
     */
    public void loadDegreeTasks(List<String> taskDataRaw) throws DukeException {
        if(taskDataRaw == null)
            throw new DukeException("degreeTasks.txt file not found");
        String toTasklist = "";
        for (int i = 1; i < taskDataRaw.size(); i++) {
            if((taskDataRaw.get(i) != null) && (!taskDataRaw.get(i).equals("")) && (taskDataRaw.get(i).matches("^[a-zA-Z]*$"))){
                TaskList thisList = new TaskList(toTasklist);
                fullDegreeTasklist.add(thisList);
                toTasklist = "";

            }
            else{
                toTasklist = toTasklist + taskDataRaw.get(i) + "\n";
            }
        }
    }

    /**
     * appends all the tasks associated with each degreeName to the user's main tasklist
     * @param degreeName
     * @param userTasklist
     * @throws DukeException
     */
    public void addDegreeTasks (String degreeName, TaskList userTasklist) throws DukeException {
        int n = fullDegreeTasklist.get(degreeMap.get(degreeName)).size();
        if (n < 1){
            throw new DukeException("There are no tasks related to " + degreeName);
        }
        for (int i = 0; i < n; i++) {
            Task toAppend = fullDegreeTasklist.get(degreeMap.get(degreeName)).get(i);
            userTasklist.add(toAppend);
        }
        checkDuplicates(userTasklist);
    }

    public void removeDegreeTasks(String index, DegreeList userDegreeList, TaskList userTaskList) throws DukeException{
        String removedDegree =  userDegreeList.get(Integer.parseInt(index));
        System.out.println(removedDegree);
        TaskList removedTasklist = new TaskList();
        for (int i = 0; i < userTaskList.size(); i++){
            if (userTaskList.get(i).description.toLowerCase().contains(removedDegree)){
                removedTasklist.add(userTaskList.get(i));
                userTaskList.banishDelete(Integer.toString(i+1));
            }
        }

    }


    public void checkDuplicates (TaskList userTasklist){


    }
}
