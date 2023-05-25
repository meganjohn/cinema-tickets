package uk.gov.dwp.uc.pairtest.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketBasketTest {

    private final long ACCOUNT_ID = 123;
    private final TicketRequest ADULT_TICKET_REQUEST = new TicketRequest(TicketRequest.Type.ADULT, 6);
    private final TicketRequest CHILD_TICKET_REQUEST = new TicketRequest(TicketRequest.Type.CHILD, 5);
    private final TicketRequest INFANT_TICKET_REQUEST = new TicketRequest(TicketRequest.Type.INFANT, 4);

    @Test
    void buildBasketCorrectly_OneOfEachTicketType() {
        TicketRequest[] ticketRequests = new TicketRequest[]{ADULT_TICKET_REQUEST, CHILD_TICKET_REQUEST, INFANT_TICKET_REQUEST};
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(ACCOUNT_ID, ticketRequests);

        TicketBasket basket = new TicketBasket().buildBasket(ticketPurchaseRequest);

        assertEquals(basket.getAdultTickets(), 6);
        assertEquals(basket.getChildTickets(), 5);
        assertEquals(basket.getInfantTickets(), 4);
    }

    @Test
    void buildBasketCorrectly_TwoOfOneTicketType() {
        TicketRequest[] ticketRequests = new TicketRequest[]{ADULT_TICKET_REQUEST, ADULT_TICKET_REQUEST};
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(ACCOUNT_ID, ticketRequests);

        TicketBasket basket = new TicketBasket().buildBasket(ticketPurchaseRequest);

        assertEquals(basket.getAdultTickets(), 12);
        assertEquals(basket.getChildTickets(), 0);
        assertEquals(basket.getInfantTickets(), 0);
    }
}
