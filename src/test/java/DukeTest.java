import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DukeTest {
    @Test
    public void dummyTest(){
        assertEquals(2, 2);
    }
    @Test
    public void DeadlineTest() {
        new DeadlineTest().Test();
    }
    @Test
    public void DeleteTest() {
        new DeleteTest().Test();
    }
    @Test
    public void DoneTest() {
        new DoneTest().Test();
    }
    @Test
    public void EventTest() {
        new EventTest().Test();
    }
    @Test
    public void FindTest() {
        new FindTest().Test();
    }
    @Test
    public void ListTest() {
        new ListTest().Test();
    }
    @Test
    public void ParserTest() {
        new ParserTest().Test();
    }
    @Test
    public void Todo() {
        new TodoTest().Test();
    }
}