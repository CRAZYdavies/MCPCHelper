package com.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerClientThread implements Runnable {

	private Socket theSocket;
	public ServerClientThread(Socket socket) {
		this.setTheSocket(socket);
	}
	@Override
	public void run() {
		ServerAPI sapi = new ServerAPI();
		try (PrintWriter out = new PrintWriter(theSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));) {
			String inputLine, outputLine;
			outputLine = sapi.processInput(null);
			out.println(outputLine);

			while ((inputLine = in.readLine()) != null) {
				outputLine = sapi.processInput(inputLine);
				out.println(outputLine);
				if (outputLine.equals("EXIT"))
					break;
			}
			theSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * @return the theSocket
	 */
	public Socket getTheSocket() {
		return theSocket;
	}
	/**
	 * @param theSocket the theSocket to set
	 */
	private void setTheSocket(Socket theSocket) {
		this.theSocket = theSocket;
	}

}
