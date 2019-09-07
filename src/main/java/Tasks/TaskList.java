package Tasks;

import java.util.ArrayList;

public class TaskList {
    private static ArrayList<Task> checkList;

    //constructors
    public TaskList(ArrayList<Task> initialList) {
        checkList = initialList;
    }
    public TaskList(){
        checkList = new ArrayList<>();
    }

    public Task getTask(int index){
        return checkList.get(index);
    }

    public ArrayList<Task> getCheckList(){
        return checkList;
    }

    public void addTask(Task t){
        checkList.add(t);
    }

    public void removeTask(int index){
        checkList.remove(index);
    }

    public int lengthOfList(){
        return checkList.size();
    }

    public void markDoneATask(int index){
        checkList.get(index - 1).markAsDone();
    }

}
