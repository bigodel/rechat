import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Server {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        try {
            System.out.print("Choose a name for the chat: ");
            String name = scan.nextLine().trim();

            List<Protocol> clients = new ArrayList<Protocol>();

            Chat server = new Chat(name);

            System.setProperty("java.rmi.server.hostname", "localhost:1099");
            LocateRegistry.createRegistry(1099);
            // bind the server
            Naming.rebind("rmi://localhost:1099/chat", server);

            System.out.println("Chat <" + name + "> online!");

            while (true) {
                String msg = scan.nextLine().trim();
                server.updateClients();

                if (!server.getClients().isEmpty()) {
                    msg = server.getMessageNumber() + " | !<" + server.getName() + ">: " + msg;
                    clients = server.getClients();
                    for (Protocol client : clients) {
                        client.send(msg);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e);
        } finally {
            scan.close();
        }
    }
}
