package runner;

import java.io.IOException;

public class Tester {
	public static void main(String[] args) throws IOException {
		float f = 0.5f;
		int s = (int) (f * 1000);
		
		System.out.println(s/1000);
	}
}
