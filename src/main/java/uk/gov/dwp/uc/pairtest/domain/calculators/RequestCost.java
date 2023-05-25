package uk.gov.dwp.uc.pairtest.domain.calculators;


import uk.gov.dwp.uc.pairtest.domain.TicketBasket;

public class RequestCost {

    // With Spring, these could be configurable values stored in the application.yml
    private static final Integer adultTicketPrice = 20;
    private static final Integer childTicketPrice = 10;
    private static final Integer infantTicketPrice = 0;


    public static int calculatePrice(TicketBasket ticketBasket) {
        return (ticketBasket.getAdultTickets() * adultTicketPrice +
                ticketBasket.getChildTickets() * childTicketPrice +
                ticketBasket.getInfantTickets() * infantTicketPrice);
    }
}
