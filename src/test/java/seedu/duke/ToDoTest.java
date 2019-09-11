package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {

  @Test
  public void dummyTest() {
    ToDo task = new ToDo("Finish work");
    assertEquals(task.description, "Finish work");
  }
}

