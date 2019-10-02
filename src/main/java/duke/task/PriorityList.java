package duke.task;

import duke.command.AddCommand;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Represents a priority list that stores a list of priorities associated with each task.
 */
public class PriorityList {
    private ArrayList<Integer> priorityList;

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
     * @param cmd The command used.
     * @return The updated Priority List.
     */
    public PriorityList addPriority(duke.command.Command cmd) {
        if (cmd instanceof AddCommand) {
            duke.ui.Ui ui = new duke.ui.Ui();
            ui.showLine();
            priorityList.add(this.getPriority());
        }


        return new PriorityList(priorityList);
    }


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
            try {
                priority = sc.nextInt();
                System.out.printf("     Set the priority to %d\n", priority);
                break;
            } catch (Exception e) {
                System.out.println("Wrong input! Please enter an integer between 1 and 5!");
            }
        }
        return priority;
    }


    /**
     * Get the length/size of the priority list
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
    public PriorityList clearList() {
        return new PriorityList(new ArrayList<Integer>());
    }


}
