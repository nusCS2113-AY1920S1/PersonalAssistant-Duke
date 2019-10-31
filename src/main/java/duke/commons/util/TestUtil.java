package duke.commons.util;

import java.nio.file.Files;
import java.nio.file.Path;

/** This class is to solve Intellij test does not run problem. will be removed */
public class TestUtil {
    public static void printToFile(String content) {
        try {
            Files.writeString(Path.of(".", "test.txt"), content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
