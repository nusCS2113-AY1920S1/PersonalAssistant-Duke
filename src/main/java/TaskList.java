import java.util.ArrayList;

class TaskList {
    private ArrayList<ITask> listOfTasks;

    TaskList() {
        listOfTasks = new ArrayList<>();
    }

    void addToList(ITask newTask) {
        this.listOfTasks.add(newTask);
    }

    void showAllTasks() {
        for (int i = 0; i < this.listOfTasks.size(); i++) {
            System.out.print(i + 1);
            System.out.println(".[" + this.listOfTasks.get(i).getInitials() + "]"
                    + "[" + this.listOfTasks.get(i).getStatusIcon() + "] "
                    + this.listOfTasks.get(i).getDescription()
            );
        }
    }

    ITask getTask(int index) {
        return this.listOfTasks.get(index);
    }

    int getNumOfTasks() {
        return this.listOfTasks.size();
    }
}
