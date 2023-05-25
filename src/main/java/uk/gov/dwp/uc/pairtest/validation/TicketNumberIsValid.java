package uk.gov.dwp.uc.pairtest.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.gov.dwp.uc.pairtest.domain.TicketBasket;
import uk.gov.dwp.uc.pairtest.exception.InvalidTicketQuantityException;

public class TicketNumberIsValid {

    private static final Logger LOGGER = LogManager.getLogger(TicketNumberIsValid.class);

    public static void ticketQuantityIsValid(TicketBasket ticketBasket, Integer maxTickets) throws InvalidTicketQuantityException {
        if (ticketBasket.getTotalTickets() > maxTickets) {
            LOGGER.error("Invalid quantity of tickets requested. Must not request more than 20 tickets per transaction.");
            throw new InvalidTicketQuantityException(String.format("Invalid quantity of tickets requested. Requested: %s. Max permitted per request: %s", ticketBasket.getTotalTickets(), maxTickets));
        }
    }
}
