package Places;

import org.json.simple.JSONObject;

public class Market {
    private final int PRICE_OF_SEED = 1;
    private final int PRICE_OF_WHEAT = 5;
    private final int PRICE_OF_CHICKEN = 100;
    private final int PRICE_OF_EGG = 10;
    private final int PRICE_OF_COW = 500;
    private final int PRICE_OF_MILK = 15;
    protected int money;

    public Market (int startingMoney) {
        this.money = startingMoney;
    }

    public int getMoney() {
        return money;
    }

    public void changeMoney(int change) {
        money += change;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("money", money);
        return obj;
    }
}
