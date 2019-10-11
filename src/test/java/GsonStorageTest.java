import duke.exception.DukeFatalException;
import duke.task.GsonStorage;
import duke.task.Patient;
import org.junit.jupiter.api.Test;


/**
 * JUnit class testing the class GsonStorage.
 *
 */
class GsonStorageTest {
    private String filePath = "data/patients.json";

    GsonStorageTest() throws DukeFatalException {
        GsonStorage gsonStorage = new GsonStorage(filePath);
    }

     /**
      * Creates 4 patient objects that only have a name, a bedNo and their allergies.
      * Then adds them to the patient hashmap.
      */
    void addDummyPatients() {
            GsonStorage.addPatientToMap(new Patient("dummy1", 100, "nuts"));
            GsonStorage.addPatientToMap(new Patient("dummy2", 200, null));
            GsonStorage.addPatientToMap(new Patient("dummy3", 300, "cats"));
            GsonStorage.addPatientToMap(new Patient("dummy4", 400, "nuts"));
        }

     /**
      * Creates 4 patient objects and gives assign values to all of their attributes.
      * Then adds them to the patient hashmap.
      */
    void addComplexPatients(){
    }

     /**
      * Creates the Json representation of all the patients in the hash map and adds them to the json file.
      * Then recreates the patient objects based on what is in the json file.
      * When the recreation is done it checks if the first patients are identical to the new ones.
      */
    @Test
    void identical() {
    }

   /**
    * Tests if patients are transformed from the json file to the hashmap properly.
    */
    @Test
    void loadPatientHashMapTest() {
    }

  /**
   * Tests if patients are transformed from the hashmap to the json file properly.
   */
    @Test
    void writeJsonFileTest() {
    }
}

// don't forget;
// change the name to storage later when storage class have been removed
// maybe put in exception that handles if the user alters with the json file
// change back the toString method in patients
// change the filePath in DukeLauncher to point at the json file instead of the tsv file

