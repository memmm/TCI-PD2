import java.util.ArrayList;
import java.util.Collection;

/**
 * A service whose role is to inform interested parties about the results of races.
 * There is a notification service, which allows clients to subscribe.
 * The service should send out messages to all of its subscribers
 * */
public class RaceResultsService {

    private Collection<Client> clients = new ArrayList<Client>();
    public void addSubscriber(Client client) {
        clients.add(client);
    }
    public void send(Message message) {
        for (Client client : clients) {
            client.receive(message);
        }
    }
}
