package duke.storage;

import duke.core.DukeException;
import duke.patient.Patient;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;

public class PatientStorage {

    /**
     * A string that represents a relative file path from the project folder.
     */
    private String filePath;

    /**
     * Constructs a Storage object with a specific file path.
     *
     * @param path A string that represents the path of the file to read or
     *             write.
     */
    public PatientStorage(String path) {
        this.filePath = path;
    }

    public ArrayList<Patient> Load() throws DukeException {
        ArrayList<Patient> patientList = new ArrayList<Patient>();
        try {
            Reader in = new FileReader(filePath);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            //        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader("Last Name", "First Name").withFirstRecordAsHeader().parse(in).getRecords();
            for (CSVRecord record : records) {
                boolean isHospitalised = false;
                int id = Integer.parseInt(record.get("Id"));
                String name = record.get("Name");
                String remark = record.get("Remark");
                String room = record.get("Room");
                String hospitalised = record.get("Hospitalised");
                if (hospitalised.equals("True")) {
                    isHospitalised = true;
                }
                patientList.add(new Patient(id, name, remark, room, isHospitalised));
                System.out.println(id + " | " + name + "|" + remark + "|" + room);
            }
            return patientList;
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
    }
}
