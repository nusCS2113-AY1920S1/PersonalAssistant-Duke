package duke.stats;

import duke.task.Dish;
import javafx.util.Pair;

import java.nio.file.Path;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Statistics {
    private Path path;
    private Map<Dish, Pair<Double, Integer>> ratedDishes;

    public Statistics(Path path, List<Dish> dishes) {
        this.path = path;
        this.ratedDishes = new HashMap<Dish, Pair<Double, Integer>>();
    }

    public void update(OrderList orderList) {
        for (Order order : orderList.getAllOrders()) {
            Dish dish = order.getDish();
            ratedDishes.putIfAbsent(dish, new Pair(0, 0));
            int newTotal = ratedDishes.get(dish).getValue() + dish.getAmount();
            double newRating = (ratedDishes.get(dish).getKey() + dish.getRating() * dish.getAmount()) / newTotal;
            ratedDishes.put(dish, new Pair<>(newRating, newTotal));
        }
    }

    public Set<Dish> getTopRated() {
        return ratedDishes
                .entrySet()
                .stream()
                .sorted(new Comparator<Map.Entry<Dish, Pair<Double, Integer>>>() {
                    @Override
                    public int compare(Map.Entry<Dish, Pair<Double, Integer>> o1, Map.Entry<Dish, Pair<Double, Integer>> o2) {
                        return o2.getValue().getKey().compareTo(o1.getValue().getKey());
                    }
                })
                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new)).keySet();

    }

    public Set<Dish> getMostOrdered() {
        return ratedDishes
                .entrySet()
                .stream()
                .sorted(new Comparator<Map.Entry<Dish, Pair<Double, Integer>>>() {
                    @Override
                    public int compare(Map.Entry<Dish, Pair<Double, Integer>> o1, Map.Entry<Dish, Pair<Double, Integer>> o2) {
                        return o2.getValue().getValue().compareTo(o1.getValue().getValue());
                    }
                })
                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new)).keySet();
    }

}
