package uk.gov.dwp.uc.pairtest.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.gov.dwp.uc.pairtest.domain.TicketBasket;
import uk.gov.dwp.uc.pairtest.exception.InsufficientAdultTicketsRequestedException;

public class AdultTicketsRequestedIsValid {

    private static final Logger LOGGER = LogManager.getLogger(AdultTicketsRequestedIsValid.class);

    public static void adultTicketsRequestedIsValid(TicketBasket ticketBasket) {
        if (ticketBasket.getAdultTickets() < 1 || ticketBasket.getAdultTickets() < ticketBasket.getInfantTickets()) {
            LOGGER.error("Not enough adult tickets requested. An adult ticket must be purchased with every infant ticket, or at least one if purchasing child tickets.");
            throw new InsufficientAdultTicketsRequestedException("Insufficient adult tickets requested.");
        }
    }
}
