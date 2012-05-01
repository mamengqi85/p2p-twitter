package DHT;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Set;

import de.uniba.wiai.lspi.chord.com.local.ChordImplAccess;
import de.uniba.wiai.lspi.chord.data.URL;
import de.uniba.wiai.lspi.chord.service.Chord;
import de.uniba.wiai.lspi.chord.service.ServiceException;

public class ChordDHT {
	
	public Chord create(String ip, String port) {
		de.uniba.wiai.lspi.chord.service.PropertiesLoader.loadPropertyFile ();
		String protocol = URL.KNOWN_PROTOCOLS.get(URL.SOCKET_PROTOCOL);
		URL url = null;
		try {
			url = new URL(protocol + "://" + ip + ":" + port + "/");
		} catch ( MalformedURLException e) {
				System.out.println(e.getLocalizedMessage());
				throw new RuntimeException(e);
		}
		Chord chord = new de.uniba.wiai.lspi.chord.service.impl.ChordImpl();
		try {
			chord.create(url);
		} catch (ServiceException e) {
			throw new RuntimeException ("Could not create DHT !", e);
		}
		
		return chord;
	}
	
	public Chord create(String port) {
		de.uniba.wiai.lspi.chord.service.PropertiesLoader.loadPropertyFile ();
		String protocol = URL.KNOWN_PROTOCOLS.get(URL.SOCKET_PROTOCOL);
		URL url = null;
		try {
			url = new URL(protocol + "://localhost:" + port + "/");
		} catch ( MalformedURLException e) {
				System.out.println(e.getLocalizedMessage());
				throw new RuntimeException(e);
		}
		Chord chord = new de.uniba.wiai.lspi.chord.service.impl.ChordImpl();
		try {
			chord.create(url);
		} catch (ServiceException e) {
			throw new RuntimeException ("Could not create DHT !", e);
		}
		
		return chord;
	}
	
	public Chord join(String destPort, String localPort) {
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
			//bootstrapURL = new URL(protocol + "://" + destIp + ":" + destPort + "/");
		} catch ( MalformedURLException e) {
			throw new RuntimeException(e);
		}
		//Chord chord = new de.uniba.wiai.lspi.chord.service.impl.ChordImpl();
		Chord chord = new de.uniba.wiai.lspi.chord.service.impl.ChordImpl();
		try {
			chord.join (localURL, bootstrapURL);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not join DHT!", e);
		}
		
		return chord;
	}
	
	public Chord join(String destIp, String destPort, String localIp, String localPort) {
		de.uniba.wiai.lspi.chord.service.PropertiesLoader.loadPropertyFile();
		String protocol = URL.KNOWN_PROTOCOLS.get(URL.SOCKET_PROTOCOL);
		URL localURL = null;
		try {
			//localURL = new URL (protocol + "://localhost:" + localPort + "/");
			localURL = new URL (protocol + "://" + localIp + ":" + localPort + "/");
		} catch (MalformedURLException e){
			throw new RuntimeException(e);
		}
		URL bootstrapURL = null;
		try {
			//bootstrapURL = new URL (protocol + "://localhost:" + destPort + "/");
			bootstrapURL = new URL(protocol + "://" + destIp + ":" + destPort + "/");
		} catch ( MalformedURLException e) {
			throw new RuntimeException(e);
		}
		//Chord chord = new de.uniba.wiai.lspi.chord.service.impl.ChordImpl();
		Chord chord = new de.uniba.wiai.lspi.chord.service.impl.ChordImpl();
		try {
			chord.join (localURL, bootstrapURL);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not join DHT!", e);
		}
		
		return chord;
	}
	
	public void leave(Chord chord) {
		try {
			System.out.println(chord);
			Chord chord2 = chord;
			chord = null;
			chord2.leave();
			System.out.println("Node sucessfully left.");
		} catch (Throwable t) {
			System.out.println("left.");
		}
	}
	
	public void insertKey(Chord chord, StringKey myKey, String data) {
		try {
			chord.insert(myKey, data);
		} catch(ServiceException e) {
		// handle exception
			throw new RuntimeException ("Could not insert key!", e);
		}
	}
	
	public void removeKey(Chord chord, StringKey myKey, String data) {
		try {
			chord.remove(myKey, data);
		} catch(ServiceException e) {
		// handle exception
			throw new RuntimeException ("Could not remove key!", e);
		}
	}
	
	public Set<Serializable> retrieveKey(Chord chord, StringKey myKey) {
		Set<Serializable> entries;
		System.out.println("DHTChord"+ chord);
		System.out.println("DHTChord"+ myKey);
		try {
			entries = chord.retrieve(myKey);
		} catch(ServiceException e) {
		// handle exception
			throw new RuntimeException ("Could not retrieve key!", e);
		}
		return entries;
	}
}
