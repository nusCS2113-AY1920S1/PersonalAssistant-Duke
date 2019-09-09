import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void stringConversionTest(){
        assertEquals("[T][\u2718] Test Todo", (new Todo("Test Todo").toString()));
    }
}
