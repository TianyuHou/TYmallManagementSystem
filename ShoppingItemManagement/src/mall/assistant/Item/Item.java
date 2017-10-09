package mall.assistant.Item;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {
	
	private final SimpleStringProperty id;
	private final SimpleStringProperty cid;
	private final SimpleStringProperty category;

	private final SimpleStringProperty name;
	private final SimpleDoubleProperty price;
	private final SimpleIntegerProperty inventory;
	
	public Item(String id, String cid, String category, String name, double price, int inventory) {
		this.id = new SimpleStringProperty(id);
		this.category = new SimpleStringProperty(category);
		this.cid = new SimpleStringProperty(cid);
		this.name = new SimpleStringProperty(name);
		this.price = new SimpleDoubleProperty(price);
		this.inventory = new SimpleIntegerProperty(inventory);
	}

	public String getId() {
		return id.get();
	}
	
	public String getCategory() {
		return category.get();
	}

	public String getCid() {
		return cid.get();
	}

	public String getName() {
		return name.get();
	}

	public double getPrice() {
		return price.get();
	}

	public int getInventory() {
		return inventory.get();
	}
	
	
}
