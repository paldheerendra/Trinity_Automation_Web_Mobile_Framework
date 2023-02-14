package com.generic.utils;

import java.net.ServerSocket;

public class AvailabelPorts {
	/**
	 * get available port
	 * @author Dheerendra Singh
	 */
	public String getPort() {
		String port = "";
		try {
			ServerSocket socket = new ServerSocket(0);
			socket.setReuseAddress(true);
			port = Integer.toString(socket.getLocalPort());
			socket.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return port;
	}
}
