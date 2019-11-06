package places;

public class Market {
    public static final int PRICE_OF_SEED = 10;
    public static final int PRICE_OF_WHEAT = 15;
    public static final int PRICE_OF_CHICKEN = 100;
    public static final int PRICE_OF_EGG = 10;
    public static final int PRICE_OF_COW = 500;
    public static final int PRICE_OF_MILK = 15;
    protected int gold;

    public Market(int startingGold) {
        this.gold = startingGold;
    }

    public int getGold() {
        return gold;
    }

}

//    public void changeGold(int change) {
//        gold += change;
//    }
//
//
//    public JSONObject toJSON() {
//        JSONObject obj = new JSONObject();
//        obj.put("gold", gold);
//        return obj;
//    }