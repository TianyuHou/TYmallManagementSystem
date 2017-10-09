package mall.assistant.staff;

import javafx.beans.property.SimpleStringProperty;

public class Staff {
	private final SimpleStringProperty id;
	private final SimpleStringProperty name;
	private final SimpleStringProperty department;

	private final SimpleStringProperty position;
	private final SimpleStringProperty mobile;
	private final SimpleStringProperty email;
	
	public Staff(String id, String department, String name,
			String position, String mobile, String email) {
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.department = new SimpleStringProperty(department);
		this.position = new SimpleStringProperty(position);
		this.mobile = new SimpleStringProperty(mobile);
		this.email = new SimpleStringProperty(email);
	}

	public String getId() {
		return id.get();
	}

	public String getName() {
		return name.get();
	}

	public String getDepartment() {
		return department.get();
	}

	public String getPosition() {
		return position.get();
	}

	public String getMobile() {
		return mobile.get();
	}

	public String getEmail() {
		return email.get();
	}
}
