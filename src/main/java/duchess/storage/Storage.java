package duchess.storage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import duchess.exceptions.DuchessException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage {
    private final Logger logger;

    private String fileName;
    private Deque<String> undoStack;
    private Deque<String> redoStack;

    // storageSize must be 1 more than intended size value.
    private static final int storageSize = 11;
    private static final String UNREADABLE_FILE_MESSAGE
            = "Unable to read file, continuing with default list.";
    private static final String FILE_WRITE_ERROR_MESSAGE
            = "An unexpected error occurred when writing to the file. ";
    private static final String JSON_PARSE_ERROR_MESSAGE
            = "JSON parse was unsuccessful.";
    private static final String UNSUCCESSFUL_MAP_ERROR_MESSAGE
            = "Mapping was unsuccessful.";
    private static final String EMPTY_REDO_STACK_ERROR_MESSAGE
            = "Redo stack is empty.";
    private static final String EMPTY_UNDO_STACK_ERROR_MESSAGE
            = "Undo stack is empty.";
    private static final String DUCHESS_STORAGE_ERROR_MESSAGE
            = "Check duchess.storage input.";
    private static final String STRING_TO_STORE_ERROR_MESSAGE
            = "Unable to convert String to Store.";

    /**
     * Constructs Storage object.
     *
     * @param fileName filename for duchess.storage
     */
    public Storage(String fileName) {
        this.logger = Logger.getLogger("Duchess");
        this.fileName = fileName;
        undoStack = new LinkedList<>();
        redoStack = new LinkedList<>();
    }

    /**
     * Returns the tasklist loaded from file.
     */
    public Store load() throws DuchessException {
        try {
            FileInputStream fileStream = new FileInputStream(this.fileName);
            Store store = getObjectMapper().readValue(fileStream, Store.class);
            fileStream.close();
            return store;
        } catch (IOException | ClassCastException e) {
            throw new DuchessException(UNREADABLE_FILE_MESSAGE);
        }
    }

    /**
     * Saves the given tasklist to file.
     *
     * @param store the store to save
     * @throws DuchessException an error if unable to write to file
     */
    public void save(Store store) throws DuchessException {
        logger.log(Level.INFO, "Saves to file.");
        try {
            FileOutputStream fileStream = new FileOutputStream(this.fileName);
            getObjectMapper().writeValue(fileStream, store);
            fileStream.close();
        } catch (IOException e) {
            throw new DuchessException(FILE_WRITE_ERROR_MESSAGE + e);
        }
    }

    private ObjectMapper getObjectMapper() {
        return new ObjectMapper()
                .enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
                .disable(MapperFeature.AUTO_DETECT_CREATORS,
                        MapperFeature.AUTO_DETECT_FIELDS,
                        MapperFeature.AUTO_DETECT_GETTERS,
                        MapperFeature.AUTO_DETECT_IS_GETTERS)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Obtains last Store object.
     *
     * @return last Store object
     * @throws DuchessException throws exception when unable to obtain Store object
     */
    public Store getLastSnapshot() throws DuchessException {
        if (undoStack.size() == 0) {
            throw new DuchessException(EMPTY_UNDO_STACK_ERROR_MESSAGE);
        }
        String jsonVal = undoStack.pollLast();
        try {
            Store store = getObjectMapper().readValue(jsonVal, Store.class);
            return store;
        } catch (JsonParseException e) {
            throw new DuchessException(JSON_PARSE_ERROR_MESSAGE);
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new DuchessException(UNSUCCESSFUL_MAP_ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DuchessException(DUCHESS_STORAGE_ERROR_MESSAGE);
        }
    }

    /**
     * Adds Store object to stack.
     *
     * @param store current store object
     */
    public void addToUndoStackPush(Store store) {
        String jsonVal = getStoreToString(store);
        String undoStackTop;

        if (undoStack.size() != 0) {
            undoStackTop = undoStack.peekLast();
            // Only push to undoStack if the topmost stack object is different.
            if (!undoStackTop.equals(jsonVal)) {
                undoStack.addLast(jsonVal);

                // Clears redo whenever there store is modified.
                redoStack.clear();
            }
        } else {
            assert (undoStack.size() == 0);
            undoStack.addLast(jsonVal);
        }

        // Ensures undoStack size is within acceptable value.
        deleteExcessUndoStack();
    }

    /**
     * Obtains first Store object.
     *
     * @return first Store object
     * @throws DuchessException throws exception when unable to obtain Store object
     */
    public Store getFirstSnapshot() throws DuchessException {
        if (redoStack.size() == 0) {
            throw new DuchessException(EMPTY_REDO_STACK_ERROR_MESSAGE);
        }
        String jsonVal = redoStack.pollFirst();

        // Add this string to undoStack
        undoStack.addLast(jsonVal);
        assert (undoStack.size() != 0);

        deleteExcessUndoStack();

        assert (undoStack.size() <= 10);

        try {
            Store store = getObjectMapper().readValue(jsonVal, Store.class);
            return store;
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new DuchessException(JSON_PARSE_ERROR_MESSAGE);
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new DuchessException(UNSUCCESSFUL_MAP_ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DuchessException(DUCHESS_STORAGE_ERROR_MESSAGE);
        }
    }

    /**
     * Returns an undoStack.
     *
     * @return undoStack
     */
    public Deque<String> getUndoStack() {
        return this.undoStack;
    }

    /**
     * Returns a redoStack.
     *
     * @return redoStack
     */
    public Deque<String> getRedoStack() {
        return this.redoStack;
    }

    /**
     * Returns the top object of UndoStack as a Store object.
     *
     * @return top object as a store object
     */
    public Store peekUndoStackAsStore() throws DuchessException {
        if (undoStack.size() != 0) {
            try {
                String undoStackString = undoStack.peekLast();
                Store store = getObjectMapper().readValue(undoStackString, Store.class);
                return store;

            } catch (IOException e) {
                throw new DuchessException(STRING_TO_STORE_ERROR_MESSAGE);
            }
        }
        return new Store();
    }

    /**
     * Returns a string representation of a Store object.
     * Returns an empty string if exception thrown.
     *
     * @param store store object
     * @return string representative of Store object
     */
    private String getStoreToString(Store store) {
        String jsonVal;
        try {
            jsonVal = getObjectMapper().writeValueAsString(store);
        } catch (JsonProcessingException e) {
            jsonVal = "";
            assert (jsonVal.equals(""));
        }
        return jsonVal;
    }

    /**
     * Adds deserialized string from undoStack to redoStack.
     */
    public void addToRedoStack() {

        if (undoStack.size() != 0) {
            String jsonVal = undoStack.peekLast();
            redoStack.addFirst(jsonVal);
        }

        deleteExcessRedoStack();
    }

    /**
     * Sets redoStack to specified size.
     */
    private void deleteExcessRedoStack() {
        while (redoStack.size() > storageSize) {
            redoStack.pollLast();
        }
    }

    /**
     * Sets undoStack to specified size.
     */
    private void deleteExcessUndoStack() {
        while (undoStack.size() > storageSize) {
            undoStack.pollFirst();
        }
    }
}