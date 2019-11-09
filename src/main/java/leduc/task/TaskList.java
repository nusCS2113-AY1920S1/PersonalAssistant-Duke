package leduc.task;

import jdk.jfr.Event;
import leduc.Date;
import leduc.exception.ConflictDateException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the list of tasks.
 */
public class TaskList {
    private List<Task> tasks;

    /**
     * Constructor of tasks list.
     * @param tasks Arraylist of tasks.
     */
    public TaskList(List<Task> tasks){
        this.tasks = tasks;
    }

    /**
     * Returns the task in the position i of the tasks list.
     * @param i position of the task in the tasks list to returns.
     * @return the task in the position i of the tasks list.
     */
    public Task get(int i ){
        return this.tasks.get(i);
    }

    /**
     * Add a task to the tasks list.
     * @param t the task to add.
     */
    public void add(Task t ){
        this.tasks.add(t);
    }

    /**
     * Returns the size of the tasks list.
     * @return size of the tasks list.
     */
    public int size(){
        return this.tasks.size();
    }

    /**
     * Returns the task which is removed from the tasks list at the position i.
     * @param i the position of the task to remove in the tasks list.
     * @return the task which is removed from the tasks list at the position i.
     */
    public Task remove( int i){
        return this.tasks.remove(i);
    }

    /**
     * Returns the String of display of one element of the list of tasks.
     * @param index the position of the task to display in the tasks list.
     * @return the String of display of one element of the list of tasks.
     */
    public String displayOneElementList(int index){
        Task t = this.tasks.get(index);
        String result = "\t "+ (index+1) + ". " + t.toString() + "\n";
        return result;
    }

    /**
     * get the list
     * @return the task list
     */
    public ArrayList<Task> getList(){
        return (ArrayList<Task>) this.tasks;
    }

    /**
     * Setter of the list
     */
    public void setList(ArrayList<Task> task){
        this.tasks= task;
    }

    /**
     * Verify if there are event that are in conflict with the date
     * @param date1 the start date
     * @param date2 the end date
     * @throws  ConflictDateException Exception thrown when the new event is in conflict with others event.
     */
    public void verifyConflictDate(Date date1, Date date2) throws ConflictDateException {
        ArrayList<Task> conflictTasks = new ArrayList<>();
        for (Task t : tasks){
            if(t.isEvent()){
                if(date1.getDate().isAfter(((EventsTask)t).getDateFirst().getDate()) && date1.getDate().isBefore(((EventsTask)t).getDateSecond().getDate())){
                    conflictTasks.add(t);
                }
                else if(date2.getDate().isAfter(((EventsTask)t).getDateFirst().getDate()) && date2.getDate().isBefore(((EventsTask)t).getDateSecond().getDate())){
                    conflictTasks.add(t);
                }
                else if(date1.getDate().isBefore(((EventsTask)t).getDateFirst().getDate()) && date2.getDate().isAfter(((EventsTask)t).getDateSecond().getDate())){
                    conflictTasks.add(t);
                }
                else if(date1.getDate().equals(((EventsTask)t).getDateFirst().getDate()) || date1.getDate().equals(((EventsTask)t).getDateSecond().getDate()) ||  date2.getDate().equals(((EventsTask)t).getDateSecond().getDate()) || date2.getDate().equals(((EventsTask)t).getDateFirst().getDate())){
                    conflictTasks.add(t);
                }
            }
        }
        if(!conflictTasks.isEmpty()){
            throw new ConflictDateException(conflictTasks);
        }
    }

    /**
     * Verify if there are event(without the task to be edited) that are in conflict with the date
     * @param date1 the start date
     * @param date2 the end date
     * @throws  ConflictDateException Exception thrown when the new event is in conflict with others event.
     */
    public void verifyConflictDateEdit(Date date1, Date date2, EventsTask editTask) throws ConflictDateException {
        ArrayList<Task> conflictTasks = new ArrayList<>();
        for (Task t : tasks){
            if(t.isEvent()){
                boolean isNotEditTask = t.getPriority()!=editTask.getPriority() || t.getMark()!= editTask.getMark() || ((EventsTask) t).getDateFirst()!= editTask.getDateFirst() || ((EventsTask)t ).getDateSecond() != editTask.getDateSecond() || t.getTag()!=editTask.getTag() || t.getTask()!=editTask.getTask();
                if (isNotEditTask) {
                    if (date1.getDate().isAfter(((EventsTask) t).getDateFirst().getDate()) && date1.getDate().isBefore(((EventsTask) t).getDateSecond().getDate())) {
                        conflictTasks.add(t);
                    } else if (date2.getDate().isAfter(((EventsTask) t).getDateFirst().getDate()) && date2.getDate().isBefore(((EventsTask) t).getDateSecond().getDate())) {
                        conflictTasks.add(t);
                    } else if (date1.getDate().isBefore(((EventsTask) t).getDateFirst().getDate()) && date2.getDate().isAfter(((EventsTask) t).getDateSecond().getDate())) {
                        conflictTasks.add(t);
                    } else if (date1.getDate().equals(((EventsTask) t).getDateFirst().getDate()) || date1.getDate().equals(((EventsTask) t).getDateSecond().getDate()) || date2.getDate().equals(((EventsTask) t).getDateSecond().getDate()) || date2.getDate().equals(((EventsTask) t).getDateFirst().getDate())) {
                        conflictTasks.add(t);
                    }
                }
            }
        }
        if(!conflictTasks.isEmpty()){
            throw new ConflictDateException(conflictTasks);
        }
    }

    /**
     * Extracts all Todo's into a seperate arraylist. Tasks with/without dates must be separated prior to sorting
     * @param  tasks is the list of tasks
     */
    public  ArrayList<Task> extractTodo(TaskList tasks){
        ArrayList<Task> extractedTodos = new ArrayList<Task>();
        for (int i = 0; i < tasks.size(); i++){
            if ((tasks.get(i) instanceof TodoTask)){
                extractedTodos.add(tasks.get(i));
            }
        }
        return extractedTodos;
    }
    /**
     * Extracts all EventsTask/HomeworkTasks into a seperate arraylist. Tasks with/without dates must be seperated prior to sorting
     *@param tasks tasks is the list of tasks
     */
    public  ArrayList<Task> filterTasks(TaskList tasks){
        ArrayList<Task> filteredTasklist = new ArrayList<Task>();
        for (int i = 0; i < tasks.size(); i++){
            if (!(tasks.get(i) instanceof TodoTask)){
                filteredTasklist.add(tasks.get(i));
            }
        }
        return filteredTasklist;
    }
    /**
     * Sorts the list of tasks by date.
     * @param filteredTasklist which filters out all Tasks that do not have a date field..
     * @param extractedTodos which is a list of all Todo objects, will get appended to the final sorted list.
     */
    public  ArrayList<Task> sort(ArrayList<Task> filteredTasklist, ArrayList<Task> extractedTodos){
        ArrayList<Task> sortedTasks = new ArrayList<Task>();
        for(int i = 0; filteredTasklist.size() > 0; i++){
            Task initialTask = filteredTasklist.get(0);//set initial task
            for(int j = 0; j < filteredTasklist.size(); j++){
                if((filteredTasklist.get(j).getDate().compareTo(initialTask.getDate())) < 0) {//compare each date to initialdate
                    initialTask = filteredTasklist.get(j);//update if necessary
                }
            }
            sortedTasks.add(initialTask);
            filteredTasklist.remove(initialTask);
        }
        sortedTasks.addAll(extractedTodos);
        return sortedTasks;
    }
}
