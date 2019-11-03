/**
 * The sales history.
 * Uses ordered set as the data structure for kepping sales history.
 *
 * @author tygq13
 */
package cube.model.sale;

import java.util.TreeSet;
import java.util.Iterator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SalesHistory {
	@JsonProperty
	private TreeSet<Sale> salesSet;

	/**
	 * Default constructor for SalesHistory.
	 * Initialize a new sales history ordered set.
	 */
	public SalesHistory() {
		this.salesSet = new TreeSet<Sale>();
	}

	/**
	 * Constructor with one argument.
	 * Utilize existing sales history set.
	 * @param salesSet
	 */
	public SalesHistory(TreeSet<Sale> salesSet) {
		this.salesSet = salesSet;
	}

	/**
	 * Adds a sale record to sales history.
	 * @param saleRecord The sale record to be added.
	 */
	public void add(Sale saleRecord) {
		salesSet.add(saleRecord);
	}

	/**
	 * Gets the size of sales history set.
	 * @return The size of sales history set.
	 */
	public int size() {
		return salesSet.size();
	}

	/**
	 * Gets the iterator for sales history set.
	 * @return The iterator for sales history set.
	 */
	public Iterator iterator() {
		return salesSet.iterator();
	}

	/**
	 * Converts the sales history to String.
	 * @return The formatted string of sales history.
	 */
	@Override
	// more for debug use for now
	public String toString() {
		String result = "";
		Iterator i = salesSet.iterator();
		while(i.hasNext()) {
			result += i.next() + "\n";
		}
		return result;
	}

}