package planner.util.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Storage {

    /**
     * Path to storage data file.
     * Boolean flag to indicate if data file exists.
     *
     */
    private Path path;
    private Path dataPath;

    private boolean dataPathExists = false;
    private boolean fileExists = false;

    /**
     * Default Constructor for storage class.
     *
     */
    public Storage() {
        path = Paths.get("data/config.json");
        setFileExists();
    }

    /**
     * Overloaded Constructor for storage class, specifying the data path as String.
     */
    public Storage(String filePath) {
        dataPath = Paths.get(filePath);
        setDataPathExists();
    }

    public void setDataPath(Path dataPath) {
        this.dataPath = dataPath;
        setDataPathExists();
    }

    public boolean getFileExits() {
        return fileExists;
    }

    public boolean getDataPathExists() {
        return dataPathExists;
    }

    private void setFileExists() {
        fileExists = Files.isRegularFile(path);
    }

    private void setDataPathExists() {
        dataPathExists = Files.isRegularFile(dataPath);
    }

    private void makeFile(Path path) throws IOException {
        Files.createDirectories(path.getParent());
        Files.createFile(path);
    }

    /**
     * Helper function to write nusMods data to file.
     * @param data List of String of data from nusMods.
     */
    public void writeModsData(List<String> data) {
        try {
            if (!dataPathExists) {
                makeFile(dataPath);
                setDataPathExists();
            }
            Files.write(dataPath, data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
