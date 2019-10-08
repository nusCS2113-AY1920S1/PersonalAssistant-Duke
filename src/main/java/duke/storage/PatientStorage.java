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

public class PatientStorage extends Storage<Patient>{

    /**
     * A string that represents a relative file path from the project folder.
     */
    private String filePath;

    /**
     * Constructs a Storage object with a specific file path.
     *
     * @param filePath A string that represents the path of the file to read or
     *             write.
     */
    public PatientStorage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Patient> load() throws DukeException {
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
                String hospitalised = record.get("isHospitalised");
                if (hospitalised.equals("TRUE")) {
                    isHospitalised = true;
                }
                patientList.add(new Patient(id, name, remark, room, isHospitalised));
                System.out.println(id + " | " + name + " | " + remark + " | " + room + " | " + isHospitalised);
            }
            return patientList;
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
    }

    @Override
    public void save(ArrayList<Patient> patients) throws DukeException {
        try{
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("ID", "Name", "Room", "Remark", "isHospitalised"));
//            ArrayList<Patient> patients = patientList.getPatientList();
            for (Patient patient : patients){
                int id = patient.getID();
                String room = patient.getRoom();
                String name = patient.getName();
                boolean isHospitalised = patient.isHospitalised();
                String remark = patient.getRemark();
                csvPrinter.printRecord(id, name, room, remark, isHospitalised);
            }
            csvPrinter.flush();
        }
        catch(IOException e){
            throw new DukeException(e.getMessage());
        }
    }
}
