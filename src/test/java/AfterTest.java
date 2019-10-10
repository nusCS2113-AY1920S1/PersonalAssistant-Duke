import duke.items.tasks.After;
import duke.items.tasks.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AfterTest {

    @org.junit.jupiter.api.Test
    void testToString_createAndMarkDoneNewTask_newDoAfterTaskCreatedAndMarkedDoneWithToStringFormat() {
        Task firstTask = new After("pack for holidays", "final examinations");
        assertEquals("[A][✗] pack for holidays (after: final examinations)", firstTask.toString());
        assertNotEquals("[A][✓] pack for holidays (after: final examinations)", firstTask.toString());
        firstTask.markDone();
        assertEquals("[A][✓] pack for holidays (after: final examinations)", firstTask.toString());
        assertNotEquals("[A][✗] pack for holidays (after: final examinations)", firstTask.toString());
        Task secondTask = new After("play with friends", "Friday 6pm");
        assertEquals("[A][✗] play with friends (after: Friday 6pm)", secondTask.toString());
        assertNotEquals("[A][✓] play with friends (after: Friday 6pm)", secondTask.toString());
        secondTask.markDone();
        assertEquals("[A][✓] play with friends (after: Friday 6pm)", secondTask.toString());
        assertNotEquals("[A][✗] play with friends (after: Friday 6pm)", secondTask.toString());
    }

    @org.junit.jupiter.api.Test
    void testStoreString_createAndMarkDoneNewTask_newDoAfterTaskCreatedAndMarkedDoneWithStoreStringFormat() {
        Task thirdTask = new After("pack for holidays", "final examinations");
        assertEquals("A | 0 | pack for holidays | final examinations", thirdTask.storeString());
        assertNotEquals("A | 1 | pack for holidays | final examinations", thirdTask.storeString());
        thirdTask.markDone();
        assertEquals("A | 1 | pack for holidays | final examinations", thirdTask.storeString());
        assertNotEquals("A | 0 | pack for holidays | final examinations", thirdTask.storeString());
        Task fourthTask = new After("play with friends", "Friday 6pm");
        assertEquals("A | 0 | play with friends | Friday 6pm", fourthTask.storeString());
        assertNotEquals("A | 1 | play with friends | Friday 6pm", fourthTask.storeString());
        fourthTask.markDone();
        assertEquals("A | 1 | play with friends | Friday 6pm", fourthTask.storeString());
        assertNotEquals("A | 0 | play with friends | Friday 6pm", fourthTask.storeString());
    }
}