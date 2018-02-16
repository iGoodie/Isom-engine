package lib.input.keyboard;

public class Keys {
	public static final KeyPair KEY_ESC = new KeyPair('\u001B', 0x0000001B);

	public static final KeyPair KEY_UP = new KeyPair('\uFFFF', 0x00000026);
	public static final KeyPair KEY_DOWN = new KeyPair('\uFFFF', 0x00000028);
	public static final KeyPair KEY_LEFT = new KeyPair('\uFFFF', 0x00000025);
	public static final KeyPair KEY_RIGHT = new KeyPair('\uFFFF', 0x00000027);

	public static final KeyPair KEY_F4 = new KeyPair('\u0000', 0x00000064);
	public static final KeyPair KEY_F11 = new KeyPair('\u0000', 0x0000006B);

	public static final KeyPair KEY_W = new KeyPair('W', 0x00000057);
	public static final KeyPair KEY_A = new KeyPair('A', 0x00000041);
	public static final KeyPair KEY_S = new KeyPair('S', 0x00000053);
	public static final KeyPair KEY_D = new KeyPair('D', 0x00000044);
	public static final KeyPair KEY_F = new KeyPair('F', 0x00000046);
	public static final KeyPair KEY_E = new KeyPair('E', 0x00000045);

	public static final KeyPair KEY_SHIFT = new KeyPair('\uFFFF', 0x00000010);
	public static final KeyPair KEY_CTRL = new KeyPair('\uFFFF', 0x00000011);
	public static final KeyPair KEY_ALT = new KeyPair('\uFFFF', 0x00000012);

}
