package duke.storage;

import java.io.File;

/**
 * This class is in charge of parsing user-designated help command to correct filepath
 */
public class LoadHelpUtil {

    public static File load(String specifiedHelp) {
        String line = "";
        String helpFileName = "";
        File helpFile;
        if (specifiedHelp.isBlank()) {
            helpFileName = "help.txt";
        } else {
            helpFileName = specifiedHelp + ".txt";
        }
        String sep = System.getProperty("file.separator");
        helpFile = new File("src" + sep + "main" + sep + "java" + sep + "duke"
                + sep + "commons" + sep + "help" + sep + helpFileName);
        return helpFile;
    }
}
