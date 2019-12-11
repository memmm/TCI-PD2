import java.util.*;

/**
 * A service whose role is to inform interested parties about the results of races.
 * There is a notification service, which allows clients to subscribe.
 * The service should send out messages to all of its subscribers
 * */
public class RaceResultsService {

    private Collection<Client> F1MessagingList = new HashSet<Client>();
    private Collection<Client> HorseRaceMessagingList = new HashSet<Client>();
    private Collection<Client> BoatRaceMessagingList = new HashSet<Client>();
    private MessageLog msgLog;

    public RaceResultsService(MessageLog msgLog) {
        this.msgLog = msgLog;
    }

    public Collection<Client> getList(int no) {
        if (no >= 0 && no <= getMessagingLists().size())
            return getMessagingLists().get(no);
        else return null;
    };

    public List<HashSet<Client>> getMessagingLists() {
        List lists = new ArrayList<Collection<Client>>();
        lists.add(F1MessagingList);
        lists.add(HorseRaceMessagingList);
        lists.add(BoatRaceMessagingList);
        return lists;
    }

    public void addSubscriber(Client client, Collection<Client> list)
    {
        if (client.getAge() >= 18)
            list.add(client);
    }

    public void send(Message message, Collection<Client> list) {
        msgLog.log(message.getText(), message.getDate());
        for (Client client : list) {
            client.receive(message);
        }
    }

    public void removeSubscriber(Client client, Collection<Client> list) {
        list.remove(client);
    }
}
