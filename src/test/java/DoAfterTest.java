import org.junit.jupiter.api.Test;
import task.DoAfter;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoAfterTest {

    @Test
    void doAfter() {
        DoAfter doafter = new DoAfter("eat", "2 hours");
        assertEquals(doafter.toString(), "[DA][✘] [A] eat (after: 2 hours)");
    }
}
