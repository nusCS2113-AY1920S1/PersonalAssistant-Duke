import java.util.ArrayList;

public class TaskList  {
    protected ArrayList<Task> taskList;

    public TaskList(ArrayList<Task> task) {
        this.taskList = task;
    }

    public ArrayList<Task> fullTaskList(){
        return taskList;
    }

    public void addTask(Task t) {
        taskList.add(t);
    }

    public void deleteTask(Integer i) throws IndexOutOfBoundsException {
            taskList.remove(i - 1);
    }

    public Task getTask(int i) throws IndexOutOfBoundsException {
        return taskList.get(i - 1);
    }

    public int getSize() {
        return taskList.size();
    }

    public TaskList(){
        taskList = new ArrayList<Task>();
    }



}
