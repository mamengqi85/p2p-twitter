package DHT;

import static org.junit.Assert.*;
import org.junit.*;

import de.uniba.wiai.lspi.chord.service.AsynChord;
import de.uniba.wiai.lspi.chord.service.Chord;
import de.uniba.wiai.lspi.chord.service.ServiceException;


public class ChordUtilityTest {
/*	
	@Test
	public void testAsynChord () {
		fail("");
		AsynChordDHT dht = new AsynChordDHT();
		AsynChord chord1 = dht.create("12345");
		AsynChord chord2 = dht.join("12345", "8181");
		
		System.out.println("start Chord2:");
		String data = "Just an Example.";
		StringKey myKey = new StringKey(data);
		dht.insertKey(chord2, myKey, data);
		dht.insertKey(chord2, myKey, "ni cai shi example");
		String data2 = "ni quan jia dou shi example";
		StringKey myKey2 = new StringKey(data2);
		dht.insertKey(chord2, myKey2, data2);
		dht.retrieveKey(chord2, myKey);
		dht.retrieveKey(chord2, myKey2);
		System.out.println("end Chord2");
		
		AsynChord chord3 = dht.join("12345", "8282");
		
		System.out.println("start Chord3:");
		dht.retrieveKey(chord3, myKey);
		//Utility.removeKeyWithFuture(chord, myKey, data);
		dht.removeKey(chord3, myKey, data);
		dht.retrieveKey(chord3, myKey);
		dht.retrieveKey(chord3, myKey2);
		System.out.println("end Chord3:");
	}
	*/
	
//	@Test
//	public void testChord () {
	public static void main(String[] args) {
		ChordDHT dht = new ChordDHT();
		//Chord chord1 = dht.create("127.0.0.1", "12345");
		Chord chord1 = dht.create("12345");
		Chord chord2 = dht.join("12345", "8181");
		//Chord chord2 = dht.join("127.0.0.1", "12345", "127.0.0.1", "8181");
		
		System.out.println("start Chord2:");
		String data = "Just an Example.";
		StringKey myKey = new StringKey(data);
		dht.insertKey(chord2, myKey, data);
		dht.insertKey(chord2, myKey, "ni cai shi example");
		String data2 = "ni quan jia dou shi example";
		StringKey myKey2 = new StringKey(data2);
		dht.insertKey(chord2, myKey2, data2);
		System.out.println(dht.retrieveKey(chord2, myKey));
		System.out.println(dht.retrieveKey(chord2, myKey2));
		System.out.println("end Chord2");
		
		//Chord chord3 = dht.join("127.0.0.1", "12345", "127.0.0.1", "8282");
		Chord chord3 = dht.join("12345", "8282");
		
		System.out.println("start Chord3:");
		System.out.println(dht.retrieveKey(chord3, myKey));
		//Utility.removeKeyWithFuture(chord, myKey, data);
		dht.removeKey(chord2, myKey, data);
		System.out.println(dht.retrieveKey(chord3, myKey));
		System.out.println(dht.retrieveKey(chord3, myKey2));
		System.out.println("end Chord3:");
		
		//dht.leave(chord2);
/*		try {
			chord3.leave();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(dht.retrieveKey(chord3, myKey));
		System.out.println(dht.retrieveKey(chord3, myKey2));
		*/
	}
}
