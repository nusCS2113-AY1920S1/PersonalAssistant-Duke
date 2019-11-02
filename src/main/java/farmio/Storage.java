package farmio;

import exceptions.FarmioException;
import exceptions.FarmioFatalException;
import frontend.AsciiColours;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.function.LongFunction;
import java.util.logging.*;
import java.util.logging.Level;

public class Storage {

    private String GAME_FILENAME = "save.json";
    private JSONObject jsonFarmer;
    private final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Storage constructor to initialise storage object.
     */
    public Storage() {
        jsonFarmer = null;
    }

    void setupLogger() throws FarmioFatalException {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        Logger rootLogger = Logger.getLogger("global");
        Handler[] handlers = rootLogger.getHandlers();
        for(Handler handler: handlers){
            rootLogger.removeHandler(handler);
        }
        logger.setLevel(Level.INFO);
        FileHandler handler = null;
        try {
            handler = new FileHandler("farmio.log");
        } catch (IOException e) {
            throw new FarmioFatalException("Failed to access \'farmio.log\'.\nPlease try running farmio in another directory.");
        }
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
    }

    /**
     * Check the esistence of a save.json file on the application directory.
     * @return True is save.json exist and false if save.json does not exist.
     */
    boolean getSaveExist() {
        return new File(GAME_FILENAME).exists();
    }

    /**
     * Read save.json file as an json formatted file
     * and returns JSON representation of farmer.
     * @return  JSONObject containing all farmer assets.
     * @throws FarmioException Error reading or parsing save.json.
     */
    public JSONObject loadFarmer() throws FarmioException {
        Reader reader = null;
        try {
            reader = new FileReader(GAME_FILENAME);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, e.toString());
            throw new FarmioException("Game save not found!");
        }
        JSONParser parser = new JSONParser();
        try {
            jsonFarmer = (JSONObject) parser.parse(reader);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.toString());
            throw new FarmioException("Game save corrupted!");
        }
        try{
            double level = (Double) jsonFarmer.get("level");
            if(!getLevelExist(level)){
                LOGGER.log(Level.INFO, "Detected invalid level: " + level);
                throw new FarmioException("Game level corrupted!");
            }
        }catch(Exception e){
            LOGGER.log(Level.WARNING, "Failed level double check. >" + e.toString());
        }
        return jsonFarmer;
    }

    /**
     * A recovery process to load farmer json data from previous successful
     * saved farmer json data stored withing the application during runtime.
     * Method also attempts to write previous successful saved farmer json data
     * to save.json.
     * @return JSONObject that represent farmer object in json format.
     * @throws FarmioException No previous successful save or
     */
    public JSONObject loadFarmerBackup() throws FarmioException {
        if(jsonFarmer == null){
            LOGGER.log(Level.INFO, "jsonFarmer is empty! Recovery failed.");
            throw new FarmioException("Recovery failed!");
        }
        recoverFarmer();
        return jsonFarmer;
    }

    /**
     * Gets json representation of farmer form the argumentand write json data into save.json.
     * @param farmer Farmio.Farmer object to be stored in to save.json.
     */
    public boolean storeFarmer(Farmer farmer) {
        FileWriter file;
        try {
            file = new FileWriter(GAME_FILENAME);
            jsonFarmer = farmer.toJson();
            file.write(jsonFarmer.toJSONString());
            file.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Get previous successful famer json data save and update task list in json data.
     * After updat, json data will be stored in save.json.
     * @param farmer Farmer instance to be updated to from json data.
     * @return True if save is successful.
     */
    public String storeFarmerPartial(Farmer farmer) {
        if(jsonFarmer == null){
            return null;
        }
        FileWriter file;
        try {
            file = new FileWriter(GAME_FILENAME);
            jsonFarmer = farmer.updateJSON(jsonFarmer);
            file.write(jsonFarmer.toJSONString());
            file.close();
        } catch (IOException e) {
            return null;
        }
        return System.getProperty("user.dir").concat(GAME_FILENAME);
    }

    /**
     * Write previous successful farmer save into save.json.
     * @return  True if save is successful.
     */
    private boolean recoverFarmer() {
        FileWriter file;
        try {
            file = new FileWriter(GAME_FILENAME);
            file.write(jsonFarmer.toJSONString());
            file.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Retirve frames from resource folder using path parameter. Reads and formats frame into string array list.
     * @param path the path of the frames in resource folder.
     * @param frameId frame number to be read.
     * @param frameWidth Width of the fame to be filled.
     * @param frameHeight Height of the frame to be filled.
     * @return An string array list of a specified frame in path.
     * @throws FarmioFatalException In intolerable rror reading frame from path.
     */
    public ArrayList<String> loadFrame(String path, int frameId, int frameWidth, int frameHeight) throws FarmioFatalException {
        path = "asciiArt/" + path + "/frame" + frameId + ".txt";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getResourceStream(path)));
        String line;
        ArrayList<String> frame = new ArrayList<>();
        while (true) {
            try {
                if ((line = bufferedReader.readLine()) == null) break;
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, e.toString());
                throw new FarmioFatalException(formatFatalMessage(path));
            }
            if (line.length() <= frameWidth) {
                line = String.format("%-" + frameWidth + "s", line);
            } else {
                line = String.format("%" + frameWidth + "s", "");
            }
            frame.add(
                    "|" +
                            AsciiColours.BACKGROUND_WHITE +
                            AsciiColours.BLACK +
                            line +
                            AsciiColours.SANE +
                            "|"
            );
        }
        if (frame.size() < frameHeight) {
            for (int i = frame.size(); i < frameHeight; i++) {
                frame.add(
                        "|" +
                                AsciiColours.BACKGROUND_WHITE +
                                AsciiColours.BLACK +
                                String.format("%" + frameWidth + "s", "") +
                                AsciiColours.SANE +
                                "|"
                );
            }
        }
        return frame;
    }

    private boolean getLevelExist(double level){
        String path = "levels/" + level + ".json";
        if(getResourceStream(path) != null){
            return true;
        }
        return false;
    }

    /**
     * Retrive specific level information from resource folder.
     * @param level level number to be retrived
     * @return level json data in JSONObject to be restored using Level constructor.
     * @throws FarmioFatalException Intolerable error when reading level json file.
     */
    public JSONObject getLevel(double level) throws FarmioFatalException {
        String path = "levels/" + level + ".json";
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(new InputStreamReader(getResourceStream(path)));
        } catch (IOException | ParseException e) {
            LOGGER.log(Level.WARNING, e.toString());
            throw new FarmioFatalException(formatFatalMessage(path));
        }
    }

    /**
     * Locate path in resouces folder and return stream to be processed.
     * @param path Path of interest in resouce folder.
     * @return InputStream to be process using InputStreamReader.
     */
    private InputStream getResourceStream(String path) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(path);
    }

    /**
     * Format fatal message to be displayed in ui.
     * @param path Path that caused a FarmioFatalException.
     * @return Formatted error message to be displayed.
     */
    private String formatFatalMessage(String path) {
        return "\"" + path + "\" not found!";
    }
}
