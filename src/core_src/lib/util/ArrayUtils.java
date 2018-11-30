package lib.util;

public class ArrayUtils {
	
	public static String[] merge(String[]...arrays) {
		int length = 0;
	    for (String[] array : arrays) {
	        length += array.length;
	    }
	    
	    String[] output = new String[length];
	    
	    int pos = 0;
	    for (String[] array : arrays) {
	        for (String element : array) {
	            output[pos] = element;
	            pos++;
	        }
	    }
	    
	    return output;
	}

	public static String[] append(String[] array, String...elements) {
		String[] output = new String[array.length + elements.length];
		
		for(int i=0; i<array.length; i++) { // Copy original
			output[i] = array[i];
		}
		
		for(int i=0; i<elements.length; i++) { // Append elements
			output[output.length + i - 1] = elements[i];
		}
		
		return output;
	}

	public static String[] subset(String[] array, int startIndex) {
		String[] output = new String[array.length - startIndex];
		System.arraycopy(array, startIndex, output, 0, output.length);
		return output;
	}
}
