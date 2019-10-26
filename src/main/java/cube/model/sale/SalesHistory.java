package cube.model.sale;

import java.util.TreeSet;
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

}