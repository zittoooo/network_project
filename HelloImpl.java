//서버쪽
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

//서버쪽!!

public class HelloImpl extends UnicastRemoteObject implements Hello {

    private static final long serialVersionUID = 1L;
    private static int PORT = 8050;

    public HelloImpl() throws RemoteException, Exception{
	super(PORT,
	      new RMISSLClientSocketFactory(),
	      new RMISSLServerSocketFactory());
    }

    //public String sayHello() {
    //	return "Hello World!";
   // }
    
    public int getID(int max, int min) throws RemoteException
    {
    	return (int)Math.floor(Math.random()*(max-min+1)) + min;
    }
    
    public boolean isDuplicated(int ID) throws RemoteException
    {
    	boolean isDuplicated = false;
    	try
    	{
	    	File file = new File("IDList.txt");
	    	FileReader fileReader = new FileReader(file);
	    	int tmp = 0;

	    	while((tmp = fileReader.read()) != -1) // check ID duplication
	    	{
	    		if(tmp == ID) isDuplicated = true;
	    	}
	    	fileReader.close();
	    		    	
    	}catch (FileNotFoundException e)
    	{
    		System.out.println(e);
    	}catch(IOException e)
    	{
    		System.out.println(e);
    	}
    	return isDuplicated;
    }
    public void registerID(String msg) throws RemoteException
    {
    	try
    	{
	    	File file = new File("IDList.txt");
	    	FileWriter fileWriter = new FileWriter(file,true);
	    	fileWriter.write(msg);
	    	fileWriter.flush();
	    	fileWriter.close();
	    	
    	}catch (FileNotFoundException e)
    	{
    		System.out.println(e);
    	}catch(IOException e)
    	{
    		System.out.println(e);
    	}
    }
    
    public static void main(String args[]) {

		// Create and install a security manager
		if (System.getSecurityManager() == null) {
		    System.setSecurityManager(new SecurityManager());
		}
	
		PORT = Reader.getPort(1);
		
		try {
		    // Create SSL-based registry
		    HelloImpl obj = new HelloImpl();
			RMISSLClientSocketFactory csf = new RMISSLClientSocketFactory();
			RMISSLServerSocketFactory ssf = new RMISSLServerSocketFactory();
			
		    Registry registry = LocateRegistry.createRegistry(PORT, csf, ssf);
		    
		    String hostName = "java.rmi.server.hostname";
		    String serverIP = csf.getIP();

		    System.setProperty(hostName,serverIP);
		    // Bind this object instance to the name "HelloServer"
		    registry.bind("HelloServer", obj);

	
		    System.out.println("HelloServer bound in registry");
		} catch (Exception e) {
		    System.out.println("HelloImpl err: " + e.getMessage());
		    e.printStackTrace();
		}
    }  //main	
}
