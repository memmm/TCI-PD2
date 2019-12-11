/**
 * A service whose role is to inform interested parties about the results of races.
 * There is a notification service, which allows clients to subscribe.
 * The service should send out messages to all of its subscribers
 * */
public class RaceResultsService {

    private Client client;
    public void addSubscriber(Client client) {
        this.client = client;
    }
    public void send(Message message) {
        client.receive(message);
    }
}
