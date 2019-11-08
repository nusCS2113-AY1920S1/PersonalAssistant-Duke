package javacake.utilities;

import javacake.exceptions.CakeException;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public interface IFileUtilities {

    /**
     * Reads the file specified by the user.
     * @return String of the content in the file specified by the user.
     * @throws CakeException If file does not exist.
     */
    static String readFile(String currentFilePath) throws CakeException {
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(new File(currentFilePath)));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            throw new CakeException(e.getMessage());
        }
    }

    /**
     * Converts input file name by user with the actual case sensitive file name in system.
     * @param defaultDirectoryPath Default file path to the directory of the notes file.
     * @param fileName Name of the file to be edited or deleted.
     * @return String of original file name.
     * @throws CakeException If file does not exist.
     */
    static String returnOriginalFileName(String defaultDirectoryPath, String fileName) throws CakeException {
        try {
            File file = new File(defaultDirectoryPath + fileName + ".txt");
            return FilenameUtils.removeExtension(file.getCanonicalFile().getName());
        } catch (IOException e) {
            throw new CakeException(e.getMessage());
        }
    }
}
