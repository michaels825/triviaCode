package final_ecosistemas;

import server.Observer;
import server.Receptor;

public class Usuario implements Observer{

	private Logica log;
	private Receptor receptor;
	private boolean responder;
	private int puntuacion;
	private String respuesta;
	private boolean estado;
	
	public Usuario(Logica log, Receptor receptor){
		this.responder = false;
		this.log = log;
		this.puntuacion = 0;
		this.respuesta = "";
		this.receptor = receptor;
		this.receptor.setObservador(this);
		this.estado = false;
	}

	public boolean isResponder() {
		return responder;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public void setResponder(boolean responder) {
		this.responder = responder;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}
	

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@Override
	public void mensajeRecibido(String mensaje) {
		
		if(this.responder == false &&  log.getPreguntaActual()!=null && mensaje.contains("Respuesta")) {
			this.responder = true;
			String[] separar = mensaje.split(";");
			this.respuesta = separar[1];
			log.getPreguntaActual().validar(this);
			log.validarPregunta();
			//System.out.println(this.log.getContarRespuestas());
			
			boolean terminar = log.getPantalla()+1 >= log.getPreguntas().size();
			System.out.println("terminar: " + terminar + "   " +"de : " + (log.getPantalla()+1)+ "    " +  log.getPreguntas().size());
			
			if(this.log.getContarRespuestas()!=0) {
				this.receptor.enviar("espera;"+this.estado+";"+this.puntuacion+";"+terminar);
				
			}else {
				this.receptor.enviar("respuesta;"+this.estado+";"+this.puntuacion+";"+terminar);
			}
			
			System.out.println("Respondio la " + respuesta);
		}
		
	}

	
}
