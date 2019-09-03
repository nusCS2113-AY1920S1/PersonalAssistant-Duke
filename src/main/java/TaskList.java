import java.util.ArrayList;

public class TaskList {

    protected ArrayList<Task> myList;

    public TaskList (ArrayList<Task> myList) {
        this.myList = myList;
    }

    //Method to get the tasks in a list
    public void getList() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < myList.size(); i++) { //Standard for-each loop: for (String element: myList)
            System.out.println((i + 1) + "." + myList.get(i).getStatusIcon());
        }
    }

    //Method to add a task to a list and output the size of list
    public void addToList(Task taskData) {
        myList.add(taskData);
        System.out.println("Got it. I've added this task:");
        System.out.println(taskData.getStatusIcon());
        System.out.println("Now you have " + myList.size() + " task(s) in the list.");
    }

    public void removeFromList(int taskNumber) {
        myList.remove(taskNumber);
    }
}
