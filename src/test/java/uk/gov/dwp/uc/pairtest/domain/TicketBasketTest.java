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

        TicketBasket basket = new TicketBasket(ticketPurchaseRequest);

        assertEquals(basket.getTicketsForType(TicketRequest.Type.ADULT), 6);
        assertEquals(basket.getTicketsForType(TicketRequest.Type.CHILD), 5);
        assertEquals(basket.getTicketsForType(TicketRequest.Type.INFANT), 4);
        assertEquals(basket.getTotalTickets(), 15);
    }

    @Test
    void buildBasketCorrectly_TwoOfOneTicketType() {
        TicketRequest[] ticketRequests = new TicketRequest[]{ADULT_TICKET_REQUEST, ADULT_TICKET_REQUEST};
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(ACCOUNT_ID, ticketRequests);

        TicketBasket basket = new TicketBasket(ticketPurchaseRequest);

        assertEquals(basket.getTicketsForType(TicketRequest.Type.ADULT), 12);
        assertEquals(basket.getTicketsForType(TicketRequest.Type.CHILD), 0);
        assertEquals(basket.getTicketsForType(TicketRequest.Type.INFANT), 0);
        assertEquals(basket.getTotalTickets(), 12);
    }
}
