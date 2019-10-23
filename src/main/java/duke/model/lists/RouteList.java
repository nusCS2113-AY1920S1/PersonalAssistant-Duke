package duke.model.lists;

import duke.commons.exceptions.DukeDuplicateRouteException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.DukeRouteNotFoundException;
import duke.model.transports.Route;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RouteList implements Iterable<Route>, Listable<Route> {
    private List<Route> list;

    public RouteList() {
        list = new ArrayList<>();
    }

    @Override
    public Route get(int index) throws IndexOutOfBoundsException {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    /**
     * Returns true if the list contains an equivalent Route as the given argument.
     */
    @Override
    public boolean contains(Route toCheck) {
        return list.stream().anyMatch(toCheck::isSameRoute);
    }

    /**
     * Adds a Route to the list.
     * The Task must not already exist in the list.
     */
    @Override
    public void add(Route toAdd) throws DukeException {
        if (contains(toAdd)) {
            throw new DukeDuplicateRouteException();
        }
        list.add(toAdd);
    }

    /**
     * Replaces the Task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The Task identity of {@code editedTask} must not be the same as another existing Task in the list.
     */
    public void setRoute(Route target, Route editedRoute) throws DukeException {
        int index = list.indexOf(target);
        if (index == -1) {
            throw new DukeRouteNotFoundException();
        }

        if (!target.isSameRoute(editedRoute) && contains(editedRoute)) {
            throw new DukeDuplicateRouteException();
        }

        list.set(index, editedRoute);
    }

    /**
     * Removes the equivalent Task from the list.
     * The Task must exist in the list.
     */
    public void remove(Route toRemove) throws DukeException {
        if (!list.remove(toRemove)) {
            throw new DukeRouteNotFoundException();
        }
    }

    public Route remove(int index) throws IndexOutOfBoundsException {
        return list.remove(index);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Replaces the contents of this list with {@code Tasks}.
     * {@code Tasks} must not contain duplicate Tasks.
     */
    public void setRoutes(List<Route> routes) throws DukeException {
        if (!routesAreUnique(routes)) {
            throw new DukeDuplicateRouteException();
        }

        list = routes;
    }

    @Override
    public Iterator<Route> iterator() {
        return list.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RouteList // instanceof handles nulls
                && list.equals(((RouteList) other).list));
    }

    @Override
    public int hashCode() {
        return list.hashCode();
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

    public List<Route> getRoutes() {
        return list;
    }
}

