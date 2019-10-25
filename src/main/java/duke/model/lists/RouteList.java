package duke.model.lists;

import duke.commons.exceptions.DukeRouteNotFoundException;
import duke.commons.exceptions.RouteNodeDuplicateException;
import duke.model.transports.Route;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages and handles Route manipulation.
 */
public class RouteList implements Iterable<Route>, Listable<Route> {
    private List<Route> list;

    /**
     * Constructs a RouteList object.
     */
    public RouteList() {
        list = new ArrayList<>();
    }

    @Override
    public Route get(int index) throws IndexOutOfBoundsException {
        return list.get(index);
    }

    public List<Route> getRoutes() {
        return list;
    }

    /**
     * Gets the size of the list.
     *
     * @return Size of list.
     */
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
     *
     * @param toAdd the Route to add.
     * @exception RouteNodeDuplicateException If there is a duplicate route.
     */
    @Override
    public void add(Route toAdd) throws RouteNodeDuplicateException {
        if (contains(toAdd)) {
            throw new RouteNodeDuplicateException();
        }
        list.add(toAdd);
    }

    /**
     * Replaces an existing Route with a new different Route.
     *
     * @param target The existing route.
     * @param editedRoute The new route.
     * @exception RouteNodeDuplicateException If there is a duplicate route.
     * @exception DukeRouteNotFoundException If the route is not found.
     */
    public void setRoute(Route target, Route editedRoute) throws RouteNodeDuplicateException,
            DukeRouteNotFoundException {
        int index = list.indexOf(target);
        if (index == -1) {
            throw new DukeRouteNotFoundException();
        }

        if (!target.isSameRoute(editedRoute) && contains(editedRoute)) {
            throw new RouteNodeDuplicateException();
        }

        list.set(index, editedRoute);
    }

    /**
     * Removes an existing Route from the list.
     *
     * @param toRemove The route to remove.
     * @exception DukeRouteNotFoundException If the route is not found.
     */
    public void remove(Route toRemove) throws DukeRouteNotFoundException {
        if (!list.remove(toRemove)) {
            throw new DukeRouteNotFoundException();
        }
    }

    /**
     * Removes an existing Route from the list and returns it.
     *
     * @param index The index of the Route.
     * @return route The queried Route.
     * @exception IndexOutOfBoundsException If the query is out of bound.
     */
    public Route remove(int index) throws IndexOutOfBoundsException {
        return list.remove(index);
    }

    /**
     * Returns whether the list is empty.
     *
     * @return Whether the list is empty.
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Replaces the contents of this list with a list of Routes.
     *
     * @param routes The list of Routes to replace.
     * @exception RouteNodeDuplicateException If there is a duplicate route.
     */
    public void setRoutes(List<Route> routes) throws RouteNodeDuplicateException {
        if (!isUniqueRoutes(routes)) {
            throw new RouteNodeDuplicateException();
        }

        list = routes;
    }

    /**
     * Returns an iterator to the list.
     *
     * @return The iterator to the list.
     */
    @Override
    public Iterator<Route> iterator() {
        return list.iterator();
    }

    /**
     * Checks if an object is equal to this.
     *
     * @param other The other object.
     * @return Whether this object is equal to the other object.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RouteList // instanceof handles nulls
                && list.equals(((RouteList) other).list));
    }

    /**
     * Returns the hash code of the list.
     *
     * @return The hash code of the list.
     */
    @Override
    public int hashCode() {
        return list.hashCode();
    }

    /**
     * Returns true if all Routes in list are unique.
     *
     * @param routes The routes to check.
     * @return Whether the routes are unique.
     */
    private boolean isUniqueRoutes(List<Route> routes) {
        for (int i = 0; i < routes.size() - 1; i++) {
            for (int j = i + 1; j < routes.size(); j++) {
                if (routes.get(i).isSameRoute(routes.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

