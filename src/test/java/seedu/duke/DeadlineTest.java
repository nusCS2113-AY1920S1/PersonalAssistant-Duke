package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

  @Test
  public void dummyTest() {
    Deadline task1 = new Deadline("meet Kartike.", "5/5/2019 1800");
    
    assertEquals(task1.description, "meet Kartike.");
    assertEquals(task1.date_by, "5th of May 2019, 6:00PM");
    assertEquals(task1.by, "5/5/2019 1800");

    Deadline task2 = new Deadline("meet Kartike.", "next week");
    assertEquals(task2.description, "meet Kartike.");
    assertEquals(task2.date_by, "next week");
    assertEquals(task2.by, "next week");
  }
}
