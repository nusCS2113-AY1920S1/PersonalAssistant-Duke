package duke;
import java.util.ArrayList;
import java.time.LocalDateTime;
import duke.tasks.ToDo;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.TaskList;

public class Conflict_checker  { 
    protected TaskList taskArrayList;
    public Conflict_checker(TaskList task_array_list) {
        taskArrayList = task_array_list; 
    }
    public boolean is_conflict(Event check_task ){
        //System.out.println(check_task);
        ArrayList<Task> task_temp_list =  taskArrayList.getTaskList();
        for (Task temp : task_temp_list) {
            LocalDateTime start_date_check = check_task.get_start_date();
            LocalDateTime end_date_check = check_task.get_end_date(); 
            if(temp.get_type().equals("E")){
                //System.out.println("Hi i am checking for conflicts");
                Event temp_1 = (Event) temp;
                if(temp_1.has_date()){ 
                    LocalDateTime start_date = temp_1.get_start_date();
                    LocalDateTime end_date = temp_1.get_end_date();

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
                    if((start_date.isBefore(start_date_check) || start_date.isEqual(start_date_check)) && (end_date.isAfter(start_date_check) || end_date.isEqual(start_date_check))){ 
                        return true; 
                    }

                    if((start_date.isBefore(end_date_check) || start_date.isEqual(end_date_check)) && (end_date.isAfter(end_date_check) || end_date.isEqual(end_date_check))){ 
                        return true; 
                    }
                    if((start_date.isBefore(start_date_check) || start_date.isEqual(start_date_check)) && (end_date.isAfter(end_date_check) || end_date.isEqual(end_date_check))){ 
                        return true; 
                    }
                    if((start_date_check.isBefore(start_date) || start_date_check.isEqual(start_date)) && (end_date_check.isAfter(end_date) || end_date_check.isEqual(end_date))){ 
                        return true; 
                    }
                }
            }
        }
        return false;
    }   
}