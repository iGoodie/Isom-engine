package lib.input.mouse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

public class Mouse {

	private static List<MouseListener> registeredListeners = new ArrayList<>();

	@Getter
	private static List<MousePress> activePresses = new ArrayList<>();

	public static void buttonActivated(int x, int y, int count, int button) {
		MousePress press = new MousePress(x, y, count, button);

		int index = activePresses.indexOf(press);

		// If press is already in, increase count
		if(index >= 0) {
			int deltaCount = press.count;
			press = activePresses.get(index);
			press.count += deltaCount;
		}
		else { // Else add it in
			activePresses.add(press);			
		}
		
		// Notify every listener about this activation
		final var tmpPress = press;
		registeredListeners.forEach(listener -> listener.mousePressed(tmpPress));
	}

	public static void buttonDeactivated(int x, int y, int count, int button) {
		MousePress press = new MousePress(x, y, count, button);

		activePresses.remove(press);

		// Notify every listener about this activation
		var tmpPress = press;
		registeredListeners.forEach(listener -> listener.mouseReleased(tmpPress));
	}

	public static void wheelMoved(float downCount) {
		registeredListeners.forEach(listener -> listener.wheelMoved(downCount));
	}

	public static void subscribe(MouseListener listener) {
		registeredListeners.add(listener);
	}

	public static void unsubscribe(MouseListener listener) {
		registeredListeners.remove(listener);
	}

	public static void reset() {
		activePresses.clear();
	}

	/* Checkers */
	public static boolean isButtonActive(int button) {
		return activePresses.stream()
				.anyMatch(press -> press.button == button);
	}

	public static boolean isButtonActive(MousePress press) {
		return activePresses.contains(press);
	}

	/* Getters */
	public static List<String> getPressList() {
		return activePresses.stream()
				.map(key -> key.toString())
				.collect(Collectors.toList());
	}

	/* Buttons */
	public static final int BTN_RIGHT = 39;
	public static final int BTN_LEFT = 37;
	public static final int BTN_MID = 3;

}
