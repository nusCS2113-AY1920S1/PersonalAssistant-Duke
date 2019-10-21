package duke.model;

import duke.commons.exceptions.DukeDuplicateRouteException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.DukeRouteNotFoundException;
import duke.model.locations.Route;
import duke.model.locations.RouteNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;
import java.util.List;

public class RouteList implements Iterable<Route> {
    private ObservableList<Route> internalList;

    public RouteList() {
        internalList = FXCollections.observableArrayList();
    }

    public Route get(int index) throws IndexOutOfBoundsException {
        return internalList.get(index);
    }

    public int size() {
        return internalList.size();
    }

    /**
     * Returns true if the list contains an equivalent Task as the given argument.
     */
    public boolean contains(Route toCheck) {
        return internalList.stream().anyMatch(toCheck::isSameRoute);
    }

    /**
     * Adds a Route to the list.
     * The Task must not already exist in the list.
     */
    public void add(Route toAdd) throws DukeException {
        if (contains(toAdd)) {
            throw new DukeDuplicateRouteException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The Task identity of {@code editedTask} must not be the same as another existing Task in the list.
     */
    public void setTask(Route target, Route editedRoute) throws DukeException {
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DukeRouteNotFoundException();
        }

        if (!target.isSameRoute(editedRoute) && contains(editedRoute)) {
            throw new DukeDuplicateRouteException();
        }

        internalList.set(index, editedRoute);
    }

    /**
     * Removes the equivalent Task from the list.
     * The Task must exist in the list.
     */
    public void remove(Route toRemove) throws DukeException {
        if (!internalList.remove(toRemove)) {
            throw new DukeRouteNotFoundException();
        }
    }

    public Route remove(int index) throws IndexOutOfBoundsException {
        return internalList.remove(index);
    }

    public void setTasks(RouteList replacement) {
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Tasks}.
     * {@code Tasks} must not contain duplicate Tasks.
     */
    public void setTasks(List<Route> routes) throws DukeException {
        if (!routesAreUnique(routes)) {
            throw new DukeDuplicateRouteException();
        }

        internalList.setAll(routes);
    }

    @Override
    public Iterator<Route> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RouteList // instanceof handles nulls
                && internalList.equals(((RouteList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code Tasks} contains only unique Tasks.
     */
    private boolean routesAreUnique(List<Route> routes) {
        for (int i = 0; i < routes.size() - 1; i++) {
            for (int j = i + 1; j < routes.size(); j++) {
                if (routes.get(i).isSameRoute(routes.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public ObservableList<Route> getRoutes() {
        return internalList;
    }
}

