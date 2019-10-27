/**
 * Handling of CSV File Read/Write operations in Cube
 *
 * @author kuromono
 */
package cube.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import cube.exception.CubeException;
import cube.exception.CubeLoadingException;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class FileUtilCSV<Type> extends FileUtil {
    private Type fileObject;
    private String fileFullPath;
    private static String fileName = "cube.csv";

    /**
     * Constructor with two argument.
     *
     * @param filePath the directory path where data will be stored.
     */
    public FileUtilCSV(String filePath, String fileName, Type fileObject) {
        super(filePath, fileName);
        this.fileFullPath = filePath + File.separator + fileName;
        this.fileObject = fileObject;
    }

    public Collection<Type> load() throws CubeException {
        Collection<Type> objectToLoad = null;

        if (checkFileAvailable(fileFullPath)) {
            System.out.println("Loading file from : " + fileFullPath);
            try {
                File fileSave = new File(fileFullPath);
                CsvMapper mapper = new CsvMapper();
                JavaType type = mapper.getTypeFactory().constructType(fileObject.getClass());
                CsvSchema schema = mapper.schemaFor(type);

                String csv = mapper.writer(schema).writeValueAsString(fileObject);

                MappingIterator<Type> it = mapper.readerFor(type).with(schema).readValues(csv);
                objectToLoad = it.readAll();

            } catch (IOException e) {
                e.printStackTrace();
                throw new CubeLoadingException(fileFullPath);
            }
        }
        return objectToLoad;
    }

    public void save(Collection<Type> objectToSave) throws CubeException {
        checkFileAvailable(fileFullPath);
        try {
            File fileSave = new File(fileFullPath);
            CsvMapper mapper = new CsvMapper();
            JavaType type = mapper.getTypeFactory().constructType(fileObject.getClass());
            CsvSchema schema = mapper.schemaFor(type);
            schema = schema.withUseHeader(true);
            mapper.writerFor(objectToSave.getClass()).with(schema).writeValue(fileSave, objectToSave);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CubeLoadingException(fileFullPath);
        }
    }
}
