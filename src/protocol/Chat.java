import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Chat extends UnicastRemoteObject implements Protocol {

    public String name;
    public Protocol client = null;
    public int messageNumber = 0;

    private List<Protocol> clients = new ArrayList<Protocol>();

    public Chat(String name) throws RemoteException {
        this.name = name;
    }

    public String getName() throws RemoteException {
        return this.name;
    }

    public List<Protocol> getClients() throws RemoteException {
        return clients;
    }

    public int getMessageNumber() throws RemoteException {
        return messageNumber;
    }

    public void send(String msg) throws RemoteException {
        System.out.println(msg);
        messageNumber++;
    }

    public void newClient(Protocol client) throws RemoteException {
        send("<" + client.getName() + "> has entered the chat!");
        for (Protocol c : clients) {
            c.send("<" + client.getName() + "> has entered the chat!");
        }
        clients.add(client);
    }

    public void removeClient(Protocol client) throws RemoteException {
        send("<" + client.getName() + "> left the chat!");
        for (Protocol c : clients) {
            c.send("<" + client.getName() + "> left the chat!");
        }
        clients.remove(client);
    }

    public void updateClients() throws RemoteException {
        List<Protocol> aux = clients;
        for (Protocol client : aux) {
            if (client.equals(null)) {
                removeClient(client);
            }
        }
    }
}
