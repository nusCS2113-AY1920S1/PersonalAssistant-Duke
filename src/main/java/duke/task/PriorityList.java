package duke.task;

import duke.command.AddCommand;
import duke.task.TaskList;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.util.Pair;

//@@author Dou-Maokang
/**
 * Represents a priority list that stores a list of priorities associated with each task.
 */
public class PriorityList {
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int FIVE = 5;

    private ArrayList<Integer> priorityList;
    private int defultPriority = FIVE;

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

        priorityList.set(taskNum - ONE, priority);

        return new PriorityList(priorityList);
    }

    /**
     * Add a default priority associated with a task into the list when the task is generated.
     *
     * @param cmd The command used to generate a new task;
     * @return The updated Priority List.
     */
    public PriorityList addDefaultPriority(duke.command.Command cmd) {
        if (cmd instanceof AddCommand) {
            priorityList.add(defultPriority);
        }
        return new PriorityList(priorityList);
    }

    /**
     * Add multiple default priority associated with a task into the list when these tasks are generated.
     *
     * @param numOfTimes The number of times that one task is repeated.
     * @return The updated Priority List.
     */
    public PriorityList addMultiDefaultPriority(int numOfTimes) {

        for (int i = ZERO; i < numOfTimes; i++) {
            priorityList.add(defultPriority);
        }

        return new PriorityList(priorityList);
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
     * Make the priority list empty.
     *
     * @return The empty priority list.
     */
    private PriorityList clearList() {
        return new PriorityList(new ArrayList<Integer>());
    }


    /**
     * Sort tasks based on their priority.
     * @param taskList The list of tasks.
     * @param priorities The list of priorities.
     * @return The sorted list.
     */
    public static ArrayList<Pair> sortPriority(TaskList taskList, PriorityList priorities) {
        ArrayList<Pair> pairList = new ArrayList<>();
        for (int i = ONE; i <= taskList.size(); i++) {
            Pair<Integer, Task> pair = new Pair<>(priorities.getPriority(i), taskList.get(i - 1));
            pairList.add(pair);
        }

        for (int i = ONE; i < taskList.size(); i++) {
            for (int j = i; j > ZERO; j--) {
                if (((int) pairList.get(j).getKey()) < (int) pairList.get(j).getKey()) {
                    Pair<Integer, String> temp = pairList.get(j);
                    pairList.set(j, pairList.get(j));
                    pairList.set(j, temp);
                } else {
                    break;
                }
            }
        }
        return pairList;
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = ZERO; i < priorityList.size(); i++) {
            output += priorityList.get(i) + " ";
        }

        return output;
    }

}
//@@author