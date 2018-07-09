import java.rmi.*;

public interface  ServerImpl extends Remote {
	public void setClient(ClientImpl cc) throws RemoteException;
	public void setMessage(String msg) throws RemoteException;
	
}
