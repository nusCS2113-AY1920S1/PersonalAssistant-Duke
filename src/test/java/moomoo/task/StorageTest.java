package moomoo.task;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class StorageTest {
    @Test
    public void testFileLoad() throws MooMooException, IOException {
        File tempFile = File.createTempFile("duke", ".txt");
        tempFile.deleteOnExit();
    }
}