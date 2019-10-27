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
     * Returns true if the data file is available, otherwise makes a new data file and returns false.
     *
     * @return true if data file available, otherwise false.
     * @throws CubeLoadingException exception occurs when unable to create new file.
     */
    public boolean checkFileAvailable(String fullPath) throws CubeLoadingException {
        File file = new File(fullPath);
        if (file.exists()) {
            return true;
        } else {
            create(file);
            return false;
        }
    }
}
