package repositories;

import java.util.ArrayList;

//@@author Lucria
public interface IRepository<T> {
    ArrayList<T> getAll();

    boolean addToRepo(String input);

    boolean deleteItem(int projectNumber);

    T getItem(int projectNumber);
}
