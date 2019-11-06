package places;

import org.json.simple.JSONObject;

public abstract class Farm {
    protected int price; //gold to get from selling all the stuff

    public abstract int sell();

    public abstract JSONObject toJson();
}
