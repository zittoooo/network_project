
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class Server extends UnicastRemoteObject implements ServerImpl {

	
	private static Vector vc = new Vector();
	public Server() throws RemoteException{}
	
	public void setClient(ClientImpl cc) throws RemoteException
	{
		vc.add(cc);
		
	}
	
	public void setMessage(String msg) throws RemoteException
	{
		for(int i=0;i<vc.size();i++)
		{
			ClientImpl cc = (ClientImpl)vc.elementAt(i);
			cc.setMessage(msg);						
		}
	}
	
	
	public static void main(String[] args) throws Exception
	{
		Server csi = new Server();
		Naming.rebind("chat", csi);
		System.out.println("Server Ready...");
		
	}
	
	
}
