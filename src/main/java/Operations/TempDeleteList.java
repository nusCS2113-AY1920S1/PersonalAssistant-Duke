package Operations;

import Model_Classes.Task;

import java.util.ArrayList;

public class TempDeleteList {
    private ArrayList<Task> tempDelete;

    public TempDeleteList(ArrayList<Task> tempDelete) {
        this.tempDelete = tempDelete;
    }

    public void add(Task task) {
        tempDelete.add(task);
    }

    public void restore(int index, TaskList taskList) {
        taskList.add(tempDelete.get(index));
    }
}
