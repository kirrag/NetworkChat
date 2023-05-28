package ru.netology.share;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.lang.Thread;
import java.lang.Runnable;

public class Connection {

	private final Socket socket;
	private final Thread thread;
	private final Listener listener;
	private final BufferedReader in;
	private final BufferedWriter out;

	public Connection(Listener listener, String ipAddr, int port) throws IOException {
		this(listener, new Socket(ipAddr, port));
	}

	public Connection (Listener listener, Socket socket) throws IOException {
		this.listener = listener;
		this.socket = socket;

		in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
		out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));

		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					listener.onConnectionReady(Connection.this);
					while (!thread.isInterrupted()) {
						listener.onReceiveString(Connection.this, in.readLine());
					}
				} catch (IOException e) {
					listener.onException(Connection.this, e);
				} finally {
					listener.onDisconnect(Connection.this);
				}
			}
		});

		thread.start();
	}

	public synchronized void sendString(String message) {
 		try {
			out.write(message);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			listener.onException(Connection.this, e);
			disconnect();
		}

	}

	public synchronized void disconnect() {
		thread.interrupt();
		try {
			socket.close();
		} catch (IOException e) {
			listener.onException(Connection.this, e);
		}
	}

	@Override
	public String toString() {
		return "Connection: " + socket.getInetAddress() + ": " + socket.getPort();
	}
}
