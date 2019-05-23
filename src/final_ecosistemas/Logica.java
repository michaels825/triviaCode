package final_ecosistemas;

import java.awt.Image;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import server.ControlServer;
import server.Receptor;
import server.ControlServer.ObserverServidor;

public class Logica implements ObserverServidor {

	private PApplet app;
	static public Logica log;
	private PImage inicio, instrucciones, pregunta1, pregunta2, pregunta3, pregunta4, pregunta5, pregunta6, pregunta7,
			pregunta8, pregunta9, pregunta10;

	public int pantalla;
	private ArrayList<Pregunta> preguntas;
	private ArrayList<Usuario> usuarios;
	private int contarRespuestas;

	ControlServer controlServer;
	private Pregunta preguntaActual;

	String ip_local;

	public Logica(PApplet app) {
		this.app = app;
		log = this;

		pantalla = -2;

		inicio = app.loadImage("inicio.png");
		inicio.resize(this.app.width, this.app.height);
		instrucciones = app.loadImage("instrucciones.png");
		instrucciones.resize(this.app.width, this.app.height);
		pregunta1 = app.loadImage("01.png");
		pregunta2 = app.loadImage("02.png");
		pregunta3 = app.loadImage("03.png");
		pregunta4 = app.loadImage("04.png");
		pregunta5 = app.loadImage("05.png");
		pregunta6 = app.loadImage("06.png");
		pregunta7 = app.loadImage("07.png");
		pregunta8 = app.loadImage("08.png");
		pregunta9 = app.loadImage("09.png");
		pregunta10 = app.loadImage("10.png");

		preguntas = new ArrayList<Pregunta>();
		preguntas.add(new Pregunta(app, pregunta1, "A"));
		preguntas.add(new Pregunta(app, pregunta2, "A"));
		/*preguntas.add(new Pregunta(app, pregunta3, "A"));
		preguntas.add(new Pregunta(app, pregunta4, "A"));
		preguntas.add(new Pregunta(app, pregunta5, "A"));
		preguntas.add(new Pregunta(app, pregunta6, "A"));
		preguntas.add(new Pregunta(app, pregunta7, "A"));
		preguntas.add(new Pregunta(app, pregunta8, "A"));
		preguntas.add(new Pregunta(app, pregunta9, "A"));
		preguntas.add(new Pregunta(app, pregunta10, "A"));*/

		usuarios = new ArrayList<Usuario>();

		contarRespuestas = 0;
		this.preguntaActual = preguntas.get(0);

		controlServer = ControlServer.getIntance();
		controlServer.setObservador(this);

	}

	public Pregunta getPreguntaActual() {
		return preguntaActual;
	}

	public void pintar() {
		if (pantalla == -2) {
			app.image(inicio, 0, 0);
		}

		if (pantalla == -1) {
			app.image(instrucciones, 0, 0);
		}

		if (pantalla >= 0) {
			Pregunta p = preguntas.get(this.pantalla);
			p.pintar();
		}
	}

	public void keyPressed() {

	}

	public ArrayList<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(ArrayList<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

	@Override
	public void conexionRecibida(Receptor receptor) {
		usuarios.add(new Usuario(this, receptor));
		
		if(this.usuarios.size() >= 2) {
			this.pantalla++;
		}

	}

	public void validarPregunta() {

		this.contarRespuestas += 1;

		if (this.contarRespuestas >= this.usuarios.size()) {
			for (int i = 0; i < usuarios.size(); i++) {
				Usuario s = usuarios.get(i);
				s.setResponder(false);
			}
			if (this.pantalla + 1 < this.preguntas.size()) {
				this.controlServer.enviarAll("siguiente");
				this.pantalla++;
				this.preguntaActual = preguntas.get(this.pantalla);
			}
			this.contarRespuestas = 0;

		}

	}

	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public int getContarRespuestas() {
		return contarRespuestas;
	}

	public void setContarRespuestas(int contarRespuestas) {
		this.contarRespuestas = contarRespuestas;
	}
	
	

	public int getPantalla() {
		return pantalla;
	}

	public void mousePressed() {
		if (this.pantalla == -2 && app.mouseX > 0 && app.mouseX < 1200 && app.mouseY > 0 && app.mouseY < 700) {
			if (this.pantalla + 1 < this.preguntas.size()) {
			pantalla++;
			}

		}

	}

}
