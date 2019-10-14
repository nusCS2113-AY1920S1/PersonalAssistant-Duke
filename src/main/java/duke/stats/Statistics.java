package duke.stats;

import javafx.util.Pair;

import java.nio.file.Path;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Statistics {
    private Path path;
    private Map<Dish, Pair<Integer, Integer>> ratedDishes;

    public Statistics(Path path, List<Dish> dishes) {
        this.path = path;
        this.dishes = new HashSet<Dish>();
        this.quantityDishes = new Map<Integer, Dish>();
        this.ratedDishes = new Map<Integer, Dish>();
    }

    public void update(Order order) {
        for (Dish dish : order.getDishes()) {
            ratedDishes.putIfAbsent(dish, new Pair(0, 0));
            int newTotal = ratedDishes.get(dish).getValue() + dish.getAmount();
            int newRating = (ratedDishes.get(dish).getKey() + dish.getRating()*dish.getAmount())/newTotal;
            ratedDishes.put(dish, new Pair<>(newRating, newTotal));
        }
    }

    public List<Dish> getTopRated() {
        return ratedDishes
                .entrySet()
                .stream()
                .sorted(new Comparator<Map.Entry<Dish, Pair<Integer, Integer>>>() {
                    @Override
                    public int compare(Map.Entry<Dish, Pair<Integer, Integer>> o1, Map.Entry<Dish, Pair<Integer, Integer>> o2) {
                        return o2.getValue().getKey().compareTo(o1.getValue().getKey());
                    }
                })
                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new)).keySet();

    }

    public List<Dish> getMostOrdered() {
        return ratedDishes
                .entrySet()
                .stream()
                .sorted(new Comparator<Map.Entry<Dish, Pair<Integer, Integer>>>() {
                    @Override
                    public int compare(Map.Entry<Dish, Pair<Integer, Integer>> o1, Map.Entry<Dish, Pair<Integer, Integer>> o2) {
                        return o2.getValue().getValue().compareTo(o1.getValue().getValue());
                    }
                })
                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new)).keySet();

    }

}
