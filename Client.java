import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

public class Client extends UnicastRemoteObject implements ClientImpl 
{
	public Client() throws RemoteException{}
	public void setMessage(String msg) throws RemoteException
	{
		System.out.println(msg);
	}
	
	public static void main(String[] args) throws Exception
	{
		Client cci=new Client();
		ServerImpl cs=(ServerImpl)Naming.lookup("rmi://192.168.43.187/chat");
		cs.setClient(cci);
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		
		
		while (true)
		{
			System.out.print("** ");
			String msg=in.readLine();
			cs.setMessage(msg);
		}
	}
}