package duke.commons.fileIO;

import duke.commons.exceptions.DukeException;

import java.io.*;

/**
 * Class in charge of common file operations such as reading and writing from jar resources and
 * user system files. Handles file exceptions and throws DukeException when relevant.
 */

public class FileUtil {

    private static final String systemFileSep = System.getProperty("file.separator");

    /**
     * Reads config file loaded from user file system rather than inside the jar file.
     * @param fileStr File location of user config file
     * @return BufferedReader object with contents of resource.
     * @throws DukeException If unable to find file in user's file system or incorrect
     * file directory syntax in filepaths config file.
     */
    public static BufferedReader readResourceFile(String fileStr) {
        try {
            InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(fileStr);
            return new BufferedReader(new InputStreamReader(is));
        } catch (NullPointerException e) {
            // TODO: Better Exception handling for invalid resource file.
            return new BufferedReader(new StringReader(""));
        }
    }

    /**
     * Reads config file stored in jar package of this application.
     * @param fileStr File location of config file in jar.
     * @return BufferedReader object with contents of resource
     * @throws DukeException If unable to find file in jar package.
     */
    public static BufferedReader readUserFile(String fileStr) throws DukeException {
        File file = new File(fileStr);
        return readFile(file);
    }

    private static BufferedReader readFile(File file) throws DukeException {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            createMissingFile(file);
            throw new DukeException("File " + file + " does not exist. Failed to load file.");
        }

        return bufferedReader;
    }

    /**
     * Create missing parent folders and copy missing file to host system.
     * @param file File that is missing.
     * @throws DukeException if application has difficulty creating new file in host system.
     */
    private static void createMissingFile(File file) throws DukeException {
        if (file.exists()) {
            return;
        }
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (Exception e) {
            throw new DukeException("Create missing file error : " + e.toString());
        }
    }

    public static void writeFile(String textStr, String fileStr) throws DukeException {
        try {
            File file = new File(fileStr);
            if (!file.exists()) {
                createMissingFile(file);
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            // Split large strings into smaller strings for memory efficiency
            for (String lineStr : textStr.split("\n")) {
                bufferedWriter.write(lineStr);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new DukeException("Unable to write to file: " + fileStr);
        }
    }

    /**
     * Return operating system dependent file path.
     * @param pathStr Input file path.
     * @return File path string that is compatible with operating system.
     */
    public static String getSystemFilePathStr(String pathStr) {
        return pathStr.replace("/", systemFileSep);
    }

    /**
     * Helper function to concatenate 2 file paths.
     * @param parentPathStr Parent directory of path to concatenate.
     * @param childPathStr Child directory of path to concatenate.
     * @return Concatenated String of parent and child paths.
     */
    public static String concatPaths(String parentPathStr, String childPathStr) {
        return parentPathStr + systemFileSep + childPathStr;
    }
}
