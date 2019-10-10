package Places;

import org.json.simple.JSONObject;

public abstract class Farm {
    protected int price; //money to get from selling all the stuff

    public abstract int sell();
    public abstract JSONObject toJSON();
}
