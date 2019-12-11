import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class RaceResultsServiceTest {

    @Test
    public void subscribedClientShouldReceiveMessage() {
        RaceResultsService raceResults = new RaceResultsService();
        Client client = mock(Client.class);
        Message message = mock(Message.class);
    }
}
