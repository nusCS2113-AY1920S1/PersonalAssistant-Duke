package duke.storage;

import duke.core.DukeException;
import duke.patient.Patient;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * TaskStorage.java - a class for writing/reading patient info to/from local in csv format.
 * @author  HUANG XUAN KUN
 * @version 1.2
 */
public class PatientStorage {

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
    public PatientStorage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Load the patients' info from local csv files
     *
     * @return A arrayList of Patient which contain info of patients
     * @throws DukeException throw a dukeException with error message for debugging
     */
    public ArrayList<Patient> load() throws DukeException {
        ArrayList<Patient> patientList = new ArrayList<Patient>();
        File csvFile = new File(filePath);
        try {
            if (csvFile.createNewFile()) {
                System.out.println("File " + filePath + " is created.");
            } else {
                Reader in = new FileReader(filePath);
                Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
                for (CSVRecord record : records) {
                    int id = Integer.parseInt(record.get("Id"));
                    String name = record.get("Name");
                    String nric = record.get("NRIC");
                    String remark = record.get("Remark");
                    String room = record.get("Room");
                    patientList.add(new Patient(id, name, nric, room, remark));
                }
            }
            return patientList;
        } catch (Exception e) {
            throw new DukeException("Loading of " + filePath + "is unsuccessful.\n" +
                    "e.getMessage()");
        }
    }

    /**
     * Write the patients' info to local csv files
     *
     * @param patients A list of patients containing info of patients to be written
     * @throws DukeException throw exception with error message when i/o fails
     */
    public void save(ArrayList<Patient> patients) throws DukeException {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("Id", "Name", "NRIC", "Room", "Remark"));
            for (Patient patient : patients) {
                int id = patient.getID();
                String room = patient.getRoom();
                String NRIC = patient.getNRIC();
                String name = patient.getName();
                String remark = patient.getRemark();
                csvPrinter.printRecord(id, name, NRIC, room, remark);
            }
            csvPrinter.flush();
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
    }
}
