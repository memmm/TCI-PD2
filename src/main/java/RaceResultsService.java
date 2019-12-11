import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * A service whose role is to inform interested parties about the results of races.
 * There is a notification service, which allows clients to subscribe.
 * The service should send out messages to all of its subscribers
 * */
public class RaceResultsService {

    private Collection<Client> F1MessagingList = new HashSet<Client>();
    private Collection<Client> HorseRaceMessagingList = new HashSet<Client>();
    private Collection<Client> BoatRaceMessagingList = new HashSet<Client>();

    public List<HashSet<Client>> getMessagingLists() {
        List lists = new ArrayList<Collection<Client>>();
        lists.add(F1MessagingList);
        lists.add(HorseRaceMessagingList);
        lists.add(BoatRaceMessagingList);
        return lists;
    }

    public void addSubscriber(Client client, Collection<Client> list) {
        list.add(client);
    }
    public void send(Message message, Collection<Client> list) {
        for (Client client : list) {
            client.receive(message);
        }
    }

    public void removeSubscriber(Client client, Collection<Client> list) {
        list.remove(client);
    }
}
