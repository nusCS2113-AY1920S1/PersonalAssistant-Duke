package duke.task;

import duke.enums.Numbers;

import java.util.ArrayList;
import javafx.util.Pair;

//@@author Dou-Maokang
/**
 * Represents a priority list that stores a list of priorities associated with each task.
 */
public class PriorityList {

    private ArrayList<Integer> priorityList;
    private int defaultPriority = Numbers.FIVE.value;

    /**
     * Creates an empty priority list using an array list.
     */
    public PriorityList() {
        priorityList = new ArrayList<>();
    }

    /**
     * Creates an updated priority list with the specified array list.
     *
     * @param list The array list of priorities.
     */
    public PriorityList(ArrayList<Integer> list) {
        priorityList = list;
    }


    /**
     * Add a priority associated with a task into the list.
     *
     * @param taskNum The number of the task in the task list.
     * @param priority The priority we want to set.
     * @return The updated Priority List.
     */
    public PriorityList setPriority(int taskNum, int priority) {

        priorityList.set(taskNum - Numbers.ONE.value, priority);

        return new PriorityList(priorityList);
    }

    /**
     * Add a default priority associated with a task into the list when the task is generated.
     *
     */
    public void addDefaultPriority() {
        priorityList.add(defaultPriority);
    }

    /**
     * Remove a priority when the associated task is removed.
     *
     * @param index The index of the priority to be removed.
     */
    public void remove(int index) {
        priorityList.remove(index);
    }


    /**
     * Get user input for the priority of a task.
     *
     * @param num The index number of the target priority.
     * @return The priority for a task.
     */
    public int getPriority(int num) {
        int priority = priorityList.get(num - 1);
        return priority;
    }


    /**
     * Get the length/size of the priority list.
     *
     * @return The size of the priority list.
     */
    public int getSize() {
        return priorityList.size();
    }

    /**
     * Get the list of priorities.
     *
     * @return The list of priorities.
     */
    public ArrayList<Integer> getList() {
        return this.priorityList;
    }


    /**
     * Sort tasks based on their priority.
     * @param taskList The list of tasks.
     * @param priorities The list of priorities.
     * @return The sorted list.
     */
    public static ArrayList<Pair> sortPriority(TaskList taskList, PriorityList priorities) {
        ArrayList<Pair> pairList = new ArrayList<>();
        for (int i = Numbers.ONE.value; i <= taskList.size(); i++) {
            Pair<Integer, Task> pair = new Pair<>(priorities.getPriority(i), taskList.get(i - 1));
            pairList.add(pair);
        }


        for (int i = Numbers.ZERO.value; i < taskList.size(); i++) {
            for (int j = Numbers.ZERO.value; j < taskList.size() - Numbers.ONE.value; j++) {
                if (((int) pairList.get(j).getKey()) > (int) pairList.get(j + Numbers.ONE.value).getKey()) {

                    Pair<Integer, Task> temp = pairList.get(j);
                    pairList.set(j, pairList.get(j + Numbers.ONE.value));
                    pairList.set(j + Numbers.ONE.value, temp);
                }
            }
        }
        return pairList;
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = Numbers.ZERO.value; i < priorityList.size(); i++) {
            output += priorityList.get(i) + " ";
        }

        return output;
    }

}
//@@author