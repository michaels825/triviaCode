package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import final_ecosistemas.Logica;


public class Receptor extends Thread {

	private Socket paquete;

	private DataInputStream entrada;
	private DataOutputStream salida;
	private Observer observador;
	private boolean conectado;
	private Logica log;
	

	public Receptor(Socket paquete) {
		try {
			this.conectado = true;
			this.paquete = paquete;
			entrada = new DataInputStream(paquete.getInputStream());
			salida = new DataOutputStream(paquete.getOutputStream());
			log = Logica.log;

			start();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		recibir();
	}

	public void recibir() {
		while (this.conectado) {

			try {
				// Esperando datos
				// Cuanto puedo recibir
				byte[] capacidad = new byte[50];

				entrada.read(capacidad);

				String informacion = new String(capacidad).trim();

				if (this.observador != null) {
					this.observador.mensajeRecibido(informacion);
				}
				mensajeRecibidoServidor(informacion);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void enviar(final String datos) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					salida.write(datos.getBytes());
					salida.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();

	}

	public void mensajeRecibidoServidor(String mensaje) {
		

	}
	
	
	public void setObservador(Observer observador) {
		this.observador = observador;
	}

}
