package duke;

import duke.tasks.Task;
import duke.tasks.ToDo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UiTest {

    static String wrap(String content) {
        return ("\n__________________________________\n"
                + content
                + "\n__________________________________\n");
    }

    @Test
    public void testByeMessage() { // dummy test
        assertEquals(wrap("Bye. Hope to see you again soon!"), Ui.showByeMessage());
    }

    @Test
    public void testFormatList() {
        ArrayList<Task> list = new ArrayList<>();
        list.add(new ToDo("hello"));
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(i + 1)
                    .append(". ")
                    .append(list.get(i).toString());
            if (i != list.size() - 1) {
                result.append("\n");
            }
        }
        if (list.size() == 0) {
            result.append("The list is empty!");
        }
        assertEquals(Ui.wrap(result.toString()), wrap("1. [T][x] hello"));
    }
}
