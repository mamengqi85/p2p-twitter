package DHT;

import de.uniba.wiai.lspi.chord.service.AsynChord;
import de.uniba.wiai.lspi.chord.service.Chord;

public class ChordUtilityTest {

	public static void main (String[] args) {
		chordDHT dht = new chordDHT();
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
	}
	
}