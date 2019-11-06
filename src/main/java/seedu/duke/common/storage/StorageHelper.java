package seedu.duke.common.storage;

import seedu.duke.ui.UI;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A helper class for all file io operations.
 */
public class StorageHelper {

    /**
     * Saves content to a designated path.
     * @param path where the content is stored to
     * @param content the content of the file to be saved
     * @throws IOException when error occurs at file io
     */
    public static void saveToFile(Path path, String content) throws IOException {
        if (!fileExists(path)) {
            createFileIfNotExist(path);
        }
        Files.writeString(path, content, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    /**
     * Reads content from a designated path.
     *
     * @param path where content is read from
     * @return the content of the file being read
     * @throws IOException when error occurs at file io
     */
    public static String readFromFile(Path path) throws IOException {
        return Files.readString(path);
    }

    public static List<String> readLinesFromFile(Path path) throws IOException {
        String content = readFromFile(path);
        return content.lines().collect(Collectors.toList());
    }

    /**
     * Checks whether the file already exists at the given path.
     *
     * @param path where the existence of the file is to be checked
     * @return the existence of the file
     */
    public static boolean fileExists(Path path) {
        return Files.exists(path);
    }

    /**
     * Creates a file at the designated path when the file does not already exist.
     *
     * @param path where the file is to be checked and saved
     */
    public static void createFileIfNotExist(Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            UI.getInstance().showDebug("File at " + path.toString() + " already exist. File creation abort.."
                    + ".");
        }
    }

    /**
     * Prepares the path of a data folder with the given filename.
     *
     * @return the path of the data file
     */
    public static Path prepareDataFolderPath() {
        return Path.of(".", "data");
    }

    /**
     * Prepares the path of a log folder with the given filename.
     *
     * @return the path of the data file
     */
    public static Path prepareLogFolderPath() {
        return Path.of(".", "data", "logs");
    }

    /**
     * Prepares the path of a data file with the given filename.
     *
     * @param filename the filename of the data file
     * @return the path of the data file
     */
    public static Path prepareDataPath(String filename) {
        return Path.of(".", "data", filename);
    }

    /**
     * Prepares the path of a email file with the given filename.
     *
     * @param filename the filename of the email file
     * @return the path of the email file
     */
    public static Path prepareEmailPath(String filename) {
        return Path.of(".", "data", "emails", filename);
    }

    private static final String[] directories = { "emails", "logs" };

    private static final String[] dataFilenames = { "email.txt", "keywords.txt", "task.txt", "user.txt" };

    private static void createDirectories() throws IOException {
        for (String directory : directories) {
            Path path = prepareDataPath(directory);
            Files.createDirectories(path);
        }
    }

    private static void createDataFiles() {
        for (String filename : dataFilenames) {
            Path path = prepareDataPath(filename);
            createFileIfNotExist(path);
        }
    }

    private static String defaultUserToken = "OAQABAAAAAACQN9QBRU3jT6bcBQLZNUj7K1yjcIy6vDS0hoXDM6pkuR4CpC7o"
            + "Ke_6geouyaawHd40IJpGZZqXlYQNZX5O4KKVvdT5i0B6AuEhRRv8Ol0E9rtMC9VRQ6JUNzlJJBIJKNlBSrB_xXLCl1K9"
            + "_4yAL7BvvE1R68nk-F02jdNrN_Nn5ZS9gz_QqAYPEgvTLwraKjHi0RRFtDxqKMQpdQAjL329xKmAXr68LvGGTg3gOQB"
            + "kPay4vPSO7-xpLAI6TMccPpWQfsnR-2R664gC9couXZa77kSzOmuB-7nT79oRDF_ho8h7aQDAM-t5OgkPl0cHNG-3k3"
            + "hWQNyokyR5lhE8y28UuDcwUyTJoV-hhNVTx3wQU8434_0NgZ4tF-G0m4qY8sEhabuvlLSiLMDRlwly_1Y8qKjqMLT"
            + "FWfOWGtYcmwEeraJqSNAaP31T9N6RtaFye6kOfJQCY-bqGTL5KuQqrIAoPMzRTGQKNpXzhf41JG1_Y2-fNC1LjurA"
            + "Qh0GirE-tG07xiWkzQ5wk-hrKZ9oBqEjEcoUADb2J6To-iFlK8VbR5Z46_bgvhbpaxdRLhCale9TfQM6YwQR1foF4"
            + "iNPeXrV5XAwOz6b4ZgMdj5WH0Sq7rX3AgruYS3RJSTGw6s0ZGpZ97eN5KZQvi2ZooMRzZ9r7r7OeTSkh5IjhOQP6O"
            + "poNfSAJ_rEag4gSgAHQfCCp9Z9YThF1QfYlmkt5OKvoNimJTz47KhYySBwKk6KZ3hT7qHnib4HVDkCbVo3wAWON8Sr"
            + "9UEMmuKehoLWlgA8lYkhv855EO6rRWhJXPwRrvKpXLK7fZShgvJg1YXn12DIMDOiUsZqe2kqxeCCjhog7eu6GiHSNw"
            + "ga0ODJE8wUmNQZqMfDb4R8FOFxfiKQxnXlIo0KwK-XzogSqUdL47vea64GIAA";

    private static void writeDefaultUserToken() throws IOException {
        Path path = prepareDataPath("user.txt");
        if (readFromFile(path).length() < 1) {
            saveToFile(path, defaultUserToken);
        }
    }

    /**
     * Creates all the necessary directories and files in data folder, including the data folder itself.
     *
     * @return whether this operation is successful.
     */
    public static boolean constructDataDirectory() {
        try {
            createDirectories();
            createDataFiles();
            writeDefaultUserToken();
            return true;
        } catch (IOException e) {
            UI.getInstance().showError("Prepare data directory failed..." + System.lineSeparator() +
                    "Please transfer the JAR file to a clean and accessible directory and start again. ");
            return false;
        }
    }
}
