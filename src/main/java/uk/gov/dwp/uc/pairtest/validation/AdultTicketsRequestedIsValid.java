package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.TicketRequest;

public class AdultTicketsRequestedIsValid {

    public static boolean adultTicketsRequestedIsValid(TicketRequest[] ticketRequests) {
        int numberOfInfantTickets = 0;
        int numberOfAdultTickets = 0;
        for(TicketRequest ticketRequest : ticketRequests) {
            if (ticketRequest.getTicketType().equals(TicketRequest.Type.INFANT)) {
                numberOfInfantTickets = numberOfInfantTickets + ticketRequest.getNoOfTickets();
            }
            if (ticketRequest.getTicketType().equals(TicketRequest.Type.ADULT)) {
                numberOfAdultTickets = numberOfAdultTickets + ticketRequest.getNoOfTickets();
            }
        }
        return numberOfAdultTickets >= numberOfInfantTickets;
    }
}
