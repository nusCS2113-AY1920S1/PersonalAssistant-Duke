package task;

import exception.DukeException;
import list.DegreeList;
import storage.Storage;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;
/*
@@author woblek
 */
/**
 * DegreeTask stores an arraylist of tasklists relevant for each degree programme
 * It adds all relevant tasks related to a particular degree, and puts them into the user's tasklist
 * it removes all tasks from the user's tasklist related to a removed degree
 */
public class UniversityTaskHandler {
    public static ArrayList<TaskList> fullDegreeTasklist = new ArrayList<TaskList>();

    // this map relates all the engineering programmes with an arbitrary integer
    private static final Map<String, Integer> degreeMap;
    static {
        Map<String, Integer> aMap = new HashMap<>();
        aMap.put("Biomedical Engineering", 0);
        aMap.put("Chemical Engineering", 1);
        aMap.put("Civil Engineering", 2);
        aMap.put("Computer Engineering", 3);
        aMap.put("Electrical Engineering", 4);
        aMap.put("Environmental Engineering", 5);
        aMap.put("Industrial Systems Engineering", 6);
        aMap.put("Mechanical Engineering", 7);
        aMap.put("Materials Science Engineering", 8);
        degreeMap = Collections.unmodifiableMap(aMap);
    }


    private static final Map<String, String> aliasMap;
    static {
        Map<String, String> aMap = new HashMap<>();
        aMap.put("Biomedical Engineering", "BME");
        aMap.put("Chemical Engineering", "CHE");
        aMap.put("Civil Engineering", "CIV");
        aMap.put("Computer Engineering", "CEG");
        aMap.put("Electrical Engineering", "EE");
        aMap.put("Environmental Engineering", "ENV");
        aMap.put("Industrial Systems Engineering", "ISE");
        aMap.put("Mechanical Engineering", "ME");
        aMap.put("Materials Science Engineering", "MSE");
        aliasMap = Collections.unmodifiableMap(aMap);
    }


    /**
     * takes in a list of strings of raw .txt data from degreeTasks.txt
     * builds an arraylist of Taskslists, each with tasks related to one degree programme
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
            if (!isDuplicate(toAppend, userTasklist)){
                userTasklist.add(toAppend);
            }

        }
        System.out.println("I've also added tasks related to " + degreeName + "\n");
    }

    /**
     * removes all tasks that are related to the degree programme that has been deleted
     * @param index
     * @param userDegreeList
     * @param userTaskList
     * @throws DukeException
     */
    public void removeDegreeTasks(String index, DegreeList userDegreeList, TaskList userTaskList) throws DukeException{
        Integer request = Integer.parseInt(index) - 1;
        String removedDegreeFull =  userDegreeList.get(request);
        String removedDegreeAlias = aliasMap.get(removedDegreeFull);
        ArrayList <Integer> deletionList = new ArrayList<>();
        for (int i = 0; i < userTaskList.size(); i++){
            if (userTaskList.get(i).description.contains(removedDegreeAlias)){
                deletionList.add(i);
            }
        }
        userTaskList.banishDelete(deletionList);
//        System.out.println("I've also removed tasks related to " + removedDegreeFull);
    }

    /**
     * returns true if the task that you are trying to add to the user's tasklist is a duplicate
     * @param task
     * @param userTasklist
     * @return
     * @throws DukeException
     */
    public boolean isDuplicate (Task task, TaskList userTasklist) throws DukeException {
        if (userTasklist.size() == 0){
            return false;
        }
        for (int i = 0; i < userTasklist.size(); i++){
            if (task.toList().equals(userTasklist.get(i).toList())){
                return true;
            }
        }
        return false;
    }
}
