package duke.storage;

import com.opencsv.CSVWriter;

import duke.exceptions.DukeException;
import duke.models.locker.Locker;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class ExportCsv {
    private static final String CSV_OUTPUT_PATH = "export.csv";
    private static final int FIRST_COLUMN = 0;
    private static final int SECOND_COLUMN = 1;
    private static final int THIRD_COLUMN = 2;
    private static final int FORTH_COLUMN = 3;
    private static final int FIFTH_COLUMN = 4;
    private static final int SIXTH_COLUMN = 5;
    private static final int SEVENTH_COLUMN = 6;
    private static final int EIGHTH_COLUMN = 7;
    private static final int NINTH_COLUMN = 8;
    private static final int TENTH_COLUMN = 9;

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

            String[] header = {"Locker", "Address", "Zone", "Status",
                "Name", "StudentID", "Course", "Email",
                "Start-Date","End-Date" };

            csvWriter.writeNext(header);

            for (Locker l : lockerList) {

                String[] details = new String[header.length];
                details[FIRST_COLUMN] = l.getSerialNumber().getSerialNumberForLocker();
                details[SECOND_COLUMN] = l.getAddress().getAddress();
                details[THIRD_COLUMN] = l.getZone().getZone();
                details[FORTH_COLUMN] = l.getTag().getTagName();

                if (details[FORTH_COLUMN].equals("in-use")) {
                    
                    details[FIFTH_COLUMN] = l.getUsage().get().getStudent().getName().getName();
                    details[SIXTH_COLUMN] = l.getUsage().get().getStudent().getStudentId().getStudentId();
                    details[SEVENTH_COLUMN] = l.getUsage().get().getStudent().getMajor().getCourse();
                    details[EIGHTH_COLUMN] = l.getUsage().get().getStudent().getEmail().getEmail();
                    details[NINTH_COLUMN] = l.getUsage().get().getStartDate().getDate();
                    details[TENTH_COLUMN] = l.getUsage().get().getEndDate().getDate();

                }

                csvWriter.writeNext(details);
            }

            csvWriter.close();

        } catch (IOException e) {
            throw new DukeException(" Unable to export csv ");
        }
    }

}