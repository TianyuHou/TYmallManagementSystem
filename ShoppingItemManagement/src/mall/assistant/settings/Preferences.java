package mall.assistant.settings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import mall.assistant.alertMaker.alertMaker;

public class Preferences {
	
	public static final String CONFIG_FILE = "config.txt";
	
	String username;
	String password;
	
	public Preferences() {
		username = "admin";
		setPassword("admin");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if(password.length() < 8) {
			this.password = DigestUtils.shaHex(password);
		}else {
			this.password = password;
		}
	}
	
	public static void initConfig() {
		Writer writer = null;
		Preferences preferences = new Preferences();
		Gson gson = new Gson();
		try {
			writer = new FileWriter(CONFIG_FILE);
			gson.toJson(preferences,writer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static Preferences getPreferences() {
		Gson gson = new Gson();
		Preferences preferences = new Preferences();
		try {
			preferences = gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			initConfig();
			e.printStackTrace();
		}
		return preferences;
	}
	
	public static void writeToGson(Preferences preferences) {
		Writer writer = null;
		Gson gson = new Gson();
		try {
			writer = new FileWriter(CONFIG_FILE);
			gson.toJson(preferences,writer);
			
			alertMaker.showSimpleAlert("Success", "Settings Updated");
		} catch (IOException e) {
			e.printStackTrace();
			alertMaker.showErrorMessage(e, "Failed", "Can't Save Configuration");
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	
	
}
