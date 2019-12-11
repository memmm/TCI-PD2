import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RaceResultsServiceTest {

    @Test
    public void subscribedClientShouldReceiveMessage() {
        RaceResultsService raceResults = new RaceResultsService();
        Client client = mock(Client.class);
        Message message = mock(Message.class);

        raceResults.addSubscriber(client);
        raceResults.send(message);
        verify(client).receive(message);
    }

    @Test
    public void messageShouldBeSentToAllSubscribedClients() {
        RaceResultsService raceResults = new RaceResultsService();
        Client clientA = mock(Client.class, "clientA");
        Client clientB = mock(Client.class, "clientB");
        Message message = mock(Message.class);
        raceResults.addSubscriber(clientA);
        raceResults.addSubscriber(clientB);
        raceResults.send(message);
        verify(clientA).receive(message);
        verify(clientB).receive(message);
    }
}
