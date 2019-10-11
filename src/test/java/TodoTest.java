import Tasks.Todo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void stringConversionTest(){
        String w1 = "[T][\u2718] test todo borrow book";
        String w2 = new Todo("test todo borrow book").toString();
        assertEquals(w1, w2);
    }
}
