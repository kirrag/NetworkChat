package ru.netology.client;

import java.io.IOException;

import ru.netology.share.Connection;
import ru.netology.share.Listener;
import ru.netology.share.Logger;
import ru.netology.share.Configuration;
import java.util.List;
import java.util.Scanner;


public class ChatClient implements Listener {

	private static final String LOGDIR = "log";
	private static final String LOGFILE = "_client.log";

	private static String nickName = "Имя пользователя";

	private List<Configuration> configList = Configuration.getConfig();
	private final String IP_ADDR = configList.get(1).getIpAddr();
	private final int PORT = configList.get(1).getPort();

	public static void main(String[] args) {
		new ChatClient();
	}

	private Connection connection;
	Logger logger = Logger.getInstance();

	private ChatClient() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Введите свое имя: ");
		nickName = scanner.nextLine();
		logger.setLogFile(LOGDIR, nickName + LOGFILE);

		try {
			connection = new Connection(this, IP_ADDR, PORT);
		} catch (IOException e) {
			printMsg("Connection excepton: " + e);
		}

		while(true) {
			String msg = scanner.nextLine();
			if (msg.equals("/exit")) {
				connection.disconnect();
				System.exit(0);
			}
			connection.sendString(nickName + ": " + msg);
		}
	}

	@Override
	public void onConnectionReady(Connection connection) {
		printMsg("Connection ready...");
	}

	@Override
	public void onReceiveString(Connection  connection, String value ) {
		printMsg(value);
	}

	@Override
	public void onDisconnect(Connection connection) {
		printMsg("Connection closed");
	}

	@Override
	public void onException(Connection connection, Exception e) {
		printMsg("Connection exception: " + e);
	}

	//private synchronized void printMsg(String msg) {
	private void printMsg(String msg) {
		System.out.println(msg);
		logger.log(msg);
	}
}
