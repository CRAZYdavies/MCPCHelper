package com.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

	
	
	private ServerSocket socket;
	
	public Server(){
		this.initSocket(0);
	}

	public Server(int port){
		this.initSocket(port);
	}
	
	private void initSocket(int port) {
		try {
			this.setSocket(new ServerSocket(port));
		} catch (IOException e) {
			System.out.println("The server could not bind with port: "+port);
			e.printStackTrace();
		}
	}

	/**
	 * @return the socket
	 */
	public ServerSocket getSocket() {
		return socket;
	}

	/**
	 * @param socket the socket to set
	 */
	private void setSocket(ServerSocket socket) {
		this.socket = socket;
	}
}
