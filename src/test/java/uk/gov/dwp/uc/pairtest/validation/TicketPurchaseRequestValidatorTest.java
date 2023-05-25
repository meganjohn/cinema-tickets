package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketBasket;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.*;
import static uk.gov.dwp.uc.pairtest.validation.TicketPurchaseRequestValidator.validateTicketPurchaseRequest;

public class TicketPurchaseRequestValidatorTest {

    private final Integer ADULT_TICKETS = 5;
    private final Integer CHILD_TICKETS = 3;
    private final Integer INFANT_TICKETS = 3;
    private final long ACCOUNT_ID = 2345;

    @Test
    void validRequest_DoesNotThrow() {
        final TicketBasket basket = new TicketBasket(ADULT_TICKETS, CHILD_TICKETS, INFANT_TICKETS);

        assertDoesNotThrow(() -> validateTicketPurchaseRequest(basket, ACCOUNT_ID));
    }

    @Test
    void invalidRequest_InvalidAccountId() {
        final long invalidAccountId = 0;
        final TicketBasket basket = new TicketBasket(ADULT_TICKETS, CHILD_TICKETS, INFANT_TICKETS);

        Exception exception = assertThrows(InvalidPurchaseException.class, () -> validateTicketPurchaseRequest(basket, invalidAccountId));

        String expectedMessage = "Invalid account ID provided.";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void invalidRequest_InsufficientAdultTickets() {
        final Integer insufficientAdultTickets = 1;
        final TicketBasket basket = new TicketBasket(insufficientAdultTickets, CHILD_TICKETS, INFANT_TICKETS);

        Exception exception = assertThrows(InvalidPurchaseException.class, () -> validateTicketPurchaseRequest(basket, ACCOUNT_ID));

        String expectedMessage = "Insufficient adult tickets requested.";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void invalidRequest_TooManyTickets() {
        final Integer tooManyAdultTickets = 17;
        final TicketBasket basket = new TicketBasket(tooManyAdultTickets, CHILD_TICKETS, INFANT_TICKETS);

        Exception exception = assertThrows(InvalidPurchaseException.class, () -> validateTicketPurchaseRequest(basket, ACCOUNT_ID));

        String expectedMessage = "Invalid quantity of tickets requested.";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
