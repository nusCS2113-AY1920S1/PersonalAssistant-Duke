package cube.testutil;

import java.io.File;

public class StorageUtil {
    /**
     * Boolean check to see if file exists or not.
     *
     * @return true if data file exists, false if not found.
     */
    public static boolean checkFileAvailable(String fileName) {
        String fileFullPath = "data" + File.separator + fileName;
        File file = new File(fileFullPath);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deletes the file with the specified filename.
     */
    public static void deleteFile(String fileName) {
        String fileFullPath = "data" + File.separator + fileName;
        File file = new File(fileFullPath);
        file.delete();
    }
}
