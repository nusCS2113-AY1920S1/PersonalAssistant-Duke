/**
 * Handling of CSV File Read/Write operations in Cube
 *
 * @author kuromono
 */

package cube.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import cube.exception.CubeException;
import cube.logic.parser.ParserUtil;
import cube.util.exception.CubeUtilException;
import cube.util.exception.UtilErrorMessage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TimeZone;

public class FileUtilCSV<Type> extends FileUtil {
    private Type fileObject;
    private String fileFullPath;
    private File file;
    private CsvMapper mapper;

    /**
     * Constructor with two argument.
     *
     * @param filePath the directory path where data will be stored.
     */
    public FileUtilCSV(String filePath, String fileName, Type fileObject) {
        super(filePath, fileName);
        this.fileFullPath = filePath + File.separator + fileName;
        this.fileObject = fileObject;
        this.file = new File(fileFullPath);
        this.mapper = new CsvMapper();
        this.mapper.setDateFormat(ParserUtil.getDateFormat());
        this.mapper.setTimeZone(ParserUtil.getTimeZone());
    }

    /**
     * Loads data from the CSV file to an ArrayList.
     *
     * @throws CubeException exception happens in reading from the data file.
     */
    public ArrayList<Type> load() throws CubeException {
        ArrayList<Type> collectionToLoad = new ArrayList<>();

        System.out.println("Loading file from : " + fileFullPath);
        try {
            CsvSchema schema = CsvSchema.emptySchema().withHeader();
            JavaType type = mapper.getTypeFactory().constructType(fileObject.getClass());
            ObjectReader reader = mapper.readerFor(type).with(schema);

            MappingIterator<Type> iterator = reader.readValues(file);

            while (iterator.hasNext()) {
                Type currentObject = iterator.next();
                collectionToLoad.add(currentObject);
            }

        } catch (IOException e) {
            throw new CubeUtilException(UtilErrorMessage.READ_ERROR + fileFullPath);
        }

        return collectionToLoad;
    }

    /**
     * Saves the Collection into a CSV file.
     *
     * @param collectionToSave Collection to be saved into CSV.
     * @throws CubeException exception happens in writing to the data file.
     */
    public void save(Collection<Type> collectionToSave) throws CubeException {
        checkFileAvailable(true);
        try {
            JavaType type = mapper.getTypeFactory().constructType(fileObject.getClass());
            CsvSchema schema = mapper.schemaFor(type);
            schema = schema.withUseHeader(true);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.writerFor(collectionToSave.getClass()).with(schema).writeValue(file, collectionToSave);
        } catch (IOException e) {
            throw new CubeUtilException(UtilErrorMessage.WRITE_ERROR + fileFullPath);
        }
    }
}
