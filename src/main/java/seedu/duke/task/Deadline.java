package seedu.duke.task;

import seedu.duke.ui.Ui;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * This task type inherits from Task. It specifies a day before which a task should be completed.
 */
public class Deadline extends Task {
  protected String by;
  protected String date_by;
  private Ui ui = new Ui();

  public Deadline(String description, String by) {
    super(description);
    this.by = by;
    this.translate_date();
  }

  /**
   * Makes use of the DateTimeFormatter and LocalDateTime class to parse the user input date time and initializes the date_by member variable.
   */
  public void translate_date() {
    // splitting date into individual components
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu HHmm");
    LocalDateTime parsedDate;
    try {
      parsedDate = LocalDateTime.parse(this.by, formatter);
    }
    catch (DateTimeParseException e) {
      ui.date_time_error(); 
      this.date_by = this.by;
      return;
    }
   
    String suffix;
    switch (parsedDate.getDayOfMonth() % 10)
    {
      case 1:
        suffix = "st";
        break;
      case 2:
        suffix = "nd";
        break;
      case 3:
        suffix = "rd";
        break;
      default:
        suffix = "th";
        break;
    }

    if (parsedDate.getDayOfMonth() > 3 && parsedDate.getDayOfMonth() < 21) {
      suffix = "th";
    }

    DateTimeFormatter print_format = DateTimeFormatter.ofPattern("d'" + suffix + "' 'of' MMMM uuuu',' h:mma", Locale.ENGLISH);

    this.date_by = parsedDate.format(print_format);
  }
  
  /**
   * Overrides the toString method in Task to display task type and date time.
   *
   * @return a string with the target info.
   */
  public String toString() {
    return "[D]" + super.toString() + " (by: " + this.date_by + ")";
  }
  
  /**
   * Overrides the toSaveFormat function to include task type and date time.
   *
   * @return a string with pipe separated info.
   */
  public String toSaveFormat() {
    return "D|" + super.toSaveFormat() + "|" + this.by;
  }

  /**
   * Checks equality with another Deadline instance.
   *
   * @param temp the instance to compare against.
   * @return true or false to the comparison.
   */
  public boolean equals(Deadline temp) {
    if (this.description == temp.description && this.by == temp.by && this.date_by == temp.date_by) {
      return true; 
    }
    return false;
  }
}
