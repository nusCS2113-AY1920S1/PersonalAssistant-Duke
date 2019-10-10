import org.junit.jupiter.api.Test;

import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MooMooTest {
    @Test
    public void testResponse() {
        File newFile = new File("data/tasks.txt");
        newFile.delete();

        MooMoo newMoomoo = new MooMoo();

    }
}