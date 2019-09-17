package seedu.duke.task;

import seedu.duke.ui.Ui;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * This task type inherits from Task. It specifies an event at a particular time.
 */
public class Event extends Task {
  protected String at;
  protected String date_at;
  private Ui ui = new Ui();

  public Event(String description, String at) {
    super(description);
    this.at = at;
    this.translate_date();
  }

  /**
   * Makes use of the DateTimeFormatter and LocalDateTime class to parse the user input date time and initializes the date_at member variable.
   */
  public void translate_date() {
    //  splitting date into individual components
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu HHmm");
    LocalDateTime parsedDate;
    try {
      parsedDate = LocalDateTime.parse(this.at, formatter);
    }
    catch (DateTimeParseException e) {
      ui.date_time_error(); 
      this.date_at = this.at;
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

    this.date_at = parsedDate.format(print_format);
  }

  /**
   * Overrides the toString method in Task to display task type and date time.
   *
   * @return a string with the target info.
   */
  public String toString() {
    return "[E]" + super.toString() + " (at: " + this.date_at + ")";
  }


  /**
   * Overrides the toSaveFormat function to include task type and date time.
   *
   * @return a string with pipe separated info.
   */
  public String toSaveFormat() {
    return "E|" + super.toSaveFormat() + "|" + this.at;
  }

  /**
   * Checks equality with another Event instance.
   *
   * @param temp the instance to compare against.
   * @return true or false to the comparison.
   */
  public boolean equals(Event temp) {
    if (this.description == temp.description && this.at == temp.at && this.date_at == temp.date_at) {
      return true; 
    }
    return false;
  }

  @Override
  public LocalDateTime getDateTime() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu HHmm");
    return LocalDateTime.parse(this.at, formatter);
  }

  @Override
  public void setDateTime(LocalDateTime ldt) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu HHmm");
    at = ldt.format(formatter);
    this.translate_date();
  }

  @Override
  public void setDateTime(String DateTime) {
    at = DateTime;
    this.translate_date();
  }
}
