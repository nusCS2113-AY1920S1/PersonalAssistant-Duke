package duke.recipebook;

import duke.storage.Storage;
import duke.ui.Ui;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class dishes{

    private String dishname;
    private int total;
    private float rating;

    public dishes(String name, int amount) {
        this.dishname = name;
        this.total = amount;
    }

    public dishes() {
        //
    }

    public int getAmount() {
        return total;
    }

    public void setAmount(int amount) {
        total = total + amount;
    }

    public void clearAmount() {
        total = 0;
    }

    public String getDishname() {
        return dishname;
    }

    public void setRating(int r) {
        rating = (total * rating + r) / total;
    }

    public float getRating() {
        return rating;
    }

    public String toString() {
        return dishname;
    }
}
