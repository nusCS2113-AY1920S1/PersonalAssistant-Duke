package seedu.duke.task;

import java.util.ArrayList;

public class DetectAnomalies {


    /**
     * Test if there is any clashes.
     * @return true or false.
     */
    public static Boolean test(Task task, ArrayList<Task> list) {
        boolean check = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDescription().equals(task.getDescription())) {
                if (list.get(i).getDateTime().equals(task.getDateTime())) {
                    check = true;
                    System.out.println("true true true !!!!!!");
                }
                System.out.println(list.get(i).getDescription());
                System.out.println(list.get(i).getDateTime());
                System.out.println(task.getDescription());
                System.out.println(task.getDateTime());
            }
        }
        if(!check){
            System.out.println("false false false!!!!!");
        }
        return check;
    }
}


