package Tasks;

import Commons.DukeConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * To keep track of the list of task input by user.
 */
public class TaskList {
    private static final String NO_FIELD = "void";

    private ArrayList<Assignment> list;
    private HashMap<String, HashMap<String, ArrayList<Assignment>>> map;
    private ArrayList<String> deadlineArrList = new ArrayList<>();
    private ArrayList<String> eventArrList = new ArrayList<>();

    /**
     * Creates a TaskList object.
     */
    public TaskList() {
        this.list = new ArrayList<>();
        this.map = new HashMap<>();
    }

    public ArrayList<Assignment> getList() {
        return list;
    }

    public HashMap<String, HashMap<String, ArrayList<Assignment>>> getMap() {
        return this.map;
    }

    /**
     * This method adds task into the arrayList list.
     */
    public void addTask(Assignment task) {
        if (this.map.containsKey(task.getModCode())) {
            if (!this.map.get(task.getModCode()).containsKey(task.getDate())) {
                map.get(task.getModCode()).put(task.getDate(), new ArrayList<>());
            }
        } else {
            this.map.put(task.getModCode(), new HashMap<>());
            this.map.get(task.getModCode()).put(task.getDate(), new ArrayList<>());
        }
        ArrayList<Assignment> taskArrayList = this.map.get(task.getModCode()).get(task.getDate());
        for (Assignment existingTask : taskArrayList) {
            String existingTaskDescription = existingTask.getDescription();
            String existingTaskDate = existingTask.getDateTime();
            String taskDescription = task.getDescription();
            String taskDate = task.getDateTime();
            if (!(existingTaskDescription.equals(taskDescription) || existingTaskDate.equals(taskDate))) {
                this.map.get(task.getModCode()).get(task.getDate()).add(task);
                this.list.add(task);
                return;
            }
        }
        this.map.get(task.getModCode()).get(task.getDate()).add(task);
        this.list.add(task);
    }

    /**
     * This method removes task from the map.
     */
    public void removeTask(Assignment task) {
        for (Assignment taskInList : this.map.get(task.getModCode()).get(task.getDate())) {
            if (taskInList.getDateTime().equals(task.getDateTime())) {
                this.map.get(task.getModCode()).get(task.getDate()).remove(taskInList);
                if (this.map.get(task.getModCode()).get(task.getDate()).size() == 0) {
                    this.map.get(task.getModCode()).remove(task.getDate());
                    if (this.map.get(task.getModCode()).size() == 0) {
                        this.map.remove(task.getModCode());
                    }
                }
                break;
            }
        }
    }

    /**
     * This method updates map with the task.
     */
    public void updateTask(Assignment task) {
        for (Assignment taskInList : this.map.get(task.getModCode()).get(task.getDate())) {
            if (taskInList.getDateTime().equals(task.getDateTime())) {
                Integer index = this.map.get(task.getModCode()).get(task.getDate()).indexOf(taskInList);
                Assignment temp = this.map.get(task.getModCode()).get(task.getDate()).get(index);
                temp.setDone(true);
                this.map.get(task.getModCode()).get(task.getDate()).remove(taskInList);
                this.map.get(task.getModCode()).get(task.getDate()).add(temp);
                break;
            }
        }
    }

    //Do not use this: User will input the task in the CLI
    public Assignment getTask(int index) {
        return this.list.get(index);
    }

    //Do not use this: Use toString method in Task
    public String taskToString(int index) {
        return list.get(index).toString();
    }

    /**
     * This method finds the size fo the taskList.
     */
    public int taskListSize() {
        int size = 0;
        for (String modCode : map.keySet()) {
            for (String date : map.get(modCode).keySet()) {
                for (Assignment task : map.get(modCode).get(date)) {
                    size++;
                }
            }
        }
        return size;
    }

    /**
     * This method sets reminder for a task.
     */
    public void setReminder(Assignment task, String time, boolean isReminder) {
        for (Assignment taskInList : this.map.get(task.getModCode()).get(task.getDate())) {
            if (taskInList.getDescription().equals(task.getDescription())) {
                if (isReminder) {
                    taskInList.setRemindTime(time);
                    taskInList.setReminder(true);
                    break;
                } else {
                    taskInList.setRemindTime(DukeConstants.NO_FIELD);
                    taskInList.setReminder(false);
                    break;
                }
            }
        }
    }
}