package co.edu.unbosque.controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Cliente extends Thread {

	private Socket socket;
	private ServerSocket server;
	private Scanner sc;
	private DataOutputStream out;
	private DataInputStream in;
	private String address;
	private int port;

	public Cliente(String address, int port) {
		this.socket = null;
		this.server = null;
		this.sc = new Scanner(System.in);
		this.out = null;
		this.in = null;
		this.address = address;
		this.port = port;
	}

	@Override
	public void run() {

		String line = "";
		try {
			this.socket = new Socket(this.address, this.port);
			System.out.println("Connected");
			this.out = new DataOutputStream(socket.getOutputStream());
			this.server = new ServerSocket(this.port + 1);
			this.socket = server.accept();

			for (int j = 0; j <= 4; j++) {

				this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				System.out.println("Elija una opcion:");
				System.out.println(in.readUTF());
				line = sc.nextLine();
				this.out.writeUTF(line);

			}
			this.out.close();
			this.socket.close();
			this.in.close();
			this.server.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			out.close();
			socket.close();
		} catch (IOException i) {
			System.out.println(i);
		}

	}

	public static void main(String args[]) {
		Cliente client = new Cliente("127.0.0.1", 8888);
		client.start();
	}

}
