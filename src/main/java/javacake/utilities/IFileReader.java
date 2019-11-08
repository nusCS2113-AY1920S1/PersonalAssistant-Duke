package javacake.utilities;

import javacake.exceptions.CakeException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public interface IFileReader {

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
}
