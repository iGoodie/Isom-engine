package lib.util;

public class Stringifier {
	/**
	 * Parses hex string with given integer.
	 * 
	 * @param value  Value to be parsed.
	 * @param digits Digits of result string
	 * @return Hex string of given integer.
	 */
	public static String asHex(int value, int digits) {
		String hex = asHex(value);

		// Fill zeros
		if (digits < 0)
			return hex;

		int length = hex.length();

		if (length == digits) {
			return hex;
			
		} else if (length > digits) {
			return hex.substring(length - digits);
			
		} else {
			String zeros = "";
			for (int i = 0; i < (digits - length); i++)
				zeros += "0";
			return zeros + hex;
		}
	}

	public static String asHex(int value) {
		return Integer.toHexString(value).toUpperCase();
	}
}
