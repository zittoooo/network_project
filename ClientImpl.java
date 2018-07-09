import java.rmi.*;

public interface ClientImpl extends Remote{
	public void setMessage(String msg) throws RemoteException;
	
}
