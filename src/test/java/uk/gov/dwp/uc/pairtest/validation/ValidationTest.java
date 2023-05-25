package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketBasket;
import uk.gov.dwp.uc.pairtest.exception.InsufficientAdultTicketsRequestedException;
import uk.gov.dwp.uc.pairtest.exception.InvalidAccountNumberException;
import uk.gov.dwp.uc.pairtest.exception.InvalidTicketQuantityException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static uk.gov.dwp.uc.pairtest.validation.AccountNumberIsValid.accountNumberIsValid;
import static uk.gov.dwp.uc.pairtest.validation.AdultTicketsRequestedIsValid.adultTicketsRequestedIsValid;
import static uk.gov.dwp.uc.pairtest.validation.TicketNumberIsValid.ticketQuantityIsValid;

public class ValidationTest {

    @Test
    void validAccountNumber() {
        assertDoesNotThrow(() -> accountNumberIsValid(12345L));
    }

    @Test
    void invalidAccountNumber_Zero() {
        assertThrows(InvalidAccountNumberException.class, () -> accountNumberIsValid(0L));
    }

    @Test
    void invalidAccountNumber_Negative() {
        assertThrows(InvalidAccountNumberException.class, () -> accountNumberIsValid(-10L));
    }

    @Test
    void validAdultTickets_ChildInfant() {
        Integer ADULT_TICKETS = 4;
        Integer CHILD_TICKETS = 3;
        Integer INFANT_TICKETS = 2;

        assertDoesNotThrow(() -> adultTicketsRequestedIsValid(new TicketBasket(ADULT_TICKETS, CHILD_TICKETS, INFANT_TICKETS)));
    }

    @Test
    void validAdultTickets_NoChildNoInfant() {
        Integer ADULT_TICKETS = 2;

        assertDoesNotThrow(() -> adultTicketsRequestedIsValid(new TicketBasket(ADULT_TICKETS, 0, 0)));
    }

    @Test
    void validAdultTickets_MoreChildThanAdult() {
        Integer ADULT_TICKETS = 2;
        Integer CHILD_TICKETS = 4;

        assertDoesNotThrow(() -> adultTicketsRequestedIsValid(new TicketBasket(ADULT_TICKETS, CHILD_TICKETS, 0)));
    }

    @Test
    void validAdultTickets_InfantNoChild() {
        Integer ADULT_TICKETS = 4;
        Integer INFANT_TICKETS = 2;

        assertDoesNotThrow(() -> adultTicketsRequestedIsValid(new TicketBasket(ADULT_TICKETS, 0, INFANT_TICKETS)));
    }

    @Test
    void invalidAdultTickets_MoreInfantThanAdult() {
        Integer ADULT_TICKETS = 4;
        Integer INFANT_TICKETS = 6;

        assertThrows(InsufficientAdultTicketsRequestedException.class,
                () -> adultTicketsRequestedIsValid(new TicketBasket(ADULT_TICKETS, 0, INFANT_TICKETS)));
    }

    @Test
    void invalidAdultTickets_ChildNoAdults() {
        Integer CHILD_TICKETS = 6;

        assertThrows(InsufficientAdultTicketsRequestedException.class,
                () -> adultTicketsRequestedIsValid(new TicketBasket(0, CHILD_TICKETS, 0)));
    }

    @Test
    void validNumberOfTickets_FewerThan20() {
        Integer ADULT_TICKETS = 4;
        Integer CHILD_TICKETS = 3;
        Integer INFANT_TICKETS = 2;

        assertDoesNotThrow(() -> ticketQuantityIsValid(new TicketBasket(ADULT_TICKETS, CHILD_TICKETS, INFANT_TICKETS), 20));
    }

    @Test
    void validNumberOfTickets_Exactly20() {
        Integer ADULT_TICKETS = 15;
        Integer CHILD_TICKETS = 3;
        Integer INFANT_TICKETS = 2;

        assertDoesNotThrow(() -> ticketQuantityIsValid(new TicketBasket(ADULT_TICKETS, CHILD_TICKETS, INFANT_TICKETS), 20));
    }

    @Test
    void invalidNumberOfTickets_MoreThan20() {
        Integer ADULT_TICKETS = 15;
        Integer CHILD_TICKETS = 5;
        Integer INFANT_TICKETS = 10;

        assertThrows(InvalidTicketQuantityException.class,
                () -> ticketQuantityIsValid(new TicketBasket(ADULT_TICKETS, CHILD_TICKETS, INFANT_TICKETS), 20));
    }

}
