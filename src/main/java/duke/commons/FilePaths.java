package duke.commons;

import java.io.File;

public class FilePaths {
    public static final String sep = System.getProperty("file.separator");
    public static final File DATA_FILE = new File("src" + sep + "main" + sep + "java" + sep + "duke"
            + sep + "Data" + sep + "duke.txt");
    public static final File DEFAULTS_FILE = new File("src" + sep + "main" + sep + "java" + sep + "duke"
            + sep + "Data" + sep + "defaultItems.txt");
    public static final File GOAL_FILE = new File("src" + sep + "main" + sep + "java" + sep + "duke"
            + sep + "Data" + sep + "goal.txt");
    public static final File USER_FILE = new File("src" + sep + "main" + sep + "java" + sep + "duke"
            + sep + "Data" + sep + "user.txt");
    public static final File AUTOCORRECT_FILE = new File("src" + sep + "main" + sep + "java" + sep + "duke"
            + sep + "Data" + sep + "word.txt");
}
