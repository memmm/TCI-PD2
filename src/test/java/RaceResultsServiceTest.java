import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;

public class RaceResultsServiceTest {


    private RaceResultsService raceResults = new RaceResultsService();
    private Message message = mock(Message.class);
    private Message message2 = mock(Message.class);
    private String MSG_CONTENT = "I watched C-beams glitter in the dark near the Tannhäuser Gate. All those moments will be lost in time, like tears in rain.";
    private Date MSG_DATE = new Date();
    private Client clientA = mock(Client.class, "clientA");
    private Client clientB = mock(Client.class, "clientB");
    private MessageLog log = mock(MessageLog.class);

    /**
     * Example from the book (pages 80-84)
     * */
    @Test
    public void notSubscribedClient_ShouldNotReceiveMessage() {
        raceResults.send(message, raceResults.getMessagingLists().get(0));
        verify(clientA, never()).receive(message);
        verify(clientB, never()).receive(message);
    }

    /**
     * Example from the book (pages 80-84)
     * */
    @Test
    public void subscribedClient_ShouldReceiveMessage() {
        raceResults.addSubscriber(clientA, raceResults.getMessagingLists().get(0));
        raceResults.send(message, raceResults.getMessagingLists().get(0));
        verify(clientA).receive(message);
    }

    /**
     * Example from the book (pages 80-84)
     * */
    @Test
    public void allSubscribedClients_ShouldReceiveMessages() {
        raceResults.addSubscriber(clientA, raceResults.getMessagingLists().get(0));
        raceResults.addSubscriber(clientB, raceResults.getMessagingLists().get(0));
        raceResults.send(message, raceResults.getMessagingLists().get(0));
        verify(clientA).receive(message);
        verify(clientB).receive(message);
    }

    /**
     * Example from the book (pages 80-84)
     * */
    @Test
    public void multiSubscriber_shouldReceiveOnlyOneMessage() {
        raceResults.addSubscriber(clientA, raceResults.getMessagingLists().get(0));
        raceResults.addSubscriber(clientA, raceResults.getMessagingLists().get(0));
        raceResults.send(message, raceResults.getMessagingLists().get(0));
        verify(clientA).receive(message);
    }

    /**
     * Example from the book (pages 80-84)
     * */
    @Test
    public void unsubscribedClient_ShouldNotReceiveMessages() {
        raceResults.addSubscriber(clientA, raceResults.getMessagingLists().get(0));
        raceResults.removeSubscriber(clientA, raceResults.getMessagingLists().get(0));
        raceResults.send(message, raceResults.getMessagingLists().get(0));
        verify(clientA, never()).receive(message);
    }

    /* Race Results Enhanced - RaceResults should send messages with the results of different categories of race - e.g. horse
      races, F1 races, boat-races, etc. Subscribers should be able to subscribe to selected categories. Make
      sure they receive only messages related to the ones they have signed up for. */

    /**
     * static test
     */
    @Test
    public void SUT_ShouldReturnMoreThanZeroLists() {
        Assert.assertNotSame(0,raceResults.getMessagingLists().size());
    }

    /**
     *  Dummy object
     */
    @Test
    public void subscriber_AfterSubscribeToSelectedCategory_ShouldBeOnList() {
        HashSet<Client> list = raceResults.getMessagingLists().get(0);
        raceResults.addSubscriber(clientA, list);
        Assert.assertThat(list, hasItem(clientA));
    }

    /**
     * Indirect output (test spy)
    */
    @Test
    public void subscriber_AfterSubscribeToSelectedCategory_ShouldReceiveOnlyRelevantMessage() {
        raceResults.addSubscriber(clientA, raceResults.getMessagingLists().get(0));
        raceResults.send(message, raceResults.getMessagingLists().get(1));
        verify(clientA, never()).receive(message);
    }

    /* Each message sent by RaceResultsService should be logged. Introduce a logging DOC, and make
      sure that the date and text of each message is logged. Do not implement the logging mechanism:
      concentrate on the interactions between the service and its collaborator */

    /**
     * Indirect output (stub) - SUT calls Message's getText() and getDate() getters and passes it to Logger's log() (Indirect input)
     */
    @Test
    public void logger_WhenMessageIsSent_ShouldLog() {
        when(message.getText()).thenReturn(MSG_CONTENT);
        when(message.getDate()).thenReturn(MSG_DATE);
        raceResults.send(message, raceResults.getMessagingLists().get(0));
        verify(log).log(MSG_CONTENT, MSG_DATE);
    }


    /* In the tests implemented so far, RaceResultsService sends only one message. This is unrealistic!
       Enhance the tests to ensure that subscribers will receive any number of sent messages. */

    /**
     * Indirect output
     * */
    @Test
    public void subscriber_WhenSubscribedToMultipleCategories_ShouldReceiveMultipleMessages() {
        raceResults.addSubscriber(clientA, raceResults.getMessagingLists().get(0));
        raceResults.addSubscriber(clientA, raceResults.getMessagingLists().get(1));
        raceResults.send(message, raceResults.getMessagingLists().get(0));
        raceResults.send(message2, raceResults.getMessagingLists().get(1));
        verify(clientA).receive(message);
        verify(clientA).receive(message);
    }

    /* What should happen if a client that is not subscribed tries to unsubscribe? Make up your mind about
       it, write a test which verifies this behaviour, and make RaceResultsService behave accordingly.*/

    /**
     * Dummy, static test -We assert that the number of clients in the list is the same as it was before the
     * unnecessary unsubscription.
     * */
    @Test
    public void notASubscriber_TriesToSubscribe_NothingShouldHappen() {
        int memberNo = raceResults.getMessagingLists().get(0).size();
        raceResults.removeSubscriber(clientA, raceResults.getMessagingLists().get(0));
        Assert.assertSame(memberNo, raceResults.getMessagingLists().get(0));

    }

}
