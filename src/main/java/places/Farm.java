package places;

import org.json.simple.JSONObject;

public abstract class Farm {
    protected int price;

    public abstract int sell();

    public abstract JSONObject toJson();
}
