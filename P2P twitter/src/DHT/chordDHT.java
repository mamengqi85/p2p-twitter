package DHT;

import java.net.MalformedURLException;

import de.uniba.wiai.lspi.chord.data.URL;
import de.uniba.wiai.lspi.chord.service.Chord;
import de.uniba.wiai.lspi.chord.service.ServiceException;

public class chordDHT {
	
	
	
	void create(){ 
			de.uniba.wiai.lspi.chord.service.PropertiesLoader.
			loadPropertyFile();
			String protocol = URL.KNOWN_PROTOCOLS.get(URL.SOCKET_PROTOCOL); URL localURL = null;
			try {
			localURL = new URL(protocol + "://localhost:12345/"); 
			} catch (MalformedURLException e){
			throw new RuntimeException(e); 
			}
			Chord chord = new de.uniba.wiai.lspi.chord.service.impl.ChordImpl ();
			try {
				chord.create(localURL);
			} catch (ServiceException e) {
				throw new RuntimeException("Could not create DHT!", e);
			}
			 
	}
}
