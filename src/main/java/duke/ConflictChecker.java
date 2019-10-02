package duke;


import duke.tasks.Event;
import duke.tasks.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ConflictChecker  {
    protected TaskList taskArrayList;

    public ConflictChecker(TaskList taskArrayListTemp) {
        taskArrayList = taskArrayListTemp;
    }

    /**
     *Checks if there is a conflict in a list and a given task.
     * @param checkTask the event item to be checked.
     * @return a boolean if there is a conflict or not.
     */
    public boolean is_conflict(Event checkTask) {
        //System.out.println(check_task);
        ArrayList<Task> taskTempList =  taskArrayList.getTaskList();
        for (Task temp : taskTempList) {
            LocalDateTime startDateCheck = checkTask.getStartDate();
            LocalDateTime endDateCheck = checkTask.getEndDate();
            if (temp.getType().equals("E")) {
                //System.out.println("Hi i am checking for conflicts");
                Event temp1 = (Event) temp;
                if (temp1.hasDate()) {
                    LocalDateTime startDate = temp1.getStartDate();
                    LocalDateTime endDate = temp1.getEndDate();

                    /*
                    1. if the start_date is before/equal to the start_date_check 
                    and if the  end_date is after/equal to  start_date_check
                            OR 
                    2. the start_date is before/equal to the end_date_check 
                    and the the end_date is  after/equal to end_date_check
                            OR 
                    3. the start_date is before/equal to the start_date_check and the 
                    the end_date is after/equal to the end_date_check 
                            OR 
                    4. the start_date_check is before/equal to the start_date and the 
                    end_date_check is after end_date 
                    */
                    //condition 1 
                    if ((startDate.isBefore(startDateCheck)
                            || startDate.isEqual(startDateCheck)) && (endDate.isAfter(startDateCheck)
                            || endDate.isEqual(startDateCheck))) {
                        return true; 
                    }

                    if ((startDate.isBefore(endDateCheck)
                            || startDate.isEqual(endDateCheck)) && (endDate.isAfter(endDateCheck)
                            || endDate.isEqual(endDateCheck))) {
                        return true; 
                    }
                    if ((startDate.isBefore(startDateCheck)
                            || startDate.isEqual(startDateCheck)) && (endDate.isAfter(endDateCheck)
                            || endDate.isEqual(endDateCheck))) {
                        return true; 
                    }
                    if ((startDateCheck.isBefore(startDate)
                            || startDateCheck.isEqual(startDate)) && (endDateCheck.isAfter(endDate)
                            || endDateCheck.isEqual(endDate))) {
                        return true; 
                    }
                }
            }
        }
        return false;
    }   
}
