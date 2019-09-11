package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {

 
  @Test
  public void dummyTest() {
    Event task1 = new Event("meet Kartike.", "5/5/2019 1800");
    
    assertEquals(task1.description, "meet Kartike.");
    assertEquals(task1.date_at, "5th of May 2019, 6:00PM");
    assertEquals(task1.at, "5/5/2019 1800");

    Event task2 = new Event("meet Kartike.", "next week");
    assertEquals(task2.description, "meet Kartike.");
    assertEquals(task2.date_at, "next week");
    assertEquals(task2.at, "next week");

  }
}
