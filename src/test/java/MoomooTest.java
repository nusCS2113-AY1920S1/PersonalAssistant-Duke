import org.junit.jupiter.api.Test;

import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoomooTest {
    @Test
    public void testResponse() {
        File newFile = new File("data/tasks.txt");
        newFile.delete();

        Moomoo newMoomoo = new Moomoo();

    }
}