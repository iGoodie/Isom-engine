package stages;

import core.TestGame;
import lib.animation.Animation1f;
import lib.animation.Animation1f.Easing1f;
import lib.graphics.DebugRenderer;
import lib.image.PivotImage;
import lib.input.keyboard.Keyboard;
import lib.stage.Stage;

public class IntroStage extends Stage {
	PivotImage logo;
	Animation1f transparencyAnim;
	float transparency = 0;
	String loadingInfo;
	
	public IntroStage() {
		TestGame game = TestGame.getGame();
		logo = new PivotImage(game.loadImage("logo.png"));
		loadingInfo = "Fetching..";
		transparencyAnim = new Animation1f(0, 255, 1);
		transparencyAnim.easing = Easing1f.SINE_OUT;
	}
	
	@Override
	public void update(float dt) {
		transparency = transparencyAnim.proceed(dt);
		DebugRenderer.appendLine(2, "Transparency: " + (int)transparency);
		if(transparencyAnim.isFinished() && Keyboard.isKeyActiveOnce(' ')) {
			TestGame game = TestGame.getGame();
			game.changeStage(new TestStage());
		}
	}

	@Override
	public void render() {
		TestGame game = TestGame.getGame();
		game.background(0xFF_ADDAC4);
		game.pushStyle();
		game.tint(255, transparency);
		game.image(logo, game.width/2f, game.height * (0.5f - 0.1f));
		if(transparencyAnim.isFinished()) {
			game.textWithStroke("Press space to continue..", 
					(game.width/2f - game.textWidth("Press space to continue..")/2f), 
					game.height * (0.5f + 0.3f));
		}
		game.popStyle();
	}

}
