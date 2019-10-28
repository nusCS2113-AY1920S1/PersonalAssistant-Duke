package places;

import org.json.simple.JSONObject;

public class ChickenFarm extends Farm {
    @Override
    public int sell() {
        return 0;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        return obj;
    }

    public ChickenFarm() {
    }

    public ChickenFarm(JSONObject obj) {
    }
}