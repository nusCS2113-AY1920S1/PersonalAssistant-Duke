
import org.junit.jupiter.api.Test;
import task.FixedDuration;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class FixedDurationTest {

    @Test
    void testDuration() {
        FixedDuration fixedduration = new FixedDuration("eat", "2 hours");
        assertEquals(fixedduration.toString(), "[F][✘] [A] eat (needs: 2 hours)");
    }
}
