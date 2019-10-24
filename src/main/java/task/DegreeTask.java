package task;

import exception.DukeException;
import storage.Storage;

import java.io.File;
import java.util.*;

/**
 * DegreeTask stores an arraylist of tasklists relevant for each degree programme
 */
public class DegreeTask extends Task {
    public static ArrayList<TaskList> fullDegreeTasklist = new ArrayList<TaskList>();

    //this map relates all the engineeering programmes with an arbitrary integer
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
     * loads an arraylist of tasklists by reading the file degreeTasks.txt
     * @throws DukeException
     */
    public void loadDegreeTasks(List<String> taskDataRaw) throws DukeException {
        if(taskDataRaw == null)
            throw new DukeException("degreeTasks.txt file not found");
        String delimiter = taskDataRaw.get(0);
        System.out.println(delimiter);
        String toTasklist = "";
        for (int i = 1; i < taskDataRaw.size(); i++) {
            if((taskDataRaw.get(i) != null) && (!taskDataRaw.get(i).equals("")) && (taskDataRaw.get(i).matches("^[a-zA-Z]*$"))){
                toTasklist = "";
                TaskList thisList = new TaskList(toTasklist);
                fullDegreeTasklist.add(thisList);

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
        if(degreeName.contains("ceg")){
            for(int i = 0; i < fullDegreeTasklist.get(degreeMap.get(degreeName)).size(); i++){
                userTasklist.add(fullDegreeTasklist.get(degreeMap.get(degreeName)).get(i));
            }
        }
    }
}
