import duke.exception.DukeFatalException;
import duke.task.GsonStorage;
import duke.task.Patient;

import org.junit.jupiter.api.Test;

class GsonStorageTest {
    private String filePath = "data/patients.json";

    GsonStorageTest() throws DukeFatalException {
        GsonStorage gsonStorage = new GsonStorage(filePath);
    }
    
    @Test
    void addDummyPatients() {
            GsonStorage.addPatientToMap(new Patient("dummy1", 100, "nuts"));
            GsonStorage.addPatientToMap(new Patient("dummy2", 200, null));
            GsonStorage.addPatientToMap(new Patient("dummy3", 300, "cats"));
            GsonStorage.addPatientToMap(new Patient("dummy4", 400, "nuts"));
        }
}





// don't forget;
// change the name to storage later when storage class have been removed
// remove the test functions in the constructor later
// remove all the prints
// maybe put in exception that handles if the user alters with the json file
// move the dummy function to a testfile
// change back the toString method in patients
// change the filePath in DukeLauncher to point at the json file instead of the tsv file

//gsonStorage.loadPatientHashMap();
//gsonStorage.loadHashMapWithDummyPatients();
//gsonStorage.writeJsonFile();