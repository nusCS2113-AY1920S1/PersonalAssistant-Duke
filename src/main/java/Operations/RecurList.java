package Operations;

import CustomExceptions.DukeException;
import Model_Classes.Task;

import java.util.ArrayList;

public class RecurList {
    private static ArrayList<Task> tasks;

    public RecurList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task newTask) {
        tasks.add(newTask);
    }

    public void list() {
        int listCount = 1;
        for (Task output : tasks) {
            System.out.println("    " + listCount + ". " + output.toString());
            listCount += 1;
        }
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

    public static ArrayList<Task> currentList() {
        return tasks;
    }

    public void delete(int index) throws DukeException {
        tasks.remove(index - 1);
    }
}
