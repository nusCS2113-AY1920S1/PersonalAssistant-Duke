package duke.storage;

import java.io.File;

public interface StorageInterface {
    String sep = System.getProperty("file.separator");
    File file = new File("src" + sep + "main" + sep + "java" + sep + "duke"
            + sep + "Data" + sep + "duke.txt");
    File defaultFile = new File("src" + sep + "main" + sep + "java" + sep + "duke"
            + sep + "Data" + sep + "defaultItems.txt");
    File goalFile = new File("src" + sep + "main" + sep + "java" + sep + "duke"
            + sep + "Data" + sep + "goal.txt");
    File userFile = new File("src" + sep + "main" + sep + "java" + sep + "duke"
            + sep + "Data" + sep + "user.txt");
    File wordFile = new File("src" + sep + "main" + sep + "java" + sep + "duke"
            + sep + "Data" + sep + "word.txt");
}
