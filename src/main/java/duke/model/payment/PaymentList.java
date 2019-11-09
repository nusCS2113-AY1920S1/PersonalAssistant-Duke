package duke.model.payment;

import duke.commons.LogsCenter;
import duke.exception.DukeException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class PaymentList {

    private static final Logger logger = LogsCenter.getLogger(PaymentList.class);

    private static final SortingCriteria DEFAULT_SORT_CRITERIA = SortingCriteria.TIME;

    private List<Payment> internalTimeSortedList;

    private List<Payment> internalAmountSortedList;

    private List<Payment> internalPrioritySortedList;

    private ObservableList<Payment> unfilteredList;

    private SortingCriteria sortingCriteria;

    private FilteredList<Payment> filteredList;

    private StringProperty sortingCriteriaIndicator = new SimpleStringProperty();

    private ObjectProperty<Predicate> predicateIndicator = new SimpleObjectProperty<Predicate>();

    private Predicate<Payment> PREDICATE_SHOW_ALL_PAYMENTS = unused -> true;

    private enum SortingCriteria {
        TIME("time"),
        AMOUNT("amount"),
        PRIORITY("priority");

        private String literalMeaning;

        public String toString() {
            return literalMeaning;
        }

        SortingCriteria(String literalMeaning) {
            this.literalMeaning = literalMeaning;
        }
    }

    public PaymentList() {
        logger.info("1.0");
        this.internalTimeSortedList = new ArrayList<>();

        unfilteredList = FXCollections.observableArrayList();

        updateInternalAmountSortedList();
        updateInternalPrioritySortedList();

        sortingCriteria = DEFAULT_SORT_CRITERIA; // TIME
        fetchInternalListToUnfilteredList();

        filteredList = new FilteredList<>(unfilteredList);
        filteredList.setPredicate(PREDICATE_SHOW_ALL_PAYMENTS);
        sortingCriteriaIndicator.setValue(sortingCriteria.toString());
        predicateIndicator.setValue(PREDICATE_SHOW_ALL_PAYMENTS);
    }

    public PaymentList(List<Payment> timeSortedList) {
        this.internalTimeSortedList = timeSortedList;
        unfilteredList = FXCollections.observableArrayList();
        updateInternalAmountSortedList();
        updateInternalPrioritySortedList();
        sortingCriteria = DEFAULT_SORT_CRITERIA; // TIME
        fetchInternalListToUnfilteredList();
        filteredList = new FilteredList<>(unfilteredList);
        filteredList.setPredicate(PREDICATE_SHOW_ALL_PAYMENTS);
        sortingCriteriaIndicator.setValue(sortingCriteria.toString());
        predicateIndicator.setValue(PREDICATE_SHOW_ALL_PAYMENTS);
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
            target = filteredList.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_NO_ITEM_AT_INDEX, index));
        }
        return target;
    }

    public void setPayment(int index, Payment editedPayment) throws DukeException {
        remove(index);
        add(editedPayment); // using add method can help sort after change of element.
    }

    public void setSortingCriteria(String sortingCriteria) throws DukeException {
        try {
            this.sortingCriteria = SortingCriteria.valueOf(sortingCriteria.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_SORT_CRITERIA_INVALID, sortingCriteria));
        }
        fetchInternalListToUnfilteredList();
        sortingCriteriaIndicator.setValue(this.sortingCriteria.toString());
    }

    public void setTimePredicate(Predicate<Payment> predicate) {
        filteredList.setPredicate(predicate);
        predicateIndicator.setValue(predicate);
    }

    public void setSearchPredicate(String keyword) {
        SearchKeywordPredicate searchPredicate = new SearchKeywordPredicate(keyword);
        filteredList.setPredicate(searchPredicate);
        predicateIndicator.set(searchPredicate);
    }


    public FilteredList<Payment> getFilteredList() {
        return filteredList;
    }

    public StringProperty getSortingCriteriaIndicator() {
        return sortingCriteriaIndicator;
    }

    public ObjectProperty<Predicate> getPredicateIndicator() {
        return predicateIndicator;
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
        switch (sortingCriteria) {
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
