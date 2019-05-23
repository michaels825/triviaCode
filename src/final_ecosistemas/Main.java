package final_ecosistemas;

import java.net.DatagramSocket;

import processing.core.PApplet;

public class Main extends PApplet{

	public static void main(String[] args) {
PApplet.main("final_ecosistemas.Main");



	}
	
	static public PApplet app;
	Logica log;
	
@Override
public void settings() {
size(1200, 700);
}

@Override
public void setup() {
	app = this;
log = new Logica(this);
	
}

@Override
public void draw() {
log.pintar();
}

@Override
public void mousePressed() {
log.mousePressed();
}

@Override
public void keyPressed() {
log.keyPressed();
}


}
