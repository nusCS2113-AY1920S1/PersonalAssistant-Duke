package duke.storage;

import duke.commons.file.FilePathNames;
import duke.commons.file.FilePaths;
import duke.commons.file.FileUtil;

import java.io.BufferedReader;

//@@author Garystu
/**
 * This class is in charge of parsing user-designated help command to correct filepath.
 */
public class LoadHelpUtil {

    private static String defaultHelpFileStr = "help.txt";

    public static BufferedReader load(String specifiedHelp) {
        String helpFileRelativePathStr = "";

        if (specifiedHelp.isBlank()) {
            helpFileRelativePathStr = defaultHelpFileStr;
        } else {
            helpFileRelativePathStr = specifiedHelp + ".txt";
        }

        FilePaths filePaths = new FilePaths();
        String masterHelpFileStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_MASTER_HELP_FILE);
        String helpFilePathStr = FileUtil.concatPaths(masterHelpFileStr, helpFileRelativePathStr);

        BufferedReader bufferedReader = FileUtil.readResourceFile(helpFilePathStr);
        return bufferedReader;
    }
}
