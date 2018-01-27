package lib.util;

public class Performance {
	public static final long MEGABYTE = 1024L * 1024L; //1MB = 1024*1024B
	
	private static final Runtime runtime = Runtime.getRuntime();
	
	public static long usedMemory() {
		long bytes = runtime.totalMemory() - runtime.freeMemory();
		return bytes;
	}
	
	public static long usedMemoryMB() {
		return usedMemory() / MEGABYTE;
	}

	public static long totalMemory() {
		return runtime.totalMemory();
	}
	
	public static long totalMemoryMB() {
		return totalMemory() / MEGABYTE;
	}
	
	public static long testTime(Runnable test) {
		return testTimeAvg(test, 1);
	}
	
	public static long testTimeAvg(Runnable test, int testNumber) {
		long timesum=0;
		
		for(int i=0; i<testNumber; i++) {
			timesum += testTime(test);
		}
		
		return timesum / testNumber;
	}
}
