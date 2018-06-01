package stages;

import core.TestGame;
import lib.animation.Animation1f;
import lib.animation.Animation1f.Easing1f;
import lib.graphics.DebugRenderer;
import lib.image.PivotImage;
import lib.input.keyboard.Keyboard;
import lib.input.keyboard.Keys;
import lib.resources.ResourceLoader;
import lib.stage.Stage;

public class IntroStage extends Stage {
	
	PivotImage logo;
	
	Animation1f transparencyAnim;
	float transparency = 0;
	
	ResourceLoader loader;
	
	public IntroStage() {
		TestGame game = TestGame.getGame();
		
		name = "Intro Stage";
		
		logo = new PivotImage(game.loadImage("logo.png"));
		
		transparencyAnim = new Animation1f(0, 255, 1);
		transparencyAnim.easing = Easing1f.SINE_IN;
		
		// Load resources sync
		loader = new ResourceLoader() {
			@Override
			public void run() {
				this.loadingInfo = randomLine();
				
				// Load resources here
				
				this.loadingInfo = "Loading done!";
				this.loading = false;
			}
		};
		loader.start();
	}
	
	@Override
	public void update(float dt) {
		transparency = transparencyAnim.proceed(dt);
		DebugRenderer.appendLine(2, "Transparency: " + (int)transparency);
		
		if(transparencyAnim.isFinished() && Keyboard.isKeyActiveOnce(' ')) {
			TestGame game = TestGame.getGame();
			game.changeStage(new TestStage());
		}
		else if(Keyboard.isKeyActiveOnce(Keys.KEY_F1)) { //Debug purposes
			transparencyAnim.reset();
		}
	}

	@Override
	public void render() {
		TestGame game = TestGame.getGame();
		game.background(0xFF_ADDAC4);
		game.pushStyle();
		game.tint(255, transparency);
		game.image(logo, game.width/2f, game.height * (0.5f - 0.1f));
		if(transparencyAnim.isFinished() && !loader.isLoading()) {
			game.textWithStroke("Press space to continue..", 
					(game.width/2f - game.textWidth("Press space to continue..")/2f), 
					game.height * (0.5f + 0.3f));
		}
		else {
			game.textWithStroke(loader.getInfo(), 
					(game.width/2f - game.textWidth("Press space to continue..")/2f), 
					game.height * (0.5f + 0.3f));
		}
		game.popStyle();
	}
}
