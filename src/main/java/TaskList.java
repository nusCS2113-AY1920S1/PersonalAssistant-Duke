import java.util.ArrayList;

class TaskList {
    private ArrayList<Task> listOfTasks;

    TaskList() {
        listOfTasks = new ArrayList<>();
    }

    void addToTaskList(Task newTask) {
        listOfTasks.add(newTask);
    }

    void showAllTasks() {
        for (int i = 0; i < listOfTasks.size(); i++) {
            System.out.print(i + 1);
            System.out.println(".[" + listOfTasks.get(i).getStatusIcon() + "] " + listOfTasks.get(i).getDescription());
        }
    }

    Task getTask(int index) {
        return listOfTasks.get(index);
    }
}
