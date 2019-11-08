package planner.util;

import java.nio.file.Files;
import java.nio.file.Path;

public class PrintUtil {

    /**
     * This class is to solve Intellij test does not run problem. will be removed
     */
    public static void printToFile(String content) {
        try {
            Files.writeString(Path.of(".", "test.txt"), content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
