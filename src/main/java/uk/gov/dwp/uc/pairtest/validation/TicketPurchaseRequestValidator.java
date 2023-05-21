package uk.gov.dwp.uc.pairtest.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidAccountNumberException;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.exception.InvalidTicketQuantityException;
import uk.gov.dwp.uc.pairtest.exception.NoAdultTicketRequestedException;

import static uk.gov.dwp.uc.pairtest.validation.AccountNumberIsValid.accountNumberIsValid;
import static uk.gov.dwp.uc.pairtest.validation.AdultTicketsRequestedIsValid.adultTicketsRequestedIsValid;
import static uk.gov.dwp.uc.pairtest.validation.TicketNumberIsValid.ticketQuantityIsValid;

public class TicketPurchaseRequestValidator {

    private static final Logger LOGGER = LogManager.getLogger(TicketPurchaseRequestValidator.class);
    public static void validateTicketPurchaseRequest(TicketPurchaseRequest ticketPurchaseRequest) {
        try {
            if(!accountNumberIsValid(ticketPurchaseRequest.getAccountId())) {
                LOGGER.error("Invalid account ID: {}. Request: {}", ticketPurchaseRequest.getAccountId(),ticketPurchaseRequest);
                throw new InvalidAccountNumberException("Invalid account ID provided.");
            }

            if (!(ticketQuantityIsValid(ticketPurchaseRequest.getTicketTypeRequests()))) {
                LOGGER.error("Invalid quantity of tickets requested. Must not request more than 20 tickets per transaction. Request: {}", ticketPurchaseRequest);
                throw new InvalidTicketQuantityException("Invalid quantity of tickets requested.");
            }

            if(!(adultTicketsRequestedIsValid(ticketPurchaseRequest.getTicketTypeRequests()))) {
                LOGGER.error("Not enough adult tickets requested. An adult ticket must be purchased with every infant ticket, or at least one if purchasing child tickets. Request: {}", ticketPurchaseRequest);
                throw new NoAdultTicketRequestedException("Insufficient adult tickets requested.");
            }

        } catch (InvalidPurchaseException exception) {
            throw new InvalidPurchaseException(exception.getMessage());
        }

    }
}
