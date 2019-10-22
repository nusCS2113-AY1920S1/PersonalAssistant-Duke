package seedu.hustler.data;

import java.io.File;

/**
 * Class to create directories in Hustler.
 */
public class Folder {

    private static final String DIRPATH = "data";
    
    /**
     * Checks directory to see if file exists. If it
     * doesn't exist, creates one.
     * @return directory
     */
    public static File checkDirectory() {
        File directory = new File(DIRPATH);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }
}
