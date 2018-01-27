package core;

import java.util.Arrays;

import lib.GameBase;
import lib.Timer.Ticker;
import lib.camera.Coordinator;
import lib.config.LaunchBuilder;
import lib.maths.IsoMath;
import lib.maths.IsoVector;
import lib.util.ConsoleLogger;
import processing.core.PImage;
import processing.event.MouseEvent;

public class TestGame extends GameBase implements TestConstants {
	/* Singleton */
	private static TestGame game;
	public static TestGame getGame() {
		return game;
	}
	
	/**/
	private static Ticker tickTimer = new Ticker();
	
	private PImage test_tile;
	
	public void settings() {
		game = this;
		size(ST_WIDTH, ST_HEIGHT, P2D);
	}
	
	public void setup() {
		surface.setTitle(WINDOW_TITLE);
		surface.setResizable(false);
		
		smooth();
		
		selectCamera(0);
		getCamera().resize(ST_WIDTH, ST_HEIGHT);
		Coordinator.setParent(this);
		
		test_tile = loadImage("test.png");
	}
	
	public void draw() {
		background(0xFF_000000);
		
		update();
		
		render();
		
		//Takes input after this line
	}
	
	private void update() {
		tickTimer.update();
		float dt = tickTimer.deltaSec();
		float tick = tickTimer.getTick();
		
		//TODO update
		getCamera().update(dt);
		//getCamera().move(10*dt, 10*dt);
		//getCamera().rotate(PI/360 * dt * 10);
		//getCamera().zoomTo(cos(millis() / 500f) + 1.5f);
	}
	
	private void render() {
		renderPreDebug();
		
		getCamera().attachCamera(); {
			circle(0, 0, 10);
		}
		getCamera().deattachCamera();
		
		getCamera().attachCamera(); { //Render by camera options
			translate(-64, -32);
			for(int i=0; i<5; i++) {
				for(int j=0; j<3; j++) {
					IsoVector pos = Coordinator.worldToCanvas(i, j);
					image(test_tile, pos.x, pos.y);
				}
			}
		}
		getCamera().deattachCamera();
		
		renderPostDebug();
	}
	
	private void renderPreDebug() {
		//Draw grid
		stroke(100);
		for(int i=10; i<width; i+=10) {
			line(i, 0, i, height);
		}
		for(int i=10; i<height; i+=10) {
			line(0, i, width, i);			
		}
	}
	
	private void renderPostDebug() {
		ellipse(width/2, height/2, 10, 10);
		getCamera().renderDebug(this);
	}
	
	/**/
	@Override
	public void mousePressed() {
		/*IsoVector c = Coordinator.screen2Camera(getCamera(), mouseX, mouseY);
		System.out.println(c);*/
		/*IsoVector s2c = Coordinator.screenToCanvas(getCamera(), new IsoVector(mouseX, mouseY));
		System.out.println(s2c);*/
		/*IsoVector w2c = Coordinator.worldToCanvas(new IsoVector(0, 1));
		System.out.println(w2c);*/
	}
	
	@Override
	public void mouseWheel(MouseEvent e) {
		float z = IsoMath.clamp(getCamera().getZoom() + e.getCount() * -0.1f, 0.5f, 2f);
		getCamera().zoomTo(z);
	}
	
	@Override
	public void keyPressed() {
		//System.out.println(keyCode + " | " + key);
		if(keyCode == CODED) {}
		else {
			if(key == '+') {
				getCamera().zoom(0.5f);
			}
			else if(key == '-') {
				getCamera().zoom(-0.5f);
			}
			else if(key == ' ') {
				getCamera().move(10, 10);
			}
		}
	}
	
	/* Unique Main Method */
	public static void main(String[] args) {
		LaunchBuilder builder = new LaunchBuilder(TestGame.class, args);
		builder.argDisplayPrimaryMonitor();
		builder.argWindowColor(0xFF_000000);
		args = builder.build();
		ConsoleLogger.info("Launch Arguments: " + Arrays.toString(args));
		GameBase.main(args); //Continues on other thread (sync)
		//ConsoleProgram.start(new String[]{});
	}
}
