package basic_tests;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

import core.TestGame;
import lib.config.CommandLineArgs;
import lib.maths.IsoVector;

public class LaunchBuilderTest {

	public static void waitWhile(Function<Integer, Boolean> evaluativeFunc) {
		long start = System.currentTimeMillis();
		while(evaluativeFunc.apply((int)((System.currentTimeMillis()-start)/1000))) {}
	}

	@Test
	public void launcherShouldParseCmdLineArgs() {
		String cmdLineArgs = "--fullscreen -username=admin --testFlag";

		// Initiate the game loop
		TestGame.main(cmdLineArgs.split("\\s+"));

		// Wait until stage is created
		waitWhile(second -> TestGame.getGame().getCurrentStage() == null);

		// Fetch the command line args prototype
		CommandLineArgs cla = TestGame.getGame().getCmdLineArgs();

		// Assert parsed parts
		Assert.assertTrue("Username argument should exist", cla.containsArgument("username"));
		Assert.assertTrue("Fullscreen flag should exist", cla.containsFlag("fullscreen"));
		Assert.assertTrue("TestFlag flag should exist", cla.containsFlag("testFlag"));
		Assert.assertEquals("Username arg should be 'admin'", cla.getArgument("username"), "admin");
	}

	@Test
	public void launcherShouldStartSystemWithParameters() {}

	@Test
	public void gameConsoleShouldParseCorrectly() {
		String cmdLineArgs = "-username=admin --testFlag";

		// Initiate the game loop
		TestGame.main(cmdLineArgs.split("\\s+"));
		TestGame game = TestGame.getGame();

		// Wait until stage is created
		waitWhile(second -> game.getCurrentStage() == null);

		// Wait until loading stage is disposed
		waitWhile(second -> game.getCurrentStage().name.equals("Intro Stage"));

		// Select the camera at index 0
		game.selectCamera(0);
		
		// Execute movecam command on the console
		String worldMovingCommand = "movecam 11 22 w";
		game.console.parseAndExecute(worldMovingCommand);
		
		// Wait the camera to complete it's animation
		waitWhile(second -> second<1 || game.getCamera().inMotion());
		
		// Assert the new location
		Assert.assertEquals("Came should've been moved to (11,22) on World Space",
				game.getCamera().getWorldPos(), new IsoVector(11, 22));
	}

}
