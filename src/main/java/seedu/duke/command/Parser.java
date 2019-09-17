package seedu.duke.command;

import seedu.duke.task.TaskList;
import seedu.duke.ui.Ui;

/**
 * Takes raw user input as string, makes sense out of the input using regex and then performs operations based on the input.
 */
public class Parser {
  
  /**
   * Takes raw input and splits it into task type (eg. todo) and task description (eg. finish work). In cases like task type: list, bye,
   * the output array only contains task type.
   *
   * @param raw_input users single line string input
   * @return an array of Strings of length 2 or 1, either task type and task description or just task type based on raw input.
   */
  public String[] split (String raw_input) {
    String[] user_input = raw_input.split(" ", 2);
    return user_input;
  }
  
  /**
   * Takes raw user input and the current list of tasks and based on the user input performs operations like find, delete, done, list, add and bye.
   * Outputs a task list back after performing the operation.
   *
   * @param raw_input users single line string input
   * @param list existing list of tasks to perform operations upon
   * @return list after an operation has been performed
   * @see TaskList
   */
  public TaskList parse (String raw_input, TaskList list) {
    Ui ui = new Ui();
    String[] user_input = this.split(raw_input); 
    /* return output; */

    // If user inputs list
    if (user_input[0].equals("find")) {
      if (user_input.length == 1) {
        ui.empty_description_error();
      }
      else {
        list.findTask(user_input[1]);
      }
    }

    else if (user_input[0].equals("delete")) {
      if (user_input.length == 1) {
        ui.empty_description_error();
      }
      else {
        int task_id = Integer.parseInt(user_input[1]) - 1;
        list.removeTask(task_id);
      }
    }

    // list tasks
    else if (user_input[0].equals("list")) {
      list.displayList();
    }

    // Do task.
    else if (user_input[0].equals("done")) {
      // Extracting which task to do from the rest of the line after inputting only task type in line 20
      if (user_input.length == 1) {
        ui.empty_description_error(); 
      }
      else {
        int task_id = Integer.parseInt(user_input[1]) - 1;
        list.doTask(task_id);
      }
    }

    // Snooze task.
    else if (user_input[0].equals("snooze")) {
      if (user_input.length == 1) {
        ui.empty_description_error();
      }
      else {
        int task_id = Integer.parseInt(user_input[1]) - 1;
        list.snoozeTask(task_id);
      }
    }

    else {
      // add task to list
      if (user_input[0].equals("todo") || user_input[0].equals("deadline") || user_input[0].equals("event")) {
        if (user_input.length == 1) {
          ui.empty_description_error();
        }
        else {
          list.add(user_input[0], user_input[1]);
        }
      }
      else if (!user_input[0].equals("bye")) {
        ui.correct_command_error();
      }
    }
    return list;
  }
}
