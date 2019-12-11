import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RaceResultsServiceTest {

    /**
     * Example from the book (pages 80-84)
     * */
    private RaceResultsService raceResults = new RaceResultsService();
    private Message message = mock(Message.class);
    private Client clientA = mock(Client.class, "clientA");
    private Client clientB = mock(Client.class, "clientB");
    @Test
    public void subscribedClient_ShouldReceiveMessage() {
        raceResults.addSubscriber(clientA);
        raceResults.send(message);
        verify(clientA).receive(message);
    }
    @Test
    public void allSubscribedClients_ShouldReceiveMessages() {
        raceResults.addSubscriber(clientA);
        raceResults.addSubscriber(clientB);
        raceResults.send(message);
        verify(clientA).receive(message);
        verify(clientB).receive(message);
    }
}
