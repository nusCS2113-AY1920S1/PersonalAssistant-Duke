package seedu.duke.task;

import java.text.ParseException;
import java.util.ArrayList;

import seedu.duke.data.Schedule;
import seedu.duke.ui.Ui;

/**
 * A list of tasks that has a java ArrayList at its core. Contains methods that add, remove and perform operations on elements
 * of the list like mark as done.
 */
public class TaskList {
  protected ArrayList<Task> list = new  ArrayList<Task>();
  protected Ui ui = new Ui();
  protected Schedule schedule = new Schedule();
  
  public TaskList(ArrayList<Task> list) {
    this.list = list;
  }

  /**
   * Returns a task at a particular index.
   * 
   * @param index index of the task you want
   * @return task at that index which is an object of a descendent of class Task.
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
   * Adds a task to the ArrayList based on the task type and task description. Parses the description in case of event or deadline.
   * Handles exceptions.
   *
   * @param task_type the type of task eg. todo, event, deadline
   * @param task_description_full the description that follows the task type.
   */
  public void add (String task_type, String task_description_full) {
    // if tasktype is not ToDo
    if (task_type.equals("todo")) {
      list.add(new ToDo(task_description_full));
    } 
    else {
      // Extract task time and task description and initialize as deadline
      if (task_type.equals("deadline")) {
        try {
          String task_description = task_description_full.split("/", 2)[0];
          String task_time = task_description_full.split("/", 2)[1].substring(3);
          String taskDateOnly = task_time.split(" ", 2)[0];
          list.add(new Deadline(task_description, task_time));
          if (Schedule.isValidDate(taskDateOnly)) {
            schedule.addToSchedule(task_description, schedule.convertStringToDate(taskDateOnly));
          }
        }
        // if /by is not included in deadline command
        catch (ArrayIndexOutOfBoundsException e) {
          ui.wrong_description_error();
          return;
        } catch (ParseException e) {
          return;
        }
      }
      // Extract task time and task description and initialize as event
      else if (task_type.equals("event")) {
        try {
          String task_description = task_description_full.split("/", 2)[0];
          String task_time = task_description_full.split("/", 2)[1].substring(3);
          String taskDateOnly = task_time.split(" ", 2)[0];
          list.add(new Event(task_description, task_time));
          if (Schedule.isValidDate(taskDateOnly)) {
            schedule.addToSchedule(task_description, schedule.convertStringToDate(taskDateOnly));
          }
        }
        // if /at is not included in event command
        catch (ArrayIndexOutOfBoundsException e) {
          ui.wrong_description_error();
          return;
        } catch (ParseException e) {
          return;
        }
      }
      else {
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
      System.out.println("\t_____________________________________");
      System.out.println("\tNice! I've marked this task as done:");
      System.out.println("\t  " + (i + 1) + "." + list.get(i).toString());
      System.out.println("\t_____________________________________\n\n");
    }
    // Catch exception if wrong task id is done
    catch (IndexOutOfBoundsException e) {
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
      Task last_task = list.get(i);
      list.remove(i);
      System.out.println("\t_____________________________________");
      System.out.println("\tNoted. I have removed this task:");
      System.out.println("\t  " + (i + 1) + "." + last_task.toString());
      System.out.println("\tNow there are " + list.size() + " tasks left.");
      System.out.println("\t_____________________________________\n\n");
    }
    catch (IndexOutOfBoundsException e) {
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
   * @param task_description a character sequence from which tasks will be found.
   */
  public void findTask(String task_description) {
    ArrayList<Integer> matching_tasks = new ArrayList<Integer>();
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getDescription().contains(task_description)) {
        matching_tasks.add(Integer.valueOf(i));
      }
    }
    if (matching_tasks.isEmpty()) {
      ui.task_doesnt_exist_error();
      return;
    }
    System.out.println("\t_____________________________________");
    System.out.println("\tFound " + matching_tasks.size() + ". Here you go.");
    for (Integer id : matching_tasks) {
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
