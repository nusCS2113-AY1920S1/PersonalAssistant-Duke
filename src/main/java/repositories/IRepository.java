package repositories;

import java.util.ArrayList;

public interface IRepository<T> {
    ArrayList<T> getAll();

    boolean addToRepo(String input);

    boolean deleteItem(int projectNumber);

    T getItem(int projectNumber);
}
