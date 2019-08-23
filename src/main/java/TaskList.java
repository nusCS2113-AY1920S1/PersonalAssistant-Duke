import java.util.ArrayList;

class TaskList {
    private ArrayList<ToDos> listOfToDos;
    private ArrayList<Deadline> listOfDeadlines;
    private ArrayList<Event> listOfEvents;

    TaskList() {
        listOfToDos = new ArrayList<>();
        listOfDeadlines = new ArrayList<>();
        listOfEvents = new ArrayList<>();
    }

    void addToToDos(ToDos newToDos) {
        listOfToDos.add(newToDos);
    }

    void showAllTasks() {
        for (int i = 0; i < listOfToDos.size(); i++) {
            System.out.print(i + 1);
            System.out.println(".[" + listOfToDos.get(i).getStatusIcon() + "] " + listOfToDos.get(i).getDescription());
        }
    }

    ToDos getTask(int index) {
        return listOfToDos.get(index);
    }
}
