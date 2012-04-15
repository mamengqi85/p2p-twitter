package DHT;

import java.io.Serializable;
import java.util.Set;

public class MyCallBack implements de.uniba.wiai.lspi.chord.service.ChordCallback {

	@Override
	public void inserted(de.uniba.wiai.lspi.chord.service.Key key,
			Serializable entry, Throwable t) {
		if (t == null) {
			System.out.println("Successfully inserted " + entry + " with key " + key);
		} else {
			System.err.println("Insertion of " + entry + " with key " + key + " failed!");
			t.printStackTrace();
		}
	}

	@Override
	public void removed(de.uniba.wiai.lspi.chord.service.Key key,
			Serializable entry, Throwable t) {
		if (t == null) {
			System.out.println("Successfully removed " + entry + " with key " + key);
		} else {
			System.err.println("Removal of " + entry + " with key " + key + " failed!");
			t.printStackTrace();
		}
	}

	@Override
	public void retrieved(de.uniba.wiai.lspi.chord.service.Key key,
			Set<Serializable> entries, Throwable t) {
		if (t == null) {
			System.out.println("start retrieving!");
			System.out.println(entries);
		} else {
			System.err.println("Removal of " + "the entry with key " + key + " failed!");
			t.printStackTrace();
		}
	}
}