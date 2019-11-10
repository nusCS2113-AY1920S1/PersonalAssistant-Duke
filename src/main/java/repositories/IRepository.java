package repositories;

import java.util.ArrayList;

//@@author Lucria
public interface IRepository<T> {
    ArrayList<T> getAll();

    boolean addToRepo(String input);

    boolean deleteItem(int indexNumber);

    T getItem(int indexNumber);

    boolean updateItem(T object, String input);

    void saveToRepo(T object);
}
