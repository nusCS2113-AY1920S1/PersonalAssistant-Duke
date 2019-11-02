package farmio;

import exceptions.FarmioException;
import exceptions.FarmioFatalException;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public interface Storage {

    /**
     * Check the existence of a save.json file on the current working directory.
     * @return True if save.json exist and false if save.json does not exist.
     */
    boolean getSaveExist();

    /**
     * Reads save.json file as an json formatted file and returns farmer JSON data.
     * @return  JSONObject containing all farmer assets.
     * @throws FarmioException Error reading or parsing save.json.
     */
    JSONObject loadFarmer() throws FarmioException;

    /**
     * A recovery process to load farmer json data from previous successful storeFarmer.
     * @return JSONObject that represent farmer object in json format.
     * @throws FarmioException No previous successful save or
     */
    JSONObject loadFarmerBackup() throws FarmioException;

    /**
     * Gets json representation of farmer form the argumentand write json data into save.json.
     * @param farmer Farmio.Farmer object to be stored in to save.json.
     */
    boolean storeFarmer(Farmer farmer);

    /**
     * Get previous successful storeFarmer, update task list in json data and write to save.json.
     * @param farmer Farmer instance to be updated to from json data.
     * @return True if save is successful.
     */
    String storeFarmerPartial(Farmer farmer);

    /**
     * Retirve frames from resource folder and formats frame into string array list.
     * @param path the path of the frames in resource folder.
     * @param frameId frame number to be read.
     * @param frameWidth Width of the fame to be filled.
     * @param frameHeight Height of the frame to be filled.
     * @return An string array list of a specified frame in path.
     * @throws FarmioFatalException In intolerable rror reading frame from path.
     */
    ArrayList<String> loadFrame(String path, int frameId, int frameWidth, int frameHeight) throws FarmioFatalException;

    /**
     * Retrive level information from resource folder.
     * @param level level number to be retrived
     * @return level json data in JSONObject to be restored using Level constructor.
     * @throws FarmioFatalException Intolerable error when reading level json file.
     */
    JSONObject getLevel(double level) throws FarmioFatalException;
}