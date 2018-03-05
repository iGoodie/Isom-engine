package lib.util;

public class ArrayUtils {
	public static String[] merge(String[]...arrays) {
		int length = 0;
	    for (String[] array : arrays) {
	        length += array.length;
	    }
	    
	    String[] result = new String[length];
	    
	    int pos = 0;
	    for (String[] array : arrays) {
	        for (String element : array) {
	            result[pos] = element;
	            pos++;
	        }
	    }
	    
	    return result;
	}

	public static String[] appendElement(String[] array, String element) {
		String[] tmp = new String[array.length+1];
		
		for(int i=0; i<array.length; i++) {
			tmp[i] = array[i];
		}
		tmp[tmp.length-1] = element;
		
		return tmp;
	}

	/*public static class ArrayList2D<T> {
		private ArrayList<ArrayList<T>> list = new ArrayList<>();
		
		public ArrayList2D() {
			this(1, 1);
		}
		
		public ArrayList2D(int initRow, int initColumn) {
			for(int i=0; i<initRow; i++) {
				ArrayList<T> columnList = new ArrayList<>(initColumn);
				list.add(columnList);
			}
		}
		
		public boolean add(int row, Collection<T> collection) {
			if(row == list.size()) list.add(new ArrayList<>());
			return list.get(row).addAll(collection);
		}
		
		public T removeTail(int row) {
			ArrayList<T> rowList = list.get(row);
			return rowList.remove(rowList.size() - 1);
		}
		
		public T removeHead(int row) {
			return list.get(row).remove(0);
		}
		
		public T removeIndex(int row, int column) {
			return list.get(row).remove(column);
		}
		
		public void print() {
			list.forEach((rowList)->{
				rowList.forEach((columnValue)->{
					System.out.print(columnValue + "\t");
				});
				System.out.println();
			});
		}
	}
	
	public static <T> void printArray(T[] array) {
		if(array.length == 0) {
			System.out.println("Array: {}");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("Array: { ");
		for(int i=0; i<array.length-1; i++) {
			sb.append(array[i].toString() + ", ");
		}
		sb.append(array[array.length-1].toString() + " }");
		
		ConsoleLogger.info(sb);
	}
	
	public static void printArray(int[] array) {
		if(array.length == 0) {
			System.out.println("Array: {}");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("Array: { ");
		for(int i=0; i<array.length-1; i++) {
			sb.append(array[i] + ", ");
		}
		sb.append(array[array.length-1] + " }");
		
		ConsoleLogger.info(sb);
	}
	
	public static <T> void printArray(T[] array, String label) {
		if(array.length == 0) {
			System.out.println("Array: {}");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(label+": { ");
		for(int i=0; i<array.length-1; i++) {
			sb.append(array[i].toString() + ", ");
		}
		sb.append(array[array.length-1].toString() + " }");
		
		ConsoleLogger.info(sb);
	}
	
	public static void printArray(int[] array, String label) {
		if(array.length == 0) {
			System.out.println("Array: {}");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(label+": { ");
		for(int i=0; i<array.length-1; i++) {
			sb.append(array[i] + ", ");
		}
		sb.append(array[array.length-1] + " }");
		
		ConsoleLogger.info(sb);
	}*/
}
