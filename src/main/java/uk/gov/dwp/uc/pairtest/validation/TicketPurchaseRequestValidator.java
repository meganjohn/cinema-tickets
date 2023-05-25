package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.TicketBasket;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static uk.gov.dwp.uc.pairtest.validation.AccountNumberIsValid.accountNumberIsValid;
import static uk.gov.dwp.uc.pairtest.validation.AdultTicketsRequestedIsValid.adultTicketsRequestedIsValid;
import static uk.gov.dwp.uc.pairtest.validation.TicketNumberIsValid.ticketQuantityIsValid;

public class TicketPurchaseRequestValidator {

    private static final Integer maxTickets = 20;

    public static void validateTicketPurchaseRequest(TicketBasket ticketBasket, Long accountId) {

        try {
            accountNumberIsValid(accountId);

            ticketQuantityIsValid(ticketBasket, maxTickets);

            adultTicketsRequestedIsValid(ticketBasket);

        } catch (InvalidPurchaseException exception) {
            throw new InvalidPurchaseException(exception.getMessage());
        }

    }
}
