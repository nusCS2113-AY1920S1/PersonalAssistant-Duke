package Places;

import org.json.simple.JSONObject;

public class CowFarm extends Farm {
    private int milk;
    @Override
    public int sell() {
        return 0;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        return obj;
    }

    public CowFarm() {
    }

    public CowFarm(JSONObject obj) {
    }
}
