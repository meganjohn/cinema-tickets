package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketBasket;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static uk.gov.dwp.uc.pairtest.validation.TicketPurchaseRequestValidator.validateTicketPurchaseRequest;

public class TicketPurchaseRequestValidatorTest {

    private final Integer ADULT_TICKETS = 5;
    private final Integer CHILD_TICKETS = 3;
    private final Integer INFANT_TICKETS = 3;
    private final long ACCOUNT_ID = 2345;

    @Test
    void validRequest_DoesNotThrow() {
        EnumMap<TicketRequest.Type, Integer> basketMap = new EnumMap<>(
                Map.of(
                        TicketRequest.Type.ADULT, ADULT_TICKETS,
                        TicketRequest.Type.CHILD, CHILD_TICKETS,
                        TicketRequest.Type.INFANT, INFANT_TICKETS));
        final TicketBasket basket = new TicketBasket();
        basket.setBasket(basketMap);

        assertDoesNotThrow(() -> validateTicketPurchaseRequest(basket, ACCOUNT_ID));
    }

    @Test
    void invalidRequest_InvalidAccountId() {
        final long invalidAccountId = 0;
        EnumMap<TicketRequest.Type, Integer> basketMap = new EnumMap<>(
                Map.of(
                        TicketRequest.Type.ADULT, ADULT_TICKETS,
                        TicketRequest.Type.CHILD, CHILD_TICKETS,
                        TicketRequest.Type.INFANT, INFANT_TICKETS));
        final TicketBasket basket = new TicketBasket();
        basket.setBasket(basketMap);

        Exception exception = assertThrows(InvalidPurchaseException.class, () -> validateTicketPurchaseRequest(basket, invalidAccountId));

        String expectedMessage = "Invalid account ID provided. Account ID: 0";

        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    void invalidRequest_InsufficientAdultTickets() {
        final Integer insufficientAdultTickets = 1;
        EnumMap<TicketRequest.Type, Integer> basketMap = new EnumMap<>(
                Map.of(
                        TicketRequest.Type.ADULT, insufficientAdultTickets,
                        TicketRequest.Type.CHILD, CHILD_TICKETS,
                        TicketRequest.Type.INFANT, INFANT_TICKETS));
        final TicketBasket basket = new TicketBasket();
        basket.setBasket(basketMap);

        Exception exception = assertThrows(InvalidPurchaseException.class, () -> validateTicketPurchaseRequest(basket, ACCOUNT_ID));

        String expectedMessage = "Insufficient adult tickets requested.";

        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    void invalidRequest_TooManyTickets() {
        final Integer tooManyAdultTickets = 17;
        EnumMap<TicketRequest.Type, Integer> basketMap = new EnumMap<>(
                Map.of(
                        TicketRequest.Type.ADULT, tooManyAdultTickets,
                        TicketRequest.Type.CHILD, CHILD_TICKETS,
                        TicketRequest.Type.INFANT, INFANT_TICKETS));
        final TicketBasket basket = new TicketBasket();
        basket.setBasket(basketMap);

        Exception exception = assertThrows(InvalidPurchaseException.class, () -> validateTicketPurchaseRequest(basket, ACCOUNT_ID));

        String expectedMessage = "Invalid quantity of tickets requested. Requested: 23. Max permitted per request: 20";

        assertEquals(exception.getMessage(), expectedMessage);
    }
}
