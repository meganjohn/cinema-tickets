package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.TicketBasket;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static uk.gov.dwp.uc.pairtest.validation.AccountIdIsValid.AccountIdIsValid;
import static uk.gov.dwp.uc.pairtest.validation.AdultTicketsRequestedIsValid.adultTicketsRequestedIsValid;
import static uk.gov.dwp.uc.pairtest.validation.TicketNumberIsValid.ticketQuantityIsValid;

public class TicketPurchaseRequestValidator {

    private static final Integer MAX_TICKETS = 20;

    public static void validateTicketPurchaseRequest(TicketBasket ticketBasket, Long accountId) {

        try {
            AccountIdIsValid(accountId);

            ticketQuantityIsValid(ticketBasket, MAX_TICKETS);

            adultTicketsRequestedIsValid(ticketBasket);

        } catch (InvalidPurchaseException exception) {
            throw new InvalidPurchaseException(exception.getMessage());
        }

    }
}
