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
import java.util.HashMap;
import java.util.Map;

/**
 * CounterStorage.java - a class for load/save of command frequency in csv format,
 * written with Storage template written by HUANG XUAN KUN
 *
 * @author QIAN JIE
 * @version 1.3
 */

public class CounterStorage {
    /**
     * A string that represents a relative file path from the project folder.
     */
    private String filePath;

    /**
     * Constructs a Storage object with a specific file path.
     *
     * @param filePath A string that represents the path of the file to read or
     *                 write.
     */
    public CounterStorage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Load the command counter table from local csv files.
     *
     * @return A Map with key being the command name and value being the counts.
     * @throws DukeException throw a dukeException with error message for debugging.
     */
    public Map<String, Integer> load() throws DukeException {
        Map<String, Integer> cmdFreqTable = new HashMap<>();
        File csvFile = new File(filePath);
        try {
            if (csvFile.createNewFile()) {
                System.out.println("File " + filePath + " is created.");
            } else {
                Reader in = new FileReader(filePath);
                Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
                for (CSVRecord record : records) {
                    String commandName = record.get("Command Name");
                    Integer frequency = Integer.parseInt(record.get("Frequency"));
                    cmdFreqTable.put(commandName, frequency);
                }
            }
            return cmdFreqTable;
        } catch (Exception e) {
            throw new DukeException("Loading of "
                    + filePath
                    + "is unsuccessful."
                    + "e.getMessage()");
        }
    }

    /**
     * Write the key value set of command count table info to local csv files.
     *
     * @param cmdFreqTable A list of patients containing info of patients to be written
     * @throws DukeException throw exception with error message when i/o fails
     */
    public void save(Map<String, Integer> cmdFreqTable) throws DukeException {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("Command Name", "Frequency"));
            for (Map.Entry<String, Integer> entry : cmdFreqTable.entrySet()) {
                String commandName = entry.getKey();
                String frequency = entry.getValue().toString();
                csvPrinter.printRecord(commandName, frequency);
            }
            csvPrinter.flush();
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
    }
}
