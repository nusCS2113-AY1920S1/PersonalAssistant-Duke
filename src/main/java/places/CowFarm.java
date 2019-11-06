package places;

import exceptions.FarmioException;
import org.json.simple.JSONObject;

public class CowFarm extends Farm {
    private final String JSON_KEY_MILK = "milk";
    private final String JSON_KEY_COW = "cow";
    private final String JSON_KEY_FULLCOW = "fullcow";

    private int milk;
    private int cow;
    private int fullCow;

    /**
     * Checks whether the cowfarm currently has Milk.
     * @return true if milkfarm has milk.
     * @return false if milkfarm has no milk.
     */
    public boolean hasMilk() {
        return milk > 0;
    }

    /**
     * Checks whether the cowfarm currently has cow.
     * @return true if cowfarm has cow.
     * @return false if cowfarm has no cow.
     */
    public boolean hasCow() {
        return cow > 0;
    }

    /**
     * Checks whether the cowfarm currently has fullCow.
     * @return true if cowfarm has fullCow.
     * @return false if cowfarm has no fullCow.
     */
    public boolean hasFullCow() {
        return fullCow > 0;
    }

    public CowFarm() {
        cow = 0;
        milk = 0;
        fullCow = 0;
    }

    public CowFarm(JSONObject obj) throws FarmioException {
        try {
            this.cow = (int) (long) obj.get(JSON_KEY_COW);
            this.milk = (int) (long) obj.get(JSON_KEY_MILK);
            this.fullCow = (int) (long) obj.get(JSON_KEY_FULLCOW);

        } catch(Exception e){
            throw new FarmioException("Game save corrupted!");
        }
    }
    @Override
    public int sell() {
        return 0;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        return obj;
    }
}
