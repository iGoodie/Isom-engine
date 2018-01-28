package lib.util;

public class Performance {
	public static final long MEGABYTE_TO_BYTE = 1024L * 1024L; //1MB = 1024*1024B
	
	private static final Runtime runtime = Runtime.getRuntime();
	
	public static long usedMemory() {
		long bytes = runtime.totalMemory() - runtime.freeMemory();
		return bytes;
	}
	
	public static long usedMemoryMB() {
		return usedMemory() / MEGABYTE_TO_BYTE;
	}

	public static long totalMemory() {
		return runtime.totalMemory();
	}
	
	public static long totalMemoryMB() {
		return totalMemory() / MEGABYTE_TO_BYTE;
	}
	
	public static long testTime(Runnable test) {
		return testTime(test, 1);
	}
	
	public static long testTime(Runnable test, int testNumber) {
		long timesum=0;
		
		long t0, t1;
		for(int i=0; i<testNumber; i++) {
			t0 = System.currentTimeMillis();
			test.run();
			t1 = System.currentTimeMillis();
			timesum += (t1-t0); // += dt
		}
		
		return timesum;
	}
	
	public static long testTimeAvg(Runnable test, int testNumber) {
		long timesum=0;
		
		long t0, t1;
		for(int i=0; i<testNumber; i++) {
			t0 = System.currentTimeMillis();
			test.run();
			t1 = System.currentTimeMillis();
			timesum += (t1-t0); // += dt
		}
		
		return timesum / testNumber;
	}
	
}
