package duke.storage;

import com.opencsv.CSVWriter;

import duke.exceptions.DukeException;
import duke.models.locker.Locker;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class OpenCsv {
    private static final String CSV_OUTPUT_PATH = "data.csv";
    private static final int FIRST_COLUMN = 0;
    private static final int SECOND_COLUMN = 1;
    private static final int THIRD_COLUMN = 2;
    /**
     * This function exports a CSV file.
     * @throws DukeException when there are errors while handling the file.
     */

    public static void exportLockers(List<Locker> lockerList) throws DukeException {
        try {
            Writer writer = Files.newBufferedWriter(Paths.get(CSV_OUTPUT_PATH));

            CSVWriter csvWriter = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] header = {"Locker", "Zone", "Status"};
            csvWriter.writeNext(header);

            for (Locker l : lockerList) {

                String[] details = new String[header.length];
                details[FIRST_COLUMN] = l.getSerialNumber().getSerialNumberForLocker();
                details[SECOND_COLUMN] = l.getZone().getZone();
                details[THIRD_COLUMN] = l.getTag().getTagName();

                csvWriter.writeNext(details);
            }
            csvWriter.close();
        } catch (IOException e) {
            throw new DukeException(" Unable to export csv ");
        }
    }
}