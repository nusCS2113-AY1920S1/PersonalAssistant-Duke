package cube.model.sale;

import java.util.TreeSet;
import java.util.Iterator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SalesHistory {
	@JsonProperty
	private TreeSet<Sale> salesSet;

	public SalesHistory() {
		this.salesSet = new TreeSet<Sale>();
	}

	public SalesHistory(TreeSet<Sale> salesSet) {
		this.salesSet = salesSet;
	}

	public void add(Sale saleRecord) {
		salesSet.add(saleRecord);
	}

	public int size() {
		return salesSet.size();
	}

	public Iterator iterator() {
		return salesSet.iterator();
	}

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