package task;

import exception.DukeException;
import storage.Storage;

import java.util.ArrayList;

/**
 * DegreeTask stores an arraylist of tasklists relevant for each degree programme
 */
public class DegreeTask extends Task {
    private Storage storage;
    private ArrayList<TaskList> fullDegreeTasklist = new ArrayList<TaskList>();


    /**
     * loads an arraylist of tasklists by reading the file degreeTasks.txt
     * @throws DukeException
     */
    public void loadDegreeTasks() throws DukeException {
        this.storage = new Storage("degreeTasks.txt");
        try {
            TaskList thisList = new TaskList(storage.load());
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
        loadDegreeTasks();
        if(degreeName.contains("ceg")){
            for(int i = 0; i < fullDegreeTasklist.get(0).size(); i++){
                userTasklist.add(fullDegreeTasklist.get(0).get(i));
            }
        }
    }
}
