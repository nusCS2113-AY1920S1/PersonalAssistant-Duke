package owlmoney.storage;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

/**
 * Handles reading and writing data to and from disk.
 */
public class Storage {
    private final String path;

    public Storage(String path) {
        this.path = path;
    }

    /**
     * Checks if the specfied file exists.
     * @param fileName the filename to check.
     * @return the result of whether the file exists.
     */
    public boolean isFileExist(String fileName) {
        return Files.exists(Paths.get(path + fileName));
    }

    /**
     * Checks if the directory does not exist.
     * @param path path of the directory.
     * @return boolean of whether created successfully.
     */
    public boolean createDirectoryIfNotExist(String path) {
        File dir = new File(path);
        boolean result = false;
        if (!dir.exists()) {
            result = dir.mkdirs();
        }
        return result;
    }

    /**
     * Writes files dynamically based on parameters specified.
     *
     * @param inputData input data in ArrayList of String Arrays.
     * @param fileName name of file when exported
     * @throws IOException when unable to write data to file.
     */
    public void writeFile(ArrayList<String[]> inputData, String fileName) throws IOException {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(path + fileName));
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
            for (String[] line : inputData) {
                csvWriter.writeNext(line);
            }
        } catch (IOException exceptionMessage) {
            throw new IOException(exceptionMessage);
        }
    }

    /**
     * Writes file for profile user name only.
     *
     * @param inputData input data in String Arrays.
     * @param fileName name of file when exported.
     * @throws IOException when unable to write data to file.
     */
    public void writeProfileFile(String[] inputData, String fileName) throws IOException {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(path + fileName));
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
            String[] headerRecord = {"Name"};
            csvWriter.writeNext(headerRecord);
            csvWriter.writeNext(inputData);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Reads input file for profile user name.
     *
     * @param fileName the name of the input file to read from.
     * @return List of String Array containing the user name.
     * @throws IOException when unable to read the file.
     */
    public List<String[]> readFile(String fileName) throws IOException {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(path + fileName));
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        ) {
            List<String[]> list = new ArrayList<>();
            list = csvReader.readAll();
            reader.close();
            csvReader.close();
            return list;
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
