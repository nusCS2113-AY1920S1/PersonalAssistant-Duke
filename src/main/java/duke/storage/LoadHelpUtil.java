package duke.storage;

import java.io.File;

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
