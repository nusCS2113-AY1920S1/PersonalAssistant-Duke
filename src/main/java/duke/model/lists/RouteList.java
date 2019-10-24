package duke.model.lists;

import duke.commons.exceptions.DukeDuplicateRouteException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.DukeRouteNotFoundException;
import duke.model.transports.Route;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages and handles Route manipulation.
 */
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
     * Adds a unique Route to the list.
     */
    @Override
    public void add(Route toAdd) throws DukeException {
        if (contains(toAdd)) {
            throw new DukeDuplicateRouteException();
        }
        list.add(toAdd);
    }

    /**
     * Replaces an existing Route with a new different Route.
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
     * Removes an existing Route from the list.
     */
    public void remove(Route toRemove) throws DukeException {
        if (!list.remove(toRemove)) {
            throw new DukeRouteNotFoundException();
        }
    }

    /**
     * Removes an existing Route from the list and returns it.
     * @param index The index of the Route.
     * @return route The queried Route.
     */
    public Route remove(int index) throws IndexOutOfBoundsException {
        return list.remove(index);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Replaces the contents of this list with a list of Routes.
     * @param routes The list of Routes to replace.
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
     * Returns true if all Routes in list are unique.
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

