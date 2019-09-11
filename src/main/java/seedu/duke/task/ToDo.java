package seedu.duke.task;

/**
 * A class that inherits from the abstract class Task. This task type is a simple todo that can be marked as done when done.
 */
public class ToDo extends Task {
  public ToDo(String description) {
    super(description);
  }
  
  /**
   * Overrides the method to display the task type along with inherited task string.
   *
   * @return details of the task in a user readable format.
   */
  public String toString() {
    return "[T]" + super.toString();
  }
  
  /**
   * Overrides the method to display the task type along with inherited task string save format.
   *
   * @return inherited string plus task format.
   */
  public String toSaveFormat() {
    return "T|" + super.toSaveFormat(); 
  }

}
