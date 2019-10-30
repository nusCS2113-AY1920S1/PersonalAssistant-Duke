package members;

import tasks.Task;

import java.util.ArrayList;

public class Member {

    /**
     * name of this member
     */
    public String name;

    /**
     * an int array list stores the index(s) of the in charge task(s)
     */
    public ArrayList<Integer> tasksInChargeIndex;

    public ArrayList<Task> tasksInCharge;


    /**
     * constructor
     * @param name name of the member
     */
    public Member(String name) {
        this.name = name;
        this.tasksInChargeIndex = new ArrayList<Integer>();
        this.tasksInCharge = new ArrayList<Task>();
    }

    /**
     * another constructor
     * @param name name of the member
     * @param tasksInChargeIndex the array list stores the index(s) of in charge task(s)
     */
    public Member(String name, ArrayList<Integer> tasksInChargeIndex) {
        this.name = name;
        this.tasksInChargeIndex = tasksInChargeIndex;
        this.tasksInCharge = new ArrayList<Task>();
        ArrayList<Member> pics = new ArrayList<Member>();
        for (int i = 0; i < tasksInChargeIndex.size(); i++) {
            int index = tasksInChargeIndex.get(i);
            tasksInCharge.add(Task.tasks.get(index - 1));
            pics = Task.tasks.get(index - 1).getPics();
            pics.add(this);
            Task.tasks.get(index - 1).setPics(pics);
        }
    }

    /**
     * a method to format the data list data store in file
     * @return a string which will show in data file that store the member list
     */
    public String dataString() {
        String data = name;
        for (int i = 0; i < tasksInChargeIndex.size(); i++) {
            data += " | " + tasksInChargeIndex.get(i);
        }
        return data;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Task> getTasksInCharge() {
        return this.tasksInCharge;
    }

    public ArrayList<Integer> getTasksInChargeIndex() {
        return this.tasksInChargeIndex;
    }

    public void setTask(int indexInList) {
        tasksInChargeIndex.add(indexInList);
        tasksInCharge.add(Task.tasks.get(indexInList - 1));
    }

    /**
     * a method to remove a task from the list of members
     * @param indexInList the index in the list of members
     */
    public void removeTask(int indexInList) {
        for (int i = 0; i < tasksInChargeIndex.size(); i++) {
            if (tasksInChargeIndex.get(i) == indexInList) {
                tasksInChargeIndex.remove(i);
                tasksInCharge.remove(Task.tasks.get(indexInList - 1));
            }
        }
    }


    @Override
    public String toString() {
        if (this.tasksInChargeIndex.size() <= 0) {
            return this.name + " is free.";
        } else {
            String tasksInChargeString = "[";
            int i = 0;
            for (i = 0; i < tasksInChargeIndex.size() - 1; i++) {
                tasksInChargeString += " " + tasksInChargeIndex.get(i) + ",";
            }
            tasksInChargeString += " " + tasksInChargeIndex.get(i) + " ]";
            return this.name + " is in charge of task(s): " + tasksInChargeString + ".";
        }
    }


    //@@author yuyanglin28
    /**
     * this is a method to update index in Member when deleting task
     */

    public void updateIndex() {
        tasksInChargeIndex.clear();
        for (int i = 0; i < tasksInCharge.size(); i++) {
            tasksInChargeIndex.add(Task.tasks.indexOf(tasksInCharge.get(i)) + 1);
        }
    }
}
