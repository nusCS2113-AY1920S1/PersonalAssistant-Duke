/**
 * Abstract parent class for FileUtil.
 *
 * @author kuromono
 */

package cube.util;

import cube.exception.CubeLoadingException;

import java.io.File;
import java.io.IOException;

public abstract class FileUtil {
    private String fileFullPath;

    /**
     * Constructor with two argument.
     *
     * @param filePath The directory path where data will be stored.
     * @param fileName The filename of the file to be read/write.
     */
    public FileUtil(String filePath, String fileName) {
        this.fileFullPath = filePath + File.separator + fileName;
    }

    /**
     * Creates the parent directory and file.
     *
     * @param file the file at which should be created.
     * @throws CubeLoadingException exception occurs when unable to create new file.
     */
    public void create(File file) throws CubeLoadingException {
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new CubeLoadingException(fileFullPath);
        }
    }

    /**
     * Boolean check to see if file available with a createFile flag to toggle creation of new file if not found.
     *
     * @return true if data file available, otherwise makes a new data file if createFile is true and returns false.
     * @throws CubeLoadingException exception occurs when unable to create new file.
     */
    public boolean checkFileAvailable(boolean createFile) throws CubeLoadingException {
        File file = new File(fileFullPath);
        if (file.exists()) {
            return true;
        } else {
            if (createFile) {
                create(file);
            }
            return false;
        }
    }
}
