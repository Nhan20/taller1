package co.edu.unbosque.controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import co.edu.unbosque.model.persistence.FileHandler;

public class Servidor extends Thread {

	private Socket socketRead;
	private Socket socketSend;
	private ServerSocket server;
	private DataInputStream in;
	private DataOutputStream out;
	private int port;
	private String addressClient;
	private Properties prop;

	public Servidor(int port) {
		super();
		this.socketRead = null;
		this.socketSend = null;
		this.server = null;
		this.in = null;
		this.out = null;
		this.port = port;
		this.addressClient = null;
		this.prop = new Properties();
	}

	@Override
	public void run() {
		String message;

		try {
			this.server = new ServerSocket(this.port);
			System.out.println("Server started");
			System.out.println("Waiting for a client ...");
			this.socketRead = server.accept();
			System.out.println("Client accepted");

			this.socketSend = new Socket(this.socketRead.getInetAddress(), this.port + 1);
			message();

			this.in.close();
			this.server.close();

		} catch (IOException i) {
			System.out.println(i);
		}

		System.out.println("Closing connection");
		try {
			socketRead.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void message() {

		String name = "";
		String op = "";

		for (int j = 0; j <= 4; j++) {
			try {
				switch (j) {
				case 0: {
					this.in = new DataInputStream(new BufferedInputStream(socketRead.getInputStream()));
					this.out = new DataOutputStream(socketSend.getOutputStream());
					this.out.writeUTF("Bienvenido a nuestro sistema de pedidos\nIngrese su nombre");
					name = in.readUTF();
					break;
				}
				case 1: {
					this.in = new DataInputStream(new BufferedInputStream(socketRead.getInputStream()));
					this.out = new DataOutputStream(socketSend.getOutputStream());
					prop = FileHandler.cargarPropiedades("vueltauno.properties");
					this.out.writeUTF(prop.getProperty("menu.opciones"));
					op = in.readUTF();
					break;
				}
				case 2: {
					this.in = new DataInputStream(new BufferedInputStream(socketRead.getInputStream()));
					this.out = new DataOutputStream(socketSend.getOutputStream());
					prop = FileHandler.cargarPropiedades("vueltados.properties");
					this.out.writeUTF(prop.getProperty("menu.opciones" + op));
					op += in.readUTF();
					break;
				}
				case 3: {
					this.in = new DataInputStream(new BufferedInputStream(socketRead.getInputStream()));
					this.out = new DataOutputStream(socketSend.getOutputStream());
					prop = FileHandler.cargarPropiedades("vueltatres.properties");
					this.out.writeUTF(prop.getProperty("menu.opciones" + op));
					op = in.readUTF();
					break;
				}
				case 4: {
					this.in = new DataInputStream(new BufferedInputStream(socketRead.getInputStream()));
					this.out = new DataOutputStream(socketSend.getOutputStream());
					prop = FileHandler.cargarPropiedades("vueltacuatro.properties");
					this.out.writeUTF(prop.getProperty("menu.opciones.datos"));
					op = in.readUTF();
					break;
				}
				case 5: {
					this.in = new DataInputStream(new BufferedInputStream(socketRead.getInputStream()));
					this.out = new DataOutputStream(socketSend.getOutputStream());
					this.out.writeUTF("Gracias por usar nuestros servicios");
					break;
				}
				}
			} catch (IOException i) {
				System.out.println(i);
			}

		}

	}

	public static void main(String args[]) {

		Servidor server = new Servidor(8888);
		server.start();
	}

}
