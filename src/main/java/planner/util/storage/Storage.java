//@@author namiwa

package planner.util.storage;

import com.google.gson.Gson;
import planner.logic.exceptions.planner.ModTamperedDataException;
import planner.credential.cryptography.Cipher;
import planner.credential.cryptography.CipherState;
import planner.credential.user.CredentialManager;
import planner.util.cryptography.CryptographyUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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

    private static Gson gson = new Gson();
    private static CredentialManager credential = new CredentialManager();
    private static Cipher cipher = new Cipher();

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

    public void makeFile(Path path) throws IOException {
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

    //@@author LongLeCE

    /**
     * Write an object to file.
     * @param object object to write
     * @param path file path
     */
    public void writeGson(Object object, String path) {
        FileWriter writer = null;
        try {
            makeFile(Paths.get(path));
        } catch (IOException ex) {
            ex.getMessage();
        }
        try {
            writer = new FileWriter(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        gson.toJson(object, writer);
        try {
            assert writer != null;
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Write an object to file but encrypted.
     * @param object object to write
     * @param path file path
     */
    public void writeGsonSecure(Object object, String path) {
        byte[] message = gson.toJson(object).getBytes(StandardCharsets.UTF_8);
        CipherState state = new CipherState(message);
        try {
            state = cipher.encode(state, credential.getEncryptionLayers(), true);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        this.writeGson(state, path);
    }

    /**
     * Read an object from file.
     * @param path file path
     * @param clazz Class of object
     */
    public <E> E readGson(String path, Class<E> clazz) {
        FileReader reader;
        try {
            reader = new FileReader(path);
        } catch (FileNotFoundException ex) {
            return null;
        }
        return gson.fromJson(reader, clazz);
    }

    /**
     * Read an object from encrypted file.
     * @param path file path
     * @param clazz Class of object
     */
    public <E> E readGsonSecure(String path, Class<E> clazz) throws ModTamperedDataException {
        CipherState state = this.readGson(path, CipherState.class);
        if (state == null) {
            return null;
        }
        try {
            state = cipher.decode(state, credential.getEncryptionLayers());
        } catch (Throwable ex) {
            throw new ModTamperedDataException();
        }
        int hashLength = credential.getHashLength();
        if (!CryptographyUtils.isOriginal(state.getMessage(), hashLength)) {
            throw new ModTamperedDataException();
        }
        return gson.fromJson(CryptographyUtils.removeTrailingHash(state.getMessage(), hashLength), clazz);
    }
}
