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

public class Storage {
    private String fileName;
    private Deque<String> undoStack;
    private Deque<String> redoStack;

    //private static final String RUN_TIME_ERROR_MESSAGE = "JSON run time error.";
    private static final String UNREADABLE_FILE_MESSAGE
            = "Unable to read file, continuing with empty list.";
    private static final String FILE_WRITE_ERROR_MESSAGE
            = "An unexpected error occurred when writing to the file. ";

    private static final String JSON_PARSE_ERROR_MESSAGE = "JSON parse was unsuccessful.";

    /**
     * Constructs Storage object.
     *
     * @param fileName filename for duchess.storage
     */
    public Storage(String fileName) {
        this.fileName = fileName;
        undoStack = new LinkedList<>();
        redoStack = new LinkedList<>();
    }

    // Unchecked type coercion is necessary here.
    // And possible cast exceptions are handled

    /**
     * Returns the tasklist loaded from file.
     */
    @SuppressWarnings("unchecked")
    public Store load() throws DuchessException {
        try {
            FileInputStream fileStream = new FileInputStream(this.fileName);
            Store store = getObjectMapper().readValue(fileStream, Store.class);
            fileStream.close();
            return store;
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
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
    public Store getLastSnapshot(int undoCounter) throws DuchessException {
        if (undoStack.size() == 0) {
            throw new DuchessException("There's nothing to undo.");
        }

        String jsonVal = undoStack.pollLast();

        try {
            Store store = getObjectMapper().readValue(jsonVal, Store.class);
            return store;
        } catch (JsonParseException e) {
            throw new DuchessException(JSON_PARSE_ERROR_MESSAGE);
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new DuchessException("Mapping was unsuccessful.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new DuchessException("Check duchess.storage input.");
        }
    }

    /**
     * Adds Store object to stack.
     *
     * @param store current store object
     * @throws DuchessException throws exception when unable to push object to stack
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
    }

    /**
     * Obtains first Store object.
     *
     * @return first Store object
     * @throws DuchessException throws exception when unable to obtain Store object
     */
    public Store getFirstSnapshot() throws DuchessException {
        if (redoStack.size() == 0) {
            throw new DuchessException("There's nothing to redo.");
        }

        String jsonVal = redoStack.pollFirst();
        // Add this string to undoStack
        undoStack.addLast(jsonVal);

        try {
            Store store = getObjectMapper().readValue(jsonVal, Store.class);
            return store;
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new DuchessException("JSON parse was unsuccessful.");
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new DuchessException("Mapping was unsuccessful.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new DuchessException("Check duchess.storage input.");
        }
    }

    public Deque<String> getUndoStack() {
        return this.undoStack;
    }

    public Deque<String> getRedoStack() {
        return this.undoStack;
    }

    /**
     * Returns the top object of UndoStack as a Store object.
     *
     * @return top object as a store object
     */
    public Store peekUndoStackAsStore() {
        if (undoStack.size() != 0) {
            try {
                String undoStackString = undoStack.peekLast();
                Store store = getObjectMapper().readValue(undoStackString, Store.class);
                return store;

            } catch (Exception e) {
                System.out.println("Unable to convert String to Store.");
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
            jsonVal = new String();
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
    }
}