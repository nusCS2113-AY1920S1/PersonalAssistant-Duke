package duke;

import java.time.LocalDateTime;
import duke.tasks.Deadline;
import duke.tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Class used to store the task list and perform necessary manipulations to the task list such as
 * adding tasks, removing tasks and finding tasks based on keywords, as well as obtaining the size
 * of the task list.
 */
public class TaskList {

    private ArrayList<Task> list = new ArrayList<>();
    public  Task get_first_e(String[] string_list,int first) throws DukeException{
        Task c1;
        //System.out.println("help me");
        //System.out.println("here "+Arrays.toString(string_list));
        try{
            //System.out.println(Arrays.toString(string_list));
            String[] timing = string_list[1].split("-");
            if(timing.length>= 2 && !(timing[1].trim().equals("")) ){
                //System.out.println(Arrays.toString(timing));
                LocalDateTime start_date = new ParseTime().parseStringToDate(timing[0].trim());
                LocalDateTime end_date =  new ParseTime().parseStringToDate(timing[1].trim());

                c1 = new duke.tasks.Event(string_list[0],start_date,end_date,timing[0],timing[1]);
                //System.out.println("Before : " + c1);
                if(first == 0 ){
                  /*  if(conflict_checker.is_conflict((Event)c1)){
                        throw new DukeException("there is a conflict in the timings, you already have an event that overlap!");
                    }

                   */
                }
            }
            else{
                throw new DukeException("Please give a starting and ending time!");
            }
        }
        catch (DukeTimeException e){
            String[] timing = string_list[2].split("-");
            if(timing.length>= 2 && !(timing[1].trim().equals("")) ){
                c1 = new duke.tasks.Event(string_list[1],timing[0],timing[1]);
            }
            else{
                throw new DukeException("Please give a starting and ending time!");
            }
        }
        return c1;
    }
    /**
     * Removes an element from the task list.
     *
     * @param index the index of the duke.tasks.Task in the task list that is to be removed
     */
    public void remove(int index) {
        list.remove(index);
    }

    /**
     * Adds an element to the task list.
     *
     * @param t the duke.tasks.Task object to be added to the task list.
     */
    public void add(Task t) {
        list.add(t);
    }

    /**
     * Returns a subset of the task list (implemented as an ArrayList of duke.tasks.Task objects) that contains
     * the query specified in the argument.
     *
     * @param query the search query to be obtained from the input command
     * @return the ArrayList of duke.tasks.Task objects whose description contained the query
     */
    public ArrayList<Task> findTask(String query) {
        ArrayList<Task> result = new ArrayList<>();
        for (Task t: list) {
            if (t.getDescription().contains(query)) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Returns the current size of the task list.
     *
     * @return the current size of the task list.
     */
    public int getSize() {
        return list.size();
    }

    public ArrayList<Task> getTasksDueSoon() {
        LocalDateTime now = LocalDateTime.now();
        ArrayList<Task> result = new ArrayList<>();
        for (Task t: list){
            if (t instanceof Deadline){
                if ((((Deadline) t).getByLDT() != null) && (Duration.between(((Deadline) t).getByLDT(), now).toSeconds() <= 10800)){ // 3 hours
                    result.add(t);
                }
            }
        }
        return result;
    }


    /**
     * Returns the task list for duke.Duke, which is implemented as an ArrayList of duke.tasks.Task objects.
     *
     * @return the task list
     */
    public ArrayList<Task> getTaskList() {
        return list;
    }

}
