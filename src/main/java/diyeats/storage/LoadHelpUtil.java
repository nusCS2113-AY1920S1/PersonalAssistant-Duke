package diyeats.storage;

import diyeats.commons.file.FilePathNames;
import diyeats.commons.file.FilePaths;
import diyeats.commons.file.FileUtil;

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
