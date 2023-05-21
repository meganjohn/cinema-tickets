package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.TicketRequest;

public class TicketNumberIsValid {

    public static boolean ticketQuantityIsValid(TicketRequest[] ticketRequests) {
        int totalTicketsRequested = 0;

        for (TicketRequest ticketRequest : ticketRequests
             ) {
            totalTicketsRequested = totalTicketsRequested + ticketRequest.getNoOfTickets();
            if (totalTicketsRequested > 20) {
                return false;
            }
        }

        return true;
    }

}
