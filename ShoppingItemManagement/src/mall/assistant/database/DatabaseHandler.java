package mall.assistant.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public final class DatabaseHandler {
	
	/*public static void main(String[] args) {
		DatabaseHandler d = new DatabaseHandler();
		d.queryTable();
		d.dropTable();
	}*/
	private static DatabaseHandler handler = null;

	private static final String DB_URL = "jdbc:derby:database;create=true";
	private static Connection conn = null;
	private static Statement stmt = null;

	private DatabaseHandler() {
		createConnection();
		setupCategoryTable();
		setupStaffTable();
		setupItemTable();
		
	}

	public static DatabaseHandler getInstance() {
		if (handler == null) {
			handler = new DatabaseHandler();
		}
		return handler;
	}

	void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			conn = DriverManager.getConnection(DB_URL);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	void dropTable() {
		String sql3 = "DROP TABLE CATEGORY";
		String sql1 = "DROP TABLE ITEM";
		String sql2 = "DROP TABLE STAFF";
		try {
			stmt = conn.createStatement();
			stmt.execute(sql1);
			stmt.execute(sql2);
			stmt.execute(sql3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	void queryTable() {
		ResultSet rs;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT category_name FROM CATEGORY");
			while(rs.next()) {
				String name = rs.getString("category_name");
				System.out.println(name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void setupItemTable() {
		String TABLE_NAME = "ITEM";
		try {
			stmt = conn.createStatement();

			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

			if (tables.next()) {
				System.out.println("Table " + TABLE_NAME + "already exists. Ready for go!");
			} else {
				stmt.execute("CREATE TABLE " + TABLE_NAME +"("
						+ "	item_id varchar(200) primary key,\n"
						+ "	cid varchar(200),\n" 
						+ "	item_name varchar(200),\n" 
						+ "	price double,\n"
						+ "	inventory int,\n" 
						+ "FOREIGN KEY (cid) REFERENCES CATEGORY(category_id)"+" )");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage() + " --- setupDatabase");
		} finally {
		}
	}

	void setupStaffTable() {
		String TABLE_NAME = "STAFF";
		try {
			stmt = conn.createStatement();

			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

			if (tables.next()) {
				System.out.println("Table " + TABLE_NAME + "already exists. Ready for go!");
			} else {
				stmt.execute("CREATE TABLE " + TABLE_NAME +"("
						+ " staff_id varchar(200) primary key,\n"
						+ "	department varchar(200),\n" 
						+ "	staff_name varchar(200),\n" 
						+" position varchar(20),\n"
						+ " mobile varchar(20),\n" 
						+ "	email varchar(100)" + " )");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage() + " --- setupDatabase");
		} finally {
		}
	}

	void setupCategoryTable() {
		String TABLE_NAME = "CATEGORY";
		try {

			stmt = conn.createStatement();
			DatabaseMetaData dbm = conn.getMetaData();

			ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
			if (tables.next()) {
				System.out.println("Table " + TABLE_NAME + "already exists. Ready for go!");
			} else {
				stmt.execute("CREATE TABLE " + TABLE_NAME + "(" 
						+ " category_id varchar(200) primary key,\n"
						+ "	category_name varchar(200)" + " )");
				stmt.execute("INSERT INTO CATEGORY VALUES ('001','Media')");
				stmt.execute("INSERT INTO CATEGORY VALUES ('002','Electronic')");
				stmt.execute("INSERT INTO CATEGORY VALUES ('003','Vegetable')");
				stmt.execute("INSERT INTO CATEGORY VALUES ('004','Fruit')");
				stmt.execute("INSERT INTO CATEGORY VALUES ('005','Meat')");
				stmt.execute("INSERT INTO CATEGORY VALUES ('006','Clothes')");
				stmt.execute("INSERT INTO CATEGORY VALUES ('007','Toy')");
				stmt.execute("INSERT INTO CATEGORY VALUES ('008','Stationery')");
				
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage() + " --- setupDatabase");
		} finally {
		}
	}
	
	public ResultSet execQuery(String query) {
		ResultSet result;
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
		} catch (SQLException ex) {
			System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
			return null;
		} finally {
		}
		return result;
	}

	public boolean execAction(String qu) {
		try {
			stmt = conn.createStatement();
			stmt.execute(qu);
			return true;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
			return false;
		} finally {
		}
	}
}
