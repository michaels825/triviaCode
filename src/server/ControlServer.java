package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;

public class ControlServer extends Thread {

	static ControlServer globalServidor;

	private ServerSocket sevidor;
	private ArrayList<Receptor> usuarios;

	private DataInputStream entrada;
	private DataOutputStream salida;
	
	private ObserverServidor observador;
	
	

	boolean conectado;

	static public ControlServer getIntance() {
		if (globalServidor == null) {
			globalServidor = new ControlServer();
		}
		return globalServidor;
	}

	public ControlServer() {
	this.start();
	}
	
	@Override
	public void run() {
		try {
			this.usuarios = new ArrayList<>();
			// Inicializa el servidor y el puerto en el que va a escuhar
			sevidor = new ServerSocket(5000);
			conectado = true;

			while (conectado) {
				// Esperando clientes
				System.out.println("esperando conexion");
				Socket socket = sevidor.accept();
				System.out.println("conexion aceptada");
				
				System.out.println(socket.getInetAddress());
				//socket.close();
				Receptor usuario = new Receptor(socket);
				
				this.usuarios.add(usuario);
				if(this.observador != null) {
					this.observador.conexionRecibida(usuario);
				}
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setObservador(ObserverServidor observador) {
		this.observador = observador;
	}
	
	public interface ObserverServidor{
		public void conexionRecibida(Receptor receptor);
	}
	
	public void enviarAll(String mensaje) {
		
		for (int i = 0; i < this.usuarios.size(); i++) {
			Receptor u = this.usuarios.get(i);
			u.enviar(mensaje);
		}
	}

}
