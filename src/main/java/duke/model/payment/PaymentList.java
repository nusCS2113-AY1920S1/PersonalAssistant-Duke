package duke.model.payment;

import duke.exception.DukeException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class PaymentList {

    private static final SortCriteria DEFAULT_SORT_CRITERIA = SortCriteria.TIME;

    private List<Payment> internalTimeSortedList;

    private List<Payment> internalAmountSortedList;

    private List<Payment> internalPrioritySortedList;

    private ObservableList<Payment> unfilteredList;

    private SortCriteria sortCriteria;

    private FilteredList<Payment> filteredList;

    private FilteredList<Payment> searchResult;

    private DisplayMode displayMode;

    Predicate<Payment> PREDICATE_SHOW_ALL_PAYMENTS = unused -> true;

    private enum SortCriteria {
        TIME, AMOUNT, PRIORITY;
    }

    private enum DisplayMode {
        FILTERED_LIST, SEARCH_RESULT;
    }

    public PaymentList() {
        this.internalTimeSortedList = new ArrayList<>();

        unfilteredList = FXCollections.observableArrayList();

        updateInternalAmountSortedList();
        updateInternalPrioritySortedList();

        sortCriteria = DEFAULT_SORT_CRITERIA; // TIME
        fetchInternalListToUnfilteredList();

        filteredList = new FilteredList<>(unfilteredList);
        filteredList.setPredicate(PREDICATE_SHOW_ALL_PAYMENTS);
        searchResult = new FilteredList<>(unfilteredList);
    }

    public PaymentList(List<Payment> timeSortedList) {
        this.internalTimeSortedList = timeSortedList;

        unfilteredList = FXCollections.observableArrayList();

        updateInternalAmountSortedList();
        updateInternalPrioritySortedList();

        sortCriteria = DEFAULT_SORT_CRITERIA; // TIME
        fetchInternalListToUnfilteredList();

        filteredList = new FilteredList<>(unfilteredList);
        filteredList.setPredicate(PREDICATE_SHOW_ALL_PAYMENTS);
        searchResult = new FilteredList<>(unfilteredList);
        displayMode = DisplayMode.FILTERED_LIST;
    }

    public void add(Payment payment) {
        internalTimeSortedList.add(payment);
        internalTimeSortedList.sort(Comparator.comparing(Payment::getDue));

        updateInternalAmountSortedList(); // add operation may break the sorted order.
        updateInternalPrioritySortedList();

        fetchInternalListToUnfilteredList();
    }

    public void remove(int index) throws DukeException {
        Payment target = getPayment(index);

        internalTimeSortedList.remove(target);
        internalAmountSortedList.remove(target);
        internalPrioritySortedList.remove(target); // remove operation doesn't break the sorted order.

        fetchInternalListToUnfilteredList();
    }

    public Payment getPayment(int index) throws DukeException {
        Payment target;
        try {
            if (displayMode == DisplayMode.FILTERED_LIST) {
                target = filteredList.get(index - 1);
            } else {
                target = searchResult.get(index - 1);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_NO_ITEM_AT_INDEX, index));
        }
        return target;
    }

    public void setPayment(int index, Payment editedPayment) throws DukeException {
        remove(index);
        add(editedPayment); // using add method can help sort after change of element.
    }

    public void setSortCriteria(String sortCriteria) throws DukeException {
        try {
            this.sortCriteria = SortCriteria.valueOf(sortCriteria.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_SORT_CRITERIA_INVALID, sortCriteria));
        }
        fetchInternalListToUnfilteredList();
    }

    public void setPredicate(Predicate<Payment> predicate) {
        displayMode = DisplayMode.FILTERED_LIST;
        filteredList.setPredicate(predicate);
    }

    public void setSearchPredicate(Predicate<Payment> searchPredicate) {
        displayMode = DisplayMode.SEARCH_RESULT;
        searchResult.setPredicate(searchPredicate);
    }


    public FilteredList<Payment> getFilteredList() {
        return filteredList;
    }

    public FilteredList<Payment> getSearchResult() {
        return searchResult;
    }

    /**
     * Returns all internal payments as an ArrayList.
     * This is for storage ONLY!
     *
     * @return list containing all internal payments.
     */
    public List<Payment> getInternalList() {
        return internalTimeSortedList;
    }

    private void fetchInternalListToUnfilteredList() {
        switch (sortCriteria) {
            case TIME:
                unfilteredList.setAll(internalTimeSortedList);
                break;

            case AMOUNT:
                unfilteredList.setAll(internalAmountSortedList);
                break;

            case PRIORITY:
                unfilteredList.setAll(internalPrioritySortedList);
                break;
        }
    }

    private void updateInternalAmountSortedList() {
        internalAmountSortedList = new ArrayList<>(internalTimeSortedList);
        internalAmountSortedList.sort(Comparator.comparing(Payment::getAmount));
        Collections.reverse(internalAmountSortedList);
    }

    private void updateInternalPrioritySortedList() {
        internalPrioritySortedList = new ArrayList<>(internalTimeSortedList);
        internalPrioritySortedList.sort(Comparator.comparing(Payment::getNumeratedPriority));
        Collections.reverse(internalPrioritySortedList);
    }
}
