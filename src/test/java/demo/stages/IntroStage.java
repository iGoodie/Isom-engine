package demo.stages;

import demo.TestGame;
import lib.animation.Animation1f;
import lib.animation.Easing1f;
import lib.graphics.DebugRenderer;
import lib.graphics.Fonts;
import lib.image.PivotImage;
import lib.input.keyboard.KeyPair;
import lib.input.keyboard.Keyboard;
import lib.input.keyboard.KeyboardListener;
import lib.registry.SpriteRegistry;
import lib.registry.TileRegistry;
import lib.resource.ResourceLoader;
import lib.stage.Stage;

public class IntroStage extends Stage<TestGame> implements KeyboardListener {

	PivotImage isomEngineLogo;
	PivotImage testGamelogo;

	Animation1f transparency;

	ResourceLoader loader;

	public IntroStage(TestGame game) {
		super(game);
		name = "Intro Stage";

		// Load splash before resources
		isomEngineLogo = new PivotImage(game.loadImage("logo.png"));
		testGamelogo = new PivotImage(game.loadImage("testlogo.png"));
		transparency = new Animation1f(0, 255, 2, Easing1f.SINE_IN);

		// Load resources sync
		loader = new ResourceLoader() {
			@Override
			public void run() {
				this.loadingInfo = randomLine();

				// Load lines for ResourceLoader
				this.loadingInfo = "Loading resource loader...";
				for (String line : parent.loadStrings("resourceloader_lines.txt")) {
					ResourceLoader.submitLine(line);
				}

				// Load tiles & props
				this.loadingInfo = "Loading tiles...";
				TileRegistry.registerTile(game.loadImage("tiles/default.png"));
				TileRegistry.registerTile(game.loadImage("tiles/grass1.png"));
				TileRegistry.registerTile(game.loadImage("tiles/grass2.png"));
				TileRegistry.registerTile(game.loadImage("tiles/grass3.png"));
				SpriteRegistry.registerSprite(game.loadImage("props/tree1.png", 155, 290));
				SpriteRegistry.registerSprite(game.loadImage("props/tree2.png", 155, 290));
				SpriteRegistry.registerSprite(game.loadImage("props/toon_tree1.png", 80, 200));
				SpriteRegistry.registerSprite(game.loadImage("props/toon_tree2.png", 80, 200));

				// Loading done
				this.loadingInfo = "Loading done!";
				this.loading = false;
			}
		};
		loader.start();

		// Subscribe to keyboard events
		Keyboard.subscribe(this);
	}

	@Override
	public void update(float dt) {
		transparency.proceed(dt);
		DebugRenderer.appendLine(2, "Transparency: " + (int) transparency.getValue());

		// Change stage if animation is done & resources are loaded
		if (transparency.isFinished() && !loader.isLoading()) {
			parent.changeStage(new YetAnotherWorldStage(parent));
		}
	}

	@Override
	public void render() {
		parent.background(0xFF_ADDAC4);

		// Render test logo
		parent.pushStyle();
		{
			parent.tint(255, transparency.getValue());
			parent.imageOnPivot(testGamelogo, parent.width / 2f, parent.height / 2f);
		}
		parent.popStyle();

		// Render loading info
		parent.pushStyle();
		{
			parent.textAlign(TestGame.CENTER);
			parent.fill(0xFF_000000);
			parent.text(loader.getLoadingInfo(), parent.width / 2f, (parent.height + testGamelogo.height) / 2f + 40);
		}
		parent.popStyle();

		// Render "Powered by Isom-engine"; Quick and ugly test hardcode
		parent.pushStyle();
		parent.pushMatrix();
		{
			float scl = .4f;
			parent.translate(parent.width - isomEngineLogo.width / 2f * scl - 20,
					parent.height - isomEngineLogo.height / 2f * scl - 15);
			parent.scale(scl);
			parent.imageOnPivot(isomEngineLogo, 0, 0);
			parent.textFont(Fonts.getFont("intro-f1"));
			parent.textSize(35f);
			parent.textWithStroke("Powered by",
					-isomEngineLogo.width * scl, -isomEngineLogo.height * scl,
					0xFF_D4EEE1, 0xFF_000000);
		}
		parent.popMatrix();
		parent.popStyle();

		// TODO : Render loading progress
//		parent.pushStyle();
//		{
//			parent.noFill();
//			parent.rect(100, 300, 300, 25);			
//		}
//		parent.popStyle();

		/*
		 * parent.pushStyle(); { parent.tint(255, transparency); parent.image(logo,
		 * parent.width/2f, parent.height * (0.5f - 0.1f));
		 * 
		 * String text = (transparencyAnim.isFinished() && !loader.isLoading()) ?
		 * "Press space to continue.." : loader.getInfo();
		 * parent.textFont(Fonts.getFont("intro-f1")); parent.textWithStroke(text,
		 * (parent.width/2f - parent.textWidth(text)/2f), parent.height * (0.5f + 0.3f),
		 * 0xFF_D4EEE1, 0xFF_000000); } parent.popStyle();
		 */
	}

	@Override
	public void keyPressed(KeyPair pair) {
		if (pair.equals(Keyboard.KEY_SPACE)) {
			if (transparency.isFinished() && !loader.isLoading()) {
				parent.changeStage(new YetAnotherWorldStage(parent));
			}
		} else if (pair.equals(Keyboard.KEY_F1)) {
			transparency.reset();
		}
	}

	@Override
	public void dispose() {
		Keyboard.unsubscribe(this);
	}
}
