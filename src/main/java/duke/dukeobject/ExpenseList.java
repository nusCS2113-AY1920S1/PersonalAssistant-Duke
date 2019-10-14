package duke.dukeobject;

import duke.exception.DukeException;
import duke.exception.DukeRuntimeException;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ExpenseList extends DukeList<Expense> {

    private Comparator<Expense> compareCriteria;

    /**
     * Defines the criteria of sort comparision as amount.
     * The {@code Expense} with more amount is regarded as the larger.
     */
    private static class SortByAmount implements Comparator<Expense> {
        public int compare(Expense e1, Expense e2) {
            return e1.getAmount().compareTo(e2.getAmount());
        }
    }

    /**
     * Defines the criteria of sort comparision as Time.
     * The {@code Expense} with later Time is regarded as the larger.
     */
    private static class SortByTime implements Comparator<Expense> {
        public int compare(Expense e1, Expense e2) {
            return e1.getTime().compareTo(e2.getTime());
        }
    }

    /**
     * Defines the criteria of sort comparision as Description in alphabetical order.
     * The {@code Expense} with opening letter 'z' is regarded as the largest.
     */
    private static class SortByDescription implements Comparator<Expense> {
        public int compare(Expense e1, Expense e2) {
            return e1.getDescription().compareTo(e2.getDescription());
        }
    }

    public ExpenseList(File file) throws DukeException {
        super(file, "expense");
    }

    /**
     * Updates {@code externalList}, then returns it.
     *
     * @return {@code externalList}.
     */
    @Override
    public List<Expense> getExternalList() {
        List<Expense> filteredSortedList;
        filteredSortedList = filter(sort(view(internalList)));
        externalList = filteredSortedList;
        return externalList;
    }

    @Override
    public void setSortCriteria(String sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    @Override
    public void setFilterCriteria(String filterCriteria) {
        this.filterCriteria = filterCriteria;
    }

    @Override
    public void setViewScope(String viewScope) {
        this.viewScope = viewScope;
    }

    /**
     * Sorts the given List with the given criteria and returns the sorted List.
     *
     * @param currentList The List going to be sorted.
     * @return The sorted List.
     */
    @Override
    public List<Expense> sort(List<Expense> currentList) {
        switch (sortCriteria) {
        case "time":
            compareCriteria = new SortByTime();
            break;
        case "amount":
            compareCriteria = new SortByAmount();
            break;
        case "description":
            compareCriteria = new SortByDescription();
            break;
        default:
            throw new DukeRuntimeException("Incorrect format of sort criteria");
        }
        List<Expense> sortedList = currentList;
        sortedList.sort(compareCriteria);
        return sortedList;
    }

    /**
     * To be implemented when tags are specified.
     *
     * @param currentList The List going to be filtered.
     * @return The filtered List.
     */
    @Override
    public List<Expense> filter(List<Expense> currentList) {
        return currentList;
    }

    /**
     * Tailors the given List so that only {@code Expense} within the given time scope are preserved.
     * Returns the tailored List.
     *
     * @param currentList The list going to be modified.
     * @return The tailored List.
     */
    @Override
    public List<Expense> view(List<Expense> currentList) {
        List<Expense> viewedList = new ArrayList<Expense>();
        LocalDateTime now = LocalDateTime.now();
        switch (viewScope) {
        case "all":
            viewedList = currentList;
            break;
        case "year":
            for (Expense e : currentList) {
                if (e.getTime().plusYears(1).isAfter(now)) {
                    viewedList.add(e);
                }
            }
            break;
        case "month":
            for (Expense e : currentList) {
                if (e.getTime().plusMonths(1).isAfter(now)) {
                    viewedList.add(e);
                }
            }
            break;
        case "week":
            for (Expense e : currentList) {
                if (e.getTime().plusDays(7).isAfter(now)) {
                    viewedList.add(e);
                }
            }
            break;
        case "day":
            for (Expense e : currentList) {
                if (e.getTime().plusDays(1).isAfter(now)) {
                    viewedList.add(e);
                }
            }
            break;
        default:
            throw new DukeRuntimeException("Incorrect format of view scope");
        }
        return viewedList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expense itemFromStorageString(String storageString) throws DukeException {
        return new Expense.Builder(storageString).build();
    }

    /**
     * Returns the total amount of money spent.
     *
     * @return BigDecimal of the total amount of money spent.
     */
    public BigDecimal getTotalAmount() {
        return internalList.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * returns the total Amount given a specific tag.
     *
     * @param tag the tag of
     * @return A BigDecimal which is the sum of all items of a single tag
     */
    public BigDecimal getTagAmount(String tag) {
        return internalList.stream()
                .filter(expense -> expense.getTags().contains(tag))
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}