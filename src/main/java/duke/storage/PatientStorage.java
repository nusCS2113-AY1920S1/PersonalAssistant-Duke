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

public class PatientStorage extends Storage<Patient> {

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

    public ArrayList<Patient> load() throws DukeException {
        ArrayList<Patient> patientList = new ArrayList<Patient>();
        try {
            Reader in = new FileReader(filePath);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                int id = Integer.parseInt(record.get("Id"));
                String name = record.get("Name");
                String nric = record.get("NRIC");
                String remark = record.get("Remark");
                String room = record.get("Room");
                patientList.add(new Patient(id, name, nric, room, remark));
//                System.out.println(id + " | " +  name + " | " + nric + " | " + remark + " | " + room); //List out patietns info for debugging
            }
            return patientList;
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
    }

    @Override
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
