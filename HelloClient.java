import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class HelloClient extends UnicastRemoteObject {

	private static final long serialVersionUID = 1L;
	public HelloClient() throws RemoteException{}
    private static int PORT = 8050;

    public static void main(String args[]) {
    String host = "";	  
	PORT = Reader.getPort(1);
	try {
		
		RMISSLClientSocketFactory csf =  new RMISSLClientSocketFactory();
		host = csf.getIP();
		Registry registry = LocateRegistry.getRegistry(host , PORT, csf);
	   	   
	    Hello obj = (Hello) registry.lookup("HelloServer");
	    
	    
	    int min = 100; //
	    int max = 999;
	    int ID = obj.getID(min,max);

	    while(obj.isDuplicated(ID))
	    {
	    	ID = obj.getID(min,max);
	    }
    	int[] key = RSA.generateKey(); //N,L,E,D
    	String msg = key[0]+","+key[3]+","+ID+","+key[2]+"\n";//N,D,text,L
	  
    	obj.registerID(msg);
	    
	    
	    System.out.println(ID+","+key[2]+","+key[0]);//text,E,N
	} catch (Exception e) {
	    System.out.println("HelloClient exception: " + e.getMessage());
	    e.printStackTrace();
	}
    }
}
