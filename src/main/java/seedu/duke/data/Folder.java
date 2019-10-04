package seedu.duke.data;

import java.io.File;

/**
 * Class to create directories in Hustler.
 */
public class Folder {

    private final static String DIRPATH = "data";

    public static File checkDirectory() {
        File directory = new File(DIRPATH);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }
}
