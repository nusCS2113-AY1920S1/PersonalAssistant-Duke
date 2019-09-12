package Model_Classes;

import CustomExceptions.DukeException;

import java.awt.desktop.SystemSleepEvent;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    public void add(Task newTask) {
        tasks.add(newTask);
    }
    public void delete(int index) throws DukeException {
        tasks.remove(index - 1);
    }
    public void list() {
        int listCount = 1;
        for (Task output : tasks) {
            System.out.println("    " + listCount + ". " + output.toString());
            listCount += 1;
        }
    }
    public void done(int index) throws DukeException{
        tasks.get(index - 1).setDone();
    }
    public void find (String key) {
        int queryCount = 1;
        for (Task query : tasks) {
            if (query.toString().toLowerCase().contains(key)) {
                System.out.println("    " + queryCount + ". " + query.toString());
            }
            queryCount += 1;
        }
        if (queryCount == 1) {
            System.out.println("    Your search returned no results.... Try searching with another keyword!");
        }
    }
    public ArrayList<Task> currentList() {
        return tasks;
    }
}
