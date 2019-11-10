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
 * NUSEvents stores an arraylist of tasklists relevant for each degree programme
 * It adds all relevant tasks related to a particular degree, and puts them into the user's tasklist
 * it removes all tasks from the user's tasklist related to a removed degree
 */
public class NUSEventList {
    public static ArrayList<TaskList> fullDegreeTasklist = new ArrayList<TaskList>();

    // this map relates all the engineering programmes with an arbitrary integer
    private static final Map<String, Integer> degreeMap;
    static {
        Map<String, Integer> aMap = new HashMap<>();
        aMap.put("BME", 0);
        aMap.put("CHE", 1);
        aMap.put("CIV", 2);
        aMap.put("CEG", 3);
        aMap.put("EE", 4);
        aMap.put("ENV", 5);
        aMap.put("ISE", 6);
        aMap.put("ME", 7);
        aMap.put("MSE", 8);
        degreeMap = Collections.unmodifiableMap(aMap);
    }


    private static final Map<String, String> aliasMap;
    static {
        Map<String, String> aMap = new HashMap<>();

        aMap.put("biomedical engineering", "BME");
        aMap.put("biomed", "BME");
        aMap.put("biomedical", "BME");
        aMap.put("bio eng", "BME");
        aMap.put("bm", "BME");
        aMap.put("bme", "BME");

        aMap.put("chemical engineering", "CHE");
        aMap.put("chem eng", "CHE");
        aMap.put("che", "CHE");

        aMap.put("cive", "CIV");
        aMap.put("civil engineering", "CIV");
        aMap.put("civil e", "CIV");
        aMap.put("civil", "CIV");
        aMap.put("civ", "CIV");

        aMap.put("computer engineering", "CEG");
        aMap.put("ceg", "CEG");
        aMap.put("come", "CEG");

        aMap.put("electrical engineering", "EE");
        aMap.put("ee", "EE");
        aMap.put("elece", "EE");

        aMap.put("environmental engineering", "ENV");
        aMap.put("enve", "ENV");
        aMap.put("env", "ENV");

        aMap.put("industrial and systems engineering", "ISE");
        aMap.put("ise", "ISE");
        aMap.put("ie", "ISE");

        aMap.put("mechanical engineering", "ME");
        aMap.put("mecheng", "ME");
        aMap.put("me", "ME");

        aMap.put("materials science and engineering", "MSE");
        aMap.put("mse", "MSE");
        aliasMap = Collections.unmodifiableMap(aMap);
    }


    /**
     * takes in a list of strings of raw .txt data from degreeTasks.txt
     * builds an arraylist of Taskslists, each with tasks related to one degree programme
     * @throws DukeException
     */
    public void loadEventLists(List<String> taskDataRaw) throws DukeException {
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
        String abbreviatedDegreeName = aliasMap.get(degreeName.toLowerCase());
        int degreeID = degreeMap.get(abbreviatedDegreeName);
        int n = fullDegreeTasklist.get(degreeID).size();
        if (n < 1){
            throw new DukeException("There are no tasks related to " + degreeName);
        }
        for (int i = 0; i < n; i++) {
            Task toAppend = fullDegreeTasklist.get(degreeID).get(i);
            if (!isDuplicate(toAppend, userTasklist)){
                toAppend.setNusDegreeName(abbreviatedDegreeName);
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
        String removedDegreeAlias = aliasMap.get(removedDegreeFull.toLowerCase());
        for (int i = (userTaskList.size()-1); i >= 0; i--){
            if(userTaskList.get(i).getNusDegreeName() == null){
                continue;
            }
            if (userTaskList.get(i).getNusDegreeName().matches(removedDegreeAlias)){
                userTaskList.remove(i);
            }
        }
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
