package duke.task;

import duke.command.AddCommand;

import java.util.Scanner;
import java.util.ArrayList;

public class PriorityList {
    private ArrayList<Integer> priorityList;

    public PriorityList() {
        priorityList = new ArrayList<>();
    }

    public PriorityList(ArrayList<Integer> list) {
        priorityList = list;
    }

    public PriorityList addPriority(duke.command.Command cmd) {
        if (cmd instanceof AddCommand) {
            duke.ui.Ui ui = new duke.ui.Ui();
            ui.showLine();
            priorityList.add(this.getPriority());
        }


        return new PriorityList(priorityList);
    }

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


    public int getSize() {
        return priorityList.size();
    }

    public ArrayList<Integer> getList() {
        return this.priorityList;
    }

    public PriorityList clearList() {
        return new PriorityList(new ArrayList<Integer>());
    }


}
