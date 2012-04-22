package Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerConfig {
	//FileOutputStream fos = new FileOutputStream();
	 int USERID;
	 int GROUPID;
     static ServerConfig server;
	 ServerTable tables;
	 
	
	private ServerConfig(){
		try {
			FileInputStream fis = new FileInputStream("Server.config");
			ObjectInputStream ois = new ObjectInputStream(fis);
			USERID = ois.readInt();
			GROUPID = ois.readInt();
			tables = (ServerTable)ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			try {
				FileOutputStream fos = new FileOutputStream("Server.congfig");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				USERID = 0;
				GROUPID = 0;
				tables = new ServerTable();
				oos.writeInt(USERID);
				oos.writeInt(GROUPID);
				oos.writeObject(tables);
				oos.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ServerConfig getInstance(){
		if(server == null){
			server = new ServerConfig();
		}
		return server;
	}
	
	
	public ServerTable getTables(){
		return tables;
	}
	
	
	public int getUSERID(){
		return USERID++;
	}
	
	public int getGROUPID(){
		return GROUPID++;
	}
	
	public void close(){
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("Server.congfig");	
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeInt(USERID);
			oos.writeInt(GROUPID);
			oos.writeObject(tables);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
}
