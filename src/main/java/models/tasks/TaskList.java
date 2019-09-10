package models.tasks;

import java.io.Serializable;
import java.util.ArrayList;

public class TaskList implements Serializable {
    private ArrayList<ITask> listOfTasks;
    private ArrayList<ITask> searchedTasks;

    public TaskList() {
        listOfTasks = new ArrayList<>();
    }

    public void addToList(ITask newTask) {
        this.listOfTasks.add(newTask);
    }

    public void deleteFromList(ITask oldTask) {
        listOfTasks.remove(oldTask);
    }

    public ArrayList<ITask> getAllTasks() {
        return this.listOfTasks;
    }

    public ITask getTask(int index) {
        return this.listOfTasks.get(index);
    }

    public int getNumOfTasks() {
        return this.listOfTasks.size();
    }

    /**
     * Method that returns tasks that match with search keywords.
     * @param input : Keyword that users wish to search for
     * @return : Returns an ArrayList of ITask that matches keyword
     */
    public ArrayList<ITask> getSearchedTasks(String input) {
        String [] allinputs = input.split(" ");
        searchedTasks = new ArrayList<>();
        for (ITask task : listOfTasks) {
            if (task.getDescription().contains(allinputs[1])) {
                searchedTasks.add(task);
            }
        }
        return searchedTasks;
    }
}
