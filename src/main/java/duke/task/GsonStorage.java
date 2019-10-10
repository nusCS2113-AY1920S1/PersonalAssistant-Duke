package duke.task;

import com.google.gson.Gson;
import duke.exception.DukeFatalException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// change name of this class to just Storage later when the Storage
// class have been removed

public class GsonStorage {

    private final File jsonFile;
    private final String filePath;
    private HashMap<String, Patient> patientMap = new HashMap<String, Patient>();

    /**
     * Constructs a new GsonStorage object, with the json file at the specified
     * path, and create HashMap for quick patient lookup.
     *
     * @throws DukeFatalException If data file cannot be setup.
     */

    public GsonStorage(String path) throws DukeFatalException {
        filePath = path;
        jsonFile = new File(filePath);
        if (!jsonFile.exists()) {
            try {
                if (!jsonFile.createNewFile()) {
                    throw new IOException();
                }
            } catch (IOException excp) {
                throw new DukeFatalException("Unable to setup data file, try checking your permissions?");
            }
        }
        if(jsonFile.length()!=0){
            loadJsonFile();
        }
    }

    private void loadJsonFile() throws DukeFatalException {
        try {
            String content = Files.readString(Paths.get(filePath), StandardCharsets.US_ASCII);
            System.out.println(content);
            Patient[] patientList = new Gson().fromJson(content, Patient[].class);                  //this line causes error - null pointer
////          first parameter is the Json representations of the patients and the second states that the object we want to create has the class patient
  //           System.out.println(patientList);
//            //for (Patient thePatient : patientList) {
//              //  patientMap.put(thePatient.getName(), thePatient);
//                //System.out.println("test");
        } catch (IOException excp) {
            throw new DukeFatalException("Unable to load data file, try checking your permissions?");
        } catch (com.google.gson.JsonSyntaxException e) {
            throw new DukeFatalException("Data file has been corrupted!");
        }
    }
   
//
        // so if you get here there is a json file and then all json representations of the patient
        // objects that are in the json file must be transformed into patients objects that should
        // be added to the hashmap  https://www.youtube.com/watch?v=ou2yFJ-NWr8&t=284s
        // https://www.youtube.com/watch?v=ZZddxpxGQPE
        // should be able to handle null values - test it


    /**
     * Creates the Json representation of every patient in the patient
     * hash map and then stores these representations in the Json file.
     *
     * @throws DukeFatalException If the file writer cannot be setup.
     */
    public void writeJsonFile() throws DukeFatalException {
        try {
            FileWriter fileWriter = new FileWriter(jsonFile);
            for (Map.Entry entry : patientMap.entrySet()) {
                String json = new Gson().toJson(entry.getValue());
                fileWriter.write(json);
            }
            fileWriter.close();
        } catch (IOException excp) {
            throw new DukeFatalException("Unable to write data! Some data may have been lost,");
        } // test if creating the fileWriter deletes everything in the json file or not
    }
}

