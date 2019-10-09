package duke.task;

import duke.command.AddCommand;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a priority list that stores a list of priorities associated with each task.
 */
public class PriorityList {
    private ArrayList<Integer> priorityList;
    private int defultPriority = 5;

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

        priorityList.set(taskNum - 1, priority);

        return new PriorityList(priorityList);
    }

    /**
     * Add a default priority associated with a task into the list when the task is generated;
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

        for (int i = 0; i < numOfTimes; i++) {
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
     * @return The priority for a task.
     */
    public int getPriority() {
        Scanner sc = new Scanner(System.in);

        System.out.println("     Enter the priority for the above added task: (1-high ~ 5-low)");
        int priority;
        while (true) {
            String input = sc.nextLine();
            try {
                priority = Integer.parseInt(input.trim());
                if ((1 <= priority) && (priority <= 5)) {
                    System.out.printf("     Set the priority to %d\n", priority);
                    break;
                } else {
                    System.out.println("Wrong input! Please enter an integer between 1 and 5!");
                }
            } catch (Exception e) {
                System.out.println("Wrong input! Please enter an integer between 1 and 5!");
                continue;
            }
        }
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

    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < priorityList.size(); i++) {
            output += priorityList.get(i) + " ";
        }

        return output;
    }

}
