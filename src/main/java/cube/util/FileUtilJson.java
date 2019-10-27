/**
 * Handling of JSON File Read/Write operations in Cube
 *
 * @author kuromono
 */
package cube.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import cube.exception.CubeException;
import cube.exception.CubeLoadingException;

public class FileUtilJson<Type> extends FileUtil {
    private Type fileObject;
    private String fileFullPath;

    /**
     * Constructor with two argument.
     *
     * @param filePath the directory path where data will be stored.
     */
    public FileUtilJson(String filePath, String fileName, Type fileObject) {
        super(filePath, fileName);
        this.fileFullPath = filePath + File.separator + fileName;
        this.fileObject = fileObject;
    }

    /**
     * Loads the serialized object from the JSON file.
     *
     * @return De-serialized object read from the JSON file.
     * @throws CubeLoadingException exception occurs in reading from data file.
     */
    public Type load() throws CubeException {
        if (checkFileAvailable(fileFullPath)) {
            System.out.println("Loading file from : " + fileFullPath);

            try {
                File fileSave = new File(fileFullPath);
                ObjectMapper mapper = new ObjectMapper();
                JavaType type = mapper.getTypeFactory().constructType(fileObject.getClass());
                fileObject = mapper.readValue(fileSave, type);
            } catch (IOException e) {
                e.printStackTrace();
                throw new CubeLoadingException(fileFullPath);
            }
        }
        return fileObject;
    }

    /**
     * Saves the object into a JSON file.
     *
     * @param fileObject Object to be saved into JSON.
     * @throws CubeException exception happens in writing to the data file.
     */
    public void save(Type fileObject) throws CubeException {
        checkFileAvailable(fileFullPath);
        try {
            File fileSave = new File(fileFullPath);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(fileSave, fileObject);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CubeLoadingException(fileFullPath);
        }
    }
}
