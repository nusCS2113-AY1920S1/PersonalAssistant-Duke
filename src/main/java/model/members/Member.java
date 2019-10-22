package model.members;

import java.util.ArrayList;

public class Member {

    /**
     * name of this member
     */
    public String name;

    /**
     * an int array list stores the index(s) of the in charge task(s)
     */
    public ArrayList<Integer> tasksInCharge;

    /**
     * constructor
     * @param name name of the member
     */
    public Member(String name) {
        this.name = name;
        this.tasksInCharge = new ArrayList<Integer>();
    }

    /**
     * another constructor
     * @param name name of the member
     * @param tasksInCharge the array list stores the index(s) of in charge task(s)
     */
    public Member(String name, ArrayList<Integer> tasksInCharge) {
        this.name = name;
        this.tasksInCharge = tasksInCharge;
    }

    /**
     * a method to format the data list data store in file
     * @return a string which will show in data file that store the member list
     */
    public String dataString() {
        String data = name;
        for (int i = 0; i < tasksInCharge.size(); i++) {
            data += " | " + tasksInCharge.get(i);
        }
        return data;
    }

    public String getName() {
        return name;
    }

    public void setTask(int indexInList) {
        tasksInCharge.add(indexInList);
    }

    /**
     * a method to remove a task from the list of members
     * @param indexInList the index in the list of members
     */
    public void removeTask(int indexInList) {
        for (int i = 0; i < tasksInCharge.size(); i++) {
            if (tasksInCharge.get(i) == indexInList) {
                tasksInCharge.remove(i);
            }
        }
    }


    @Override
    public String toString() {
        if (this.tasksInCharge.size() <= 0) {
            return this.name + " is free.";
        } else {
            String tasksInChargeString = "[";
            int i = 0;
            for (i = 0; i < tasksInCharge.size() - 1; i++) {
                tasksInChargeString += " " + tasksInCharge.get(i) + ",";
            }
            tasksInChargeString += " " + tasksInCharge.get(i) + " ]";
            return this.name + " is in charge of task(s): " + tasksInChargeString + ".";
        }
    }
}
