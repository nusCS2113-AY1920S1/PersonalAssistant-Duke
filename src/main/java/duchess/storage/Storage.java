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
import java.util.Stack;

public class Storage {
    private String fileName;
    private Stack<String> undoStack;
    private boolean isPreviousUndo;

    /**
     * Constructs Storage object.
     *
     * @param fileName filename for storage
     */
    public Storage(String fileName) {
        this.fileName = fileName;
        undoStack = new Stack<>();
        isPreviousUndo = false;
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
            throw new DuchessException("Unable to read file, continuing with empty list.");
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
            throw new DuchessException("An unexpected error occurred when writing to the file. " + e);
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
            throw new DuchessException("There's nothing to undo");
        }

        String jsonVal = undoStack.peek();
        undoStack.pop();

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
            throw new DuchessException("Check storage input issues.");
        }
    }

    /**
     * Adds Store object to stack.
     *
     * @param store current store object
     * @throws DuchessException throws exception when unable to push object to stack
     */
    public void addToUndoStackPush(Store store) throws DuchessException {

        try {
            String jsonVal = getObjectMapper().writeValueAsString(store);
            undoStack.push(jsonVal);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DuchessException("Undo stack push was unsuccessful.");
        }

    }

    public Stack<String> getUndoStack() {
        return this.undoStack;
    }

    public boolean getPreviousUndoStatus() {
        return this.isPreviousUndo;
    }

    public void setPreviousUndoFalse() {
        this.isPreviousUndo = false;
    }

    public void setPreviousUndoTrue() {
        this.isPreviousUndo = true;
    }
}
