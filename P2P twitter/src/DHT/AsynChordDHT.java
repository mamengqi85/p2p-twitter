package DHT;

import java.net.MalformedURLException;
import de.uniba.wiai.lspi.chord.data.URL;
import de.uniba.wiai.lspi.chord.service.AsynChord;
import de.uniba.wiai.lspi.chord.service.ServiceException;

public class AsynChordDHT {
	
	public AsynChord create(String port) {
		de.uniba.wiai.lspi.chord.service.PropertiesLoader.loadPropertyFile ();
		String protocol = URL.KNOWN_PROTOCOLS.get(URL.SOCKET_PROTOCOL);
		URL localURL = null;
		try {
			localURL = new URL(protocol + "://localhost:" + port + "/"); 
		} catch ( MalformedURLException e) {
				System.out.println(e.getLocalizedMessage());
				throw new RuntimeException(e);
		}
		AsynChord chord = new de.uniba.wiai.lspi.chord.service.impl.ChordImpl();
		try {
			chord.create(localURL);
		} catch (ServiceException e) {
			throw new RuntimeException ("Could not create DHT !", e);
		}
		
		return chord;
	}
	
	public AsynChord join(String destPort, String localPort) {
		//de.uniba.wiai.lspi.chord.service.PropertiesLoader.loadPropertyFile();
		String protocol = URL.KNOWN_PROTOCOLS.get(URL.SOCKET_PROTOCOL);
		URL localURL = null;
		try {
			localURL = new URL (protocol + "://localhost:" + localPort + "/");
		} catch (MalformedURLException e){
			throw new RuntimeException(e);
		}
		URL bootstrapURL = null;
		try {
			bootstrapURL = new URL (protocol + "://localhost:" + destPort + "/");
		} catch ( MalformedURLException e) {
			throw new RuntimeException(e);
		}
		//Chord chord = new de.uniba.wiai.lspi.chord.service.impl.ChordImpl();
		AsynChord chord = new de.uniba.wiai.lspi.chord.service.impl.ChordImpl();
		try {
			chord.join (localURL, bootstrapURL);
		} catch (ServiceException e) {
			throw new RuntimeException("Could not join DHT!", e);
		}
		
		return chord;
	}
	
	public void insertKey(AsynChord chord, StringKey myKey, String data) {
		MyCallBack callback = new MyCallBack();
		chord.insert(myKey, data, callback);
		//callback.inserted(myKey, data, null);
	}
	
	public void removeKey(AsynChord chord, StringKey myKey, String data) {
		MyCallBack callback = new MyCallBack();
		chord.remove(myKey, data, callback);
		//callback.removed(myKey, data, null);
	}
	
	public void retrieveKey(AsynChord chord, StringKey myKey) {
		MyCallBack callback = new MyCallBack();
		chord.retrieve(myKey, callback);
		//callback.retrieved(myKey, entries, null);
	}
	
	public void leave(AsynChord chord) {
		try {
			chord.leave();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
