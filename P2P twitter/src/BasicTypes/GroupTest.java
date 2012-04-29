package BasicTypes;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class GroupTest {

	@Test
	public void testMemberListToString() {
		Group g = new Group("test");
		g.AddUser("1");
		g.AddUser("2");
		String temp = g.MemberListToString();
		ArrayList<String> result= g.getMemberList(temp);
		
		assertTrue(result.contains("1"));
		assertTrue(result.contains("2"));
		
	}

}
