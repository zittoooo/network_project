import java.rmi.Remote;
import java.rmi.RemoteException;


// ������ �������̽�

public interface Hello extends Remote {
  //  String sayHello() throws RemoteException;
    int getID(int max, int min) throws RemoteException;
    void registerID(String msg) throws RemoteException;
    boolean isDuplicated(int ID) throws RemoteException;
}
