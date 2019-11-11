/**
 * Handling of CSV File Read/Write operations in Cube.
 *
 * @author kuromono
 */

package cube.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
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
import java.util.logging.Logger;

public class FileUtilCSV<T> extends FileUtil {
    private T fileObject;
    private String fileFullPath;
    private File file;
    private CsvMapper mapper;
    private final Logger logger = LogUtil.getLogger(FileUtilCSV.class);

    /**
     * Constructor with two argument.
     *
     * @param filePath the directory path where data will be stored.
     */
    public FileUtilCSV(String filePath, String fileName, T fileObject) {
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
    public ArrayList<T> load() throws CubeException {
        ArrayList<T> collectionToLoad = new ArrayList<>();

        logger.info("Loading file from : " + fileFullPath);
        try {
            CsvSchema schema = CsvSchema.emptySchema().withHeader();
            JavaType type = mapper.getTypeFactory().constructType(fileObject.getClass());
            ObjectReader reader = mapper.readerFor(type).with(schema);

            MappingIterator<T> iterator = reader.readValues(file);

            while (iterator.hasNext()) {
                T currentObject = iterator.next();
                collectionToLoad.add(currentObject);
            }

        } catch (IOException e) {
            logger.warning(UtilErrorMessage.READ_ERROR + fileFullPath);
            throw new CubeUtilException(UtilErrorMessage.READ_ERROR + fileFullPath);
        } catch (RuntimeJsonMappingException e) {
            logger.warning(UtilErrorMessage.CSV_MAPPER_ERROR);
            throw new CubeUtilException(UtilErrorMessage.CSV_MAPPER_ERROR);
        }

        return collectionToLoad;
    }

    /**
     * Saves the Collection into a CSV file.
     *
     * @param collectionToSave Collection to be saved into CSV.
     * @throws CubeException exception happens in writing to the data file.
     */
    public void save(Collection<T> collectionToSave) throws CubeException {
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
