package Utility;

import org.junit.Test;

public class UtilityTest {

	@Test
	public void testGetIP() {
		System.out.println(Utility.getIP());
	}
	
	@Test
	public void testGetPort() {
		System.out.println(Utility.getPort());
	}
	
	@Test
	public void getInteger() {
		//int port = Integer.getInteger(Utility.getPort());
		int port = Integer.parseInt(Utility.getPort());
		System.out.println(port);
	}
}
