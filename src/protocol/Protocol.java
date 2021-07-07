import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Protocol extends Remote {
    public void send(String msg) throws RemoteException;

    public String getName() throws RemoteException;

    public List<Protocol> getClients() throws RemoteException;

    public void newClient(Protocol client) throws RemoteException;

    public void removeClient(Protocol client) throws RemoteException;

    public int getMessageNumber() throws RemoteException;

    public void updateClients() throws RemoteException;
}
