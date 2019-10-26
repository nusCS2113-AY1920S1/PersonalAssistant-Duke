package duke.task;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


//@@author Dou-Maokang
/**
 * A test class to test the correctness of PriorityList class.
 */
public class PriorityListTest {

    @Test
    void setPriorityTest() {
        Integer[] array = {5, 5, 5, 5, 5};
        ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(array));
        PriorityList priorityList = new PriorityList(list);
        assertEquals("5 5 5 5 5 ", priorityList.toString());

        priorityList.setPriority(1, 1);
        assertEquals("1 5 5 5 5 ", priorityList.toString());

        priorityList.setPriority(2, 2);
        assertEquals("1 2 5 5 5 ", priorityList.toString());

        priorityList.setPriority(1, 5);
        assertEquals("5 2 5 5 5 ", priorityList.toString());
    }


    @Test
    void getPriorityTest() {
        Integer[] array = {1, 2, 3, 4, 5};
        ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(array));
        PriorityList priorityList = new PriorityList(list);
        assertEquals("1 2 3 4 5 ", priorityList.toString());

        assertEquals("2", String.format("%d", priorityList.getPriority(2)));

    }
}
//@@author