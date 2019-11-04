package duke.model.lists;

import duke.commons.exceptions.RouteNotFoundException;
import duke.commons.exceptions.DuplicateRouteException;
import duke.model.transports.Route;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a list of Routes and contains its related accessor methods..
 */
public class RouteList implements Iterable<Route>, Listable<Route> {
    private List<Route> list;

    /**
     * Constructs a RouteList object.
     */
    public RouteList() {
        list = new ArrayList<>();
    }

    /**
     * Adds a unique Route to the list.
     *
     * @param toAdd the Route to add.
     * @exception DuplicateRouteException If there is a duplicate route.
     */
    @Override
    public void add(Route toAdd) throws DuplicateRouteException {
        if (contains(toAdd)) {
            throw new DuplicateRouteException();
        }
        list.add(toAdd);
    }

    /**
     * Gets the Route at a given index.
     *
     * @param index The index to search for.
     * @return The Route at the index.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    @Override
    public Route get(int index) throws IndexOutOfBoundsException {
        return list.get(index);
    }

    /**
     * Removes an existing Route from the list.
     *
     * @param toRemove The route to remove.
     * @exception RouteNotFoundException If the route is not found.
     */
    public void remove(Route toRemove) throws RouteNotFoundException {
        if (!list.remove(toRemove)) {
            throw new RouteNotFoundException();
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

    public List<Route> getRoutes() {
        return list;
    }

    /**
     * Replaces an existing Route with a new different Route.
     *
     * @param target The existing route.
     * @param editedRoute The new route.
     * @exception DuplicateRouteException If there is a duplicate route.
     * @exception RouteNotFoundException If the route is not found.
     */
    public void setRoute(Route target, Route editedRoute) throws DuplicateRouteException,
            RouteNotFoundException {
        int index = list.indexOf(target);
        if (index == -1) {
            throw new RouteNotFoundException();
        }

        if (target.isSameRoute(editedRoute) || contains(editedRoute)) {
            throw new DuplicateRouteException();
        }

        list.set(index, editedRoute);
    }

    /**
     * Replaces the contents of this list with a list of Routes.
     *
     * @param routes The list of Routes to replace.
     * @exception DuplicateRouteException If there is a duplicate route.
     */
    public void setRoutes(List<Route> routes) throws DuplicateRouteException {
        if (!isUniqueRoutes(routes)) {
            throw new DuplicateRouteException();
        }

        list = routes;
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
     * Returns if a given Route is in the RouteList.
     *
     * @param route The given Route to check.
     * @return true If the Route is already inside.
     */
    @Override
    public boolean contains(Route route) {
        return list.stream().anyMatch(route::isSameRoute);
    }

    /**
     * Returns whether the list is empty.
     *
     * @return true If the list is empty.
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Checks if an object is equal to this.
     *
     * @param other The other object.
     * @return true If this object is equal to the other object.
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
     * @return true If the routes are unique.
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

    /**
     * Returns an iterator to the list of Routes.
     *
     * @return The iterator to the list of Routes.
     */
    @Override
    public Iterator<Route> iterator() {
        return list.iterator();
    }
}

