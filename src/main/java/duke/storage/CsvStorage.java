package duke.storage;

import duke.core.DukeException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class CsvStorage extends Storage {

    private String filePath;

    /**
     * Constructs a Storage object with a specific file path.
     *
     * @param filePath A string that represents the path of the file to read or
     *                 write.
     */
    public CsvStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    /**
     * .
     * @param infoList A list of records to be to be written in rows to csv file
     * @param headers
     * @throws DukeException
     */
    public void write(ArrayList<ArrayList<String>> infoList, String[] headers) throws DukeException {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(this.filePath));
            // Set up csv printer with headers given
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers));
            for (ArrayList<String> row : infoList) {
                csvPrinter.printRecord(row);
            }
            csvPrinter.flush();
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
    }


    /**
     * Read data from the file and store into a ArrayList of task.
     *
     * @return A list of rows in Map with header as key and column in row as value
     * @throws DukeException If file is not found.
     */
    public ArrayList<Map<String, String>> read() throws DukeException {
        // Initialize capacity for 3000 rows of records.
        ArrayList<Map<String, String>> infoList = new ArrayList<>(3000);
        File csvFile = new File(filePath);
        try {
            if (csvFile.createNewFile()) {
                System.out.println("File " + filePath + " is created.");
            } else {
                Reader in = new FileReader(filePath);
                Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
                for (CSVRecord record : records) {
                    // There are maximum 9 columns per row
                    Map<String, String> headerValueMap = record.toMap();
                    infoList.add(headerValueMap);
                }
            }
            return infoList;
        } catch (Exception e) {
            throw new DukeException("Loading of "
                    + filePath
                    + " is unsuccessful.\n"
                    + "e.getMessage()");
        }
    }
}
