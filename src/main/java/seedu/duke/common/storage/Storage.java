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
public interface Storage {

    /**
     * Saves content to a designated path.
     * @param path where the content is stored to
     * @param content the content of the file to be saved
     * @throws IOException when error occurs at file io
     */
    static void saveToFile(Path path, String content) throws IOException {
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
    static String readFromFile(Path path) throws IOException {
        return Files.readString(path);
    }

    static List<String> readLinesFromFile(Path path) throws IOException {
        String content = readFromFile(path);
        return content.lines().collect(Collectors.toList());
    }

    /**
     * Checks whether the file already exists at the given path.
     *
     * @param path where the existence of the file is to be checked
     * @return the existence of the file
     */
    static boolean fileExists(Path path) {
        return Files.exists(path);
    }

    /**
     * Creates a file at the designated path when the file does not already exist.
     *
     * @param path where the file is to be checked and saved
     */
    static void createFileIfNotExist(Path path) {
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
    static Path prepareDataFolderPath() {
        return Path.of(".", "data");
    }

    /**
     * Prepares the path of a log folder with the given filename.
     *
     * @return the path of the data file
     */
    static Path prepareLogFolderPath() {
        return Path.of(".", "data", "logs");
    }

    /**
     * Prepares the path of a data file with the given filename.
     *
     * @param filename the filename of the data file
     * @return the path of the data file
     */
    static Path prepareDataPath(String filename) {
        return Path.of(".", "data", filename);
    }

    /**
     * Prepares the path of a email file with the given filename.
     *
     * @param filename the filename of the email file
     * @return the path of the email file
     */
    static Path prepareEmailPath(String filename) {
        return Path.of(".", "data", "emails", filename);
    }
}
