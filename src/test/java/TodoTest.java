import org.junit.jupiter.api.Test;

public class TodoTest {
    @Test
    public void TodoTest(){
        Todo todo = new Todo("Test");
        assert todo.toString().equals("[T][\u2718] Test");
        todo.markDone();
        assert todo.toString().equals("[T][\u2713] Test");
    }
}
