package duke.storage;

import duke.commons.fileIO.FilePaths;
import duke.commons.fileIO.FileUtil;

import java.io.BufferedReader;
import java.io.File;

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
        String masterHelpFileStr = filePaths.getFilePathStr(FilePaths.FILE_PATH_NAMES.FILE_PATH_MASTER_HELP_FILE);
        String helpFilePathStr = FileUtil.concatPaths(masterHelpFileStr, helpFileRelativePathStr);

        BufferedReader bufferedReader = FileUtil.readResourceFile(helpFilePathStr);
        return bufferedReader;
    }
}
