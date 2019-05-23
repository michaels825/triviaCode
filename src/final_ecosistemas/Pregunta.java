package final_ecosistemas;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Pregunta {

	private PApplet app;
	private PImage imagen;
	private String respuesta;

	public Pregunta(PApplet app, PImage imagen, String respuesta) {
		this.app = app;
		this.imagen = imagen;
		this.respuesta = respuesta;
		this.imagen.resize(1200, 700);

	}

	public void pintar() {
		app.imageMode(PConstants.CORNER);
		app.image(imagen, 0, 0);
	}

	public void validar(Usuario usuario) {
		//System.out.println(this.respuesta + "  " + usuario.getRespuesta());
		if(usuario.getRespuesta().equals(this.respuesta)) {
			usuario.setPuntuacion(usuario.getPuntuacion() +10);
			usuario.setEstado(true);
		}else {usuario.setEstado(false);};
		
	}
}
