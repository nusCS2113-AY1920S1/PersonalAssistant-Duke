package seedu.duke.task;

import java.util.ArrayList;
import seedu.duke.ui.Ui;

/**
 * A list of tasks that has a java ArrayList at its core. Contains methods
 * that add, remove and perform operations on elements
 * of the list like mark as done.
 */
public class TaskList {
    /**
     * ArrayList of Tasks.
     */
    private ArrayList<Task> list = new  ArrayList<Task>();

    /**
     * Ui instance that communicates errors with the user.
     */
    private Ui ui = new Ui();

    /**
     * Initializes list.
     */
    public TaskList(ArrayList<Task> list) {
        this.list = list;
    }

    /**
     * Returns a task at a particular index.
     *
     * @param index index of the task you want
     * @return task at that index
     */
    public Task get(int index) {
        return list.get(index);
    }

    /**
     * Returns size of task list.
     *
     * @return length of tasklist.
     */
    public int size() {
        return list.size();
    }

    /**
     * Checks whether list is empty or not.
     *
     * @return true or false to whether the internal ArrayList is empty or not.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Adds a task to the ArrayList based on the task type and task description.
     * Parses the description in case of event or deadline.
     * Handles exceptions.
     *
     * @param taskType the type of task eg. todo, event, deadline
     * @param taskDescriptionFull the description that follows the task type.
     */
    public void add(String taskType, String taskDescriptionFull) {
        // if tasktype is not ToDo
        if (taskType.equals("todo")) {
            list.add(new ToDo(taskDescriptionFull));
        } else {
            // Extract task time and task description and initialize as deadline
            if (taskType.equals("deadline")) {
                try {
                    String taskDescription = taskDescriptionFull.split("/", 2)[0];
                    String taskTime = taskDescriptionFull.split("/", 2)[1].substring(3);
                    list.add(new Deadline(taskDescription, taskTime));
                } catch (ArrayIndexOutOfBoundsException e) {
                    ui.wrong_description_error();
                    return;
                }
            } else if (task_type.equals("doafter")) {
                try {
	            String task_description = task_description_full.split("/", 2)[0];
	            String after = task_description_full.split("/", 2)[1].substring(6);
	            boolean taskFound = false;
	            for (Task j: list) {
	                    if (j.description.equals(after)) {
	  		    list.add(new DoAfter(task_description, after));
			    taskFound = true;
			    break;
		        }
	            }
	            if (!taskFound) {
		        System.out.println();
		        System.out.println("        _____________________________________");
		        System.out.println("        Task: '" + after + "' not found!");
		        System.out.println("        _____________________________________");
		        System.out.println();
		        System.out.println();
		        return;
                    }
	        }
	        // if /after is not included in doafter command
                catch (ArrayIndexOutOfBoundsException e) {
	            ui.wrong_description_error();
	            return;
                }
            } else if (taskType.equals("event")) {
                try {
                    String taskDescription = taskDescriptionFull.split("/", 2)[0];
                    String taskTime = taskDescriptionFull.split("/", 2)[1].substring(3);
                    list.add(new Event(taskDescription, taskTime));
                } catch (ArrayIndexOutOfBoundsException e) {
                    ui.wrong_description_error();
                    return;
                }
            } else {
                ui.correct_command_error();
                return;
            }
        }

        String output = "\t  " + list.get(list.size() - 1).toString();
        System.out.println("\t_____________________________________");
        System.out.println("\tGot it. I've added this task:");
        System.out.println(output);  
        // Printing number of items in list
        System.out.println("\tNow you have " + list.size() + " tasks in the list.");
        System.out.println("\t_____________________________________\n\n");
    }

    /**
     * Returns the core ArrayList inside the TaskList object.
     *
     * @return ArrayList of Tasks.
     */
    public ArrayList<Task> return_list() {
        return list;
    }

    /**
     * Marks a task at index as done.
     *
     * @param i index of the task to mark as done.
     * @throws IndexOutOfBoundsException if an out of bounds index is requested.
     */
    public void doTask(int i) {
        try {
            list.get(i).markAsDone();
            if (list.get(i).isDone) {
                System.out.println("\t_____________________________________");
                System.out.println("\tNice! I've marked this task as done:");
                System.out.println("\t  " + (i + 1) + "." + list.get(i).toString());
                System.out.println("\t_____________________________________\n\n");
            }
        } catch (IndexOutOfBoundsException e) {
            ui.task_doesnt_exist_error();
        }
    }

    /**
     * Removes task at index.
     *
     * @param i index at which task is removed.
     * @throws IndexOutOfBoundsException if an out of bounds index is requested.
     */
    public void removeTask(int i) {
        try {
            final Task lastTask = list.get(i);
            list.remove(i);
            System.out.println("\t_____________________________________");
            System.out.println("\tNoted. I have removed this task:");
            System.out.println("\t  " + (i + 1) + "." + lastTask.toString());
            System.out.println("\tNow there are " + list.size() + " tasks left.");
            System.out.println("\t_____________________________________\n\n");
        } catch (IndexOutOfBoundsException e) {
            ui.task_doesnt_exist_error();
        }
    }

    /**
     * Displays the list of tasks contained in the object.
     */
    public void displayList() {
        // If user inputs list without appending list even once.
        if (list.isEmpty()) {
            ui.show_empty_list_error();
            return;
        }

        System.out.println("\t_____________________________________");
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("\t" + (i + 1) + "." + list.get(i).toString());
        }
        System.out.println("\t_____________________________________\n\n");
    }

    /**
     * Finds task which contains a character sequence supplied.
     *
     * @param taskDescription a character sequence from which tasks will be found.
     */
    public void findTask(String taskDescription) {
        ArrayList<Integer> matchingTasks = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDescription().contains(taskDescription)) {
                matchingTasks.add(Integer.valueOf(i));
            }
        }
        if (matchingTasks.isEmpty()) {
            ui.task_doesnt_exist_error();
            return;
        }
        System.out.println("\t_____________________________________");
        System.out.println("\tFound " + matchingTasks.size() + ". Here you go.");
        for (Integer id : matchingTasks) {
            System.out.println("\t  " + (id + 1) + "." + list.get(id).toString());
        }
        System.out.println("\t_____________________________________\n\n");
    }

    /**
     * Checks whether two instaces of TaskList are equal.
     *
     * @param temp TaskList instance to compare against.
     * @return true or false to the comparison.
     */
    public boolean equals(TaskList temp) {

        if (this.size() != temp.size()) {
            System.out.println("Length not equal");
            return false;  
        }

        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(temp.get(i))) {
                System.out.println(this.get(i).description + temp.get(i).description + "?");
                return false;
            }
        }
        return true;
    }
}
