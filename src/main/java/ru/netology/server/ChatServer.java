package ru.netology.server;

import ru.netology.share.Connection;
import ru.netology.share.Listener;
import ru.netology.share.Logger;
import ru.netology.share.Configuration;
import java.util.*;
import java.io.*;
import java.net.*;

public class ChatServer implements Listener {

	private List<Configuration> configList = Configuration.getConfig();
	private int port = configList.get(0).getPort();

	private static final String LOGDIR = "log";
	private static final String LOGFILE = "server.log";
	private final ArrayList<Connection> connections = new ArrayList<>();
	private Logger logger = Logger.getInstance();


	public ChatServer() {
		logger.setLogFile(LOGDIR, LOGFILE);
		System.out.println("Server running...");
		try (ServerSocket server = new ServerSocket(port)) {
			while (true) {
				try {
					new Connection(this, server.accept());
				} catch (IOException e) {
					System.out.println("Connection Exception: " + e);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public synchronized void onConnectionReady(Connection connection) {
		connections.add(connection);
		sendToAllConnections("Client connected: " + connection);
	}

	@Override
	public synchronized void onReceiveString(Connection connection, String message) {
		sendToAllConnections(message);
	}

	@Override
	public synchronized void onDisconnect(Connection connection) {
		connections.remove(connection);
		sendToAllConnections("Client disconnected: " + connection);
	}

	@Override
	public synchronized void onException(Connection connection, Exception e) {
		System.out.println("Connection exception: " + e);
	}

	private void sendToAllConnections(String message) {
		System.out.println(message);
		logger.log(message);
		final int cnt = connections.size();
		for (int i = 0; i < cnt; i++) {
			connections.get(i).sendString(message);
		}
	}
}
