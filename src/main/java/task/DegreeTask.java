package task;

import exception.DukeException;
import storage.Storage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * DegreeTask stores an arraylist of tasklists relevant for each degree programme
 */
public class DegreeTask extends Task {
    private Storage storage;
    private ArrayList<TaskList> fullDegreeTasklist = new ArrayList<TaskList>();
    private static final String filename = "../data/degreeTasks.txt"; //text file for tasks related to degrees
    File file = new File(filename);


    /**
     * loads an arraylist of tasklists by reading the file degreeTasks.txt
     * @throws DukeException
     */
    public void loadDegreeTasks(List<String> taskDataRaw) throws DukeException {
        this.storage = new Storage("degreeTasks.txt");
        try {
            TaskList thisList = new TaskList(storage.getTaskList());
            fullDegreeTasklist.add(thisList);
        } catch (DukeException e) {
            throw new DukeException("Error Obtaining Degree Programme Events");
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
            for(int i = 0; i < fullDegreeTasklist.get(0).size(); i++){
                userTasklist.add(fullDegreeTasklist.get(0).get(i));
            }
        }
    }
}
