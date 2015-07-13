package com.Server;

import java.io.IOException;
import java.net.Socket;

import com.Config.Configuration;

public class ServerThread implements Runnable {

	@Override
	public void run() {
		Server theServer = new Server(Configuration.getSERVER_PORT_INT());
		while(true){
			try {
				Socket clientSocket = theServer.getSocket().accept();
				new ServerClientThread(clientSocket).run();
			} catch (IOException e) {
				System.out.println("Server Thread IOException: "+e.getMessage());
				break;
			}
		}

	}

}
