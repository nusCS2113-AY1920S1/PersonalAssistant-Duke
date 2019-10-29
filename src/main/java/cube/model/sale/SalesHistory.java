package cube.model.sale;

import java.util.TreeSet;
import java.util.Iterator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SalesHistory {
	@JsonProperty
	private TreeSet<Sale> salesSet;

	public SalesHistory() {
		this.salesSet = new TreeSet<Sale>(new SaleComparator());
	}

	public SalesHistory(TreeSet<Sale> salesSet) {
		this.salesSet = salesSet;
	}

	public void add(Sale saleRecord) {
		salesSet.add(saleRecord);
	}

	public int size() {
		/* debug use
		Iterator i = salesSet.iterator();
		while(i.hasNext()) {
			System.out.println(i.next());
		}
		*/
		return salesSet.size();
	}

}