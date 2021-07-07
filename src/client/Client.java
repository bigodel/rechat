import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {

    String clientName;

    public static void main(String[] argv) {
        Scanner scan = new Scanner(System.in);
        try {
            List<Protocol> otherClients = new ArrayList<Protocol>();

            // look for the server
            Protocol server = (Protocol) Naming.lookup("rmi://localhost:1099/chat");

            String name = "";
            boolean safeName = false;
            while (!safeName) {
                System.out.print("Your username, please: ");
                name = scan.nextLine().trim();
                safeName = true;
                for (Protocol client : server.getClients()) {
                    if (client.getName().equals(name)) {
                        safeName = false;
                        System.out.println("There is already a client with this name.");
                        break;
                    }
                }
            }
            Protocol client = new Chat(name);

            System.out.println("Chatting is on!");
            server.newClient(client);

            while (true) {
                String msg = scan.nextLine().trim();
                msg = server.getMessageNumber() + " | <" + client.getName() + ">: " + msg;
                server.send(msg);

                server.updateClients();

                otherClients = server.getClients();
                for (Protocol otherClient : otherClients) {
                    if (!client.equals(otherClient))
                        otherClient.send(msg);
                }
            }
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e);
        } finally {
            scan.close();
        }
    }

}
