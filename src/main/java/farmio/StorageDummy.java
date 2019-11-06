package farmio;

import exceptions.FarmioException;
import exceptions.FarmioFatalException;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class StorageDummy implements Storage {

    @Override
    public boolean getSaveExist() {
        return false;
    }

    @Override
    public JSONObject loadFarmer() throws FarmioException {
        return null;
    }

    @Override
    public JSONObject loadFarmerBackup() throws FarmioException {
        return null;
    }

    @Override
    public boolean storeFarmer(Farmer farmer) {
        return false;
    }

    @Override
    public String storeFarmerPartial(Farmer farmer) {
        return null;
    }

    @Override
    public ArrayList<String> loadFrame(String path, int frameId, int frameWidth, int frameHeight)
            throws FarmioFatalException {
        return null;
    }

    @Override
    public JSONObject getLevel(double level) throws FarmioFatalException {
        return null;
    }
}