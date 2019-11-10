package duke.model.payment;

import duke.commons.LogsCenter;
import duke.exception.DukeException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * A list of Payments that does not allow nulls.
 * Supports a set of basic list operations such as adding, removing and editing and
 * supports sorting, changing time scope and searching by keywords operations.
 *
 * Payments can be sorted according to their amounts, due or priorities,
 * where payments with higher amounts, priorities and closer due will be placed at prior.
 *
 * Time scope of payments can be altered such that it can choose to only shows payments
 * overdue, within a week, within a month or in all time.
 *
 * Payments can be searched by keyword. Those containing keyword in their
 * description, receiver, or tag will be found out.
 */
public class PaymentList {

    private static final Logger logger = LogsCenter.getLogger(PaymentList.class);

    private static final String ITEM_NAME = "payment";

    private static final SortingCriteria DEFAULT_SORTING_CRITERIA = SortingCriteria.TIME;

    public static final Predicate<Payment> PREDICATE_SHOW_ALL_PAYMENTS = unused -> true;

    /**
     * The list containing all the payments.
     */
    private ObservableList<Payment> internalList;

    /**
     * The filtered list containing sorted and filtered payments.
     */
    private FilteredList<Payment> filteredList;

    /**
     * The external list containing sorted and filtered payments.
     */
    private ObservableList<Payment> unmodifiableFilteredList;

    /**
     * Sorting criteria used to sort payments.
     */
    private SortingCriteria sortingCriteria;

    /**
     * Helps auto fetch sorting criteria to Ui.
     */
    private ObjectProperty<SortingCriteria> sortingCriteriaIndicator = new SimpleObjectProperty<SortingCriteria>();

    /**
     * Helps auto fetch predicate to Ui.
     */
    private ObjectProperty<Predicate> predicateIndicator = new SimpleObjectProperty<Predicate>();

    /**
     * Sorting criteria of payments.
     */
    public enum SortingCriteria {
        TIME,
        AMOUNT,
        PRIORITY;
    }

    /**
     * Constructs a PaymentList with a list of payments.
     *
     * Initializes the list with default time predicate "all"
     * and default sorting criteria TIME.
     *
     * @param payments an empty list or a list of payments from storage
     */
    public PaymentList(List<Payment> payments) {
        requireNonNull(payments);

        // Fills the internal list
        this.internalList = FXCollections.observableList(payments);
        sortingCriteria = DEFAULT_SORTING_CRITERIA; // TIME
        sortInternalList();

        // Fills the filtered list
        filteredList = new FilteredList<Payment>(internalList);
        filteredList.setPredicate(PREDICATE_SHOW_ALL_PAYMENTS);

        // Fills the external unmodifiable list
        unmodifiableFilteredList = FXCollections.unmodifiableObservableList(filteredList);

        // Sets the fetcher of Ui
        predicateIndicator.setValue(PREDICATE_SHOW_ALL_PAYMENTS);
        sortingCriteriaIndicator.setValue(sortingCriteria);
    }

    /**
     * Initialize an empty PaymentList.
     */
    public PaymentList() {
        this(new ArrayList<>());
    }

    /**
     * Adds a payment to the list.
     * The list will then be sorted.
     */
    public void add(Payment payment) {
        requireNonNull(payment);

        internalList.add(payment);
        sortInternalList();
    }

    /**
     * Removes the payment at {@code index} from the list.
     * The payment must exist in the list.
     * The list will then be sorted.
     */
    public void remove(int index) throws DukeException {
        Payment target = getPayment(index);
        internalList.remove(target);
        sortInternalList();
    }

    /**
     * Gets the payment at the {@code} index.
     * The payment must exist in the list.
     *
     * @param index the index of the target payment
     * @return the target payment
     * @throws DukeException if the index is out of scope
     */
    public Payment getPayment(int index) throws DukeException {
        Payment target;
        try {
            target = filteredList.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_NO_ITEM_AT_INDEX, ITEM_NAME, index));
        }
        return target;
    }

    /**
     * Replaces the payment at {@code index} in the list with {@code editedPayment}.
     * The {@code index} must be a valid index in scope.
     * The list will then be sorted.
     */
    public void setPayment(int index, Payment editedPayment) throws DukeException {
        requireNonNull(editedPayment);

        remove(index);
        add(editedPayment);
    }

    /**
     * Sets the sorting criteria of the internal list.
     * The {@code sortingCriteria} must literally corresponds to an element of enum ignoring the case.
     * Updates the {@code sortingCriteriaIndicator}.
     *
     * @param sortingCriteria the string of sorting criteria to be set
     * @throws DukeException if no element in enum has the same name (case ignored) as {@code sortingCriteria}
     */
    public void setSortingCriteria(String sortingCriteria) throws DukeException {
        requireNonNull(sortingCriteria);

        try {
            this.sortingCriteria = SortingCriteria.valueOf(sortingCriteria.toUpperCase());
            sortInternalList();
        } catch (IllegalArgumentException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_SORT_CRITERIA_INVALID, sortingCriteria));
        }

        // Updates the fetcher of Ui
        sortingCriteriaIndicator.setValue(this.sortingCriteria);
    }

    /**
     * Sets the time predicate of the filtered list.
     * Updates the {@code predicateIndicator}.
     *
     * @param predicate the time predicate to be set
     */
    public void setTimePredicate(Predicate<Payment> predicate) {
        requireNonNull(predicate);

        assert (predicate instanceof PaymentOverduePredicate)
                || (predicate instanceof PaymentInWeekPredicate)
                || (predicate instanceof PaymentInMonthPredicate)
                || (predicate.equals(PREDICATE_SHOW_ALL_PAYMENTS));

        filteredList.setPredicate(predicate);

        // Updates the fetcher of Ui
        predicateIndicator.setValue(predicate);
    }

    /**
     * Sets the search predicate to the filtered list by specifying the {@code keyword}.
     *
     * @param keyword the keyword to search
     */
    public void setSearchPredicate(String keyword) {
        requireNonNull(keyword);

        SearchKeywordPredicate searchPredicate = new SearchKeywordPredicate(keyword);
        filteredList.setPredicate(searchPredicate);
        predicateIndicator.set(searchPredicate);
    }

    /**
     * Returns the filtered list as an unmodifiable {@code ObservableList}.
     *
     * @return the unmodifiable filtered list
     */
    public ObservableList<Payment> asUnmodifiableFilteredList() {
        return unmodifiableFilteredList;
    }

    /**
     * Returns the indicator of sorting criteria.
     * It helps Ui auto fetch the sorting criteria used in {@code PaymentList}.
     *
     * @return an {@code ObjectProperty} of the {@code sortingCriteria}.
     */
    public ObjectProperty<SortingCriteria> getSortingCriteriaIndicator() {
        return sortingCriteriaIndicator;
    }

    /**
     * Returns the indicator of predicate, which can be time or search predicate.
     * It helps Ui auto fetch the predicate used in {@code PaymentList}.
     *
     * @return an {@code ObjectProperty} of {@code Predicate}.
     */
    public ObjectProperty<Predicate> getPredicateIndicator() {
        return predicateIndicator;
    }
    
    /**
     * Returns all internal payments as a list.
     * This is for storage ONLY!
     *
     * @return a list containing all internal payments.
     */
    public List<Payment> getInternalList() {
        return internalList;
    }

    /**
     * Sorts the internal list with the current {@code sortingCriteria}.
     */
    private void sortInternalList() {
        switch (sortingCriteria) {
        case TIME:
            internalList.sort(Comparator.comparing(Payment::getDue));
            break;

        case AMOUNT:
            internalList.sort(Comparator.comparing(Payment::getAmount));
            FXCollections.reverse(internalList); // payments with higher amounts will be prior.
            break;

        case PRIORITY:
            internalList.sort(Comparator.comparing(Payment::getNumeratedPriority));
            FXCollections.reverse(internalList); // payments with higher priority will be prior.
            break;

        default:
            logger.warning("Unknown errors occur on sortingCriteria");
            break;
        }
    }
}
