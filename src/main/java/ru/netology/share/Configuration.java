package ru.netology.share;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;

public class Configuration {

	private String ipAddr;
	private int port;

	private static String configFile = "conf/settings.json";
	
	public static List<Configuration> getConfig() {
		String json = "";
		try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
			String s;
			while ((s = br.readLine()) != null) {
				json = json + s;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Configuration> listConfiguration = new ArrayList<>();

		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();

		try {
			JsonElement element = JsonParser.parseString(json);
			JsonArray jsonArray = element.getAsJsonArray();
			for (JsonElement e : jsonArray) {
				Configuration employee = gson.fromJson(e, Configuration.class);
				listConfiguration.add(employee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listConfiguration;
	}

	public Configuration() {}

	public Configuration(String ipAddr, int port) {
		this.ipAddr = ipAddr;
		this.port = port;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public int getPort() {
		return port;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "ipAddr: " + ipAddr + ", " + "port: " + port;
	}
}

