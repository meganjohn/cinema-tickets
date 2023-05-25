package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketBasket;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.exception.InsufficientAdultTicketsRequestedException;
import uk.gov.dwp.uc.pairtest.exception.InvalidAccountIdException;
import uk.gov.dwp.uc.pairtest.exception.InvalidTicketQuantityException;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static uk.gov.dwp.uc.pairtest.validation.AccountIdIsValid.AccountIdIsValid;
import static uk.gov.dwp.uc.pairtest.validation.AdultTicketsRequestedIsValid.adultTicketsRequestedIsValid;
import static uk.gov.dwp.uc.pairtest.validation.TicketNumberIsValid.ticketQuantityIsValid;

public class ValidationTest {

    private static final Integer MAX_TICKETS = 20;

    @Test
    void validAccountId() {
        assertDoesNotThrow(() -> AccountIdIsValid(12345L));
    }

    @Test
    void invalidAccountId_Zero() {
        assertThrows(InvalidAccountIdException.class, () -> AccountIdIsValid(0L));
    }

    @Test
    void invalidAccountId_Negative() {
        assertThrows(InvalidAccountIdException.class, () -> AccountIdIsValid(-10L));
    }

    @Test
    void validAdultTickets_ChildInfant() {
        Integer ADULT_TICKETS = 4;
        Integer CHILD_TICKETS = 3;
        Integer INFANT_TICKETS = 2;
        EnumMap<TicketRequest.Type, Integer> basketMap = new EnumMap<>(
                Map.of(
                        TicketRequest.Type.ADULT, ADULT_TICKETS,
                        TicketRequest.Type.CHILD, CHILD_TICKETS,
                        TicketRequest.Type.INFANT, INFANT_TICKETS));
        final TicketBasket basket = new TicketBasket();
        basket.setBasket(basketMap);

        assertDoesNotThrow(() -> adultTicketsRequestedIsValid(basket));
    }

    @Test
    void validAdultTickets_NoChildNoInfant() {
        Integer ADULT_TICKETS = 2;
        EnumMap<TicketRequest.Type, Integer> basketMap = new EnumMap<>(
                Map.of(
                        TicketRequest.Type.ADULT, ADULT_TICKETS));
        final TicketBasket basket = new TicketBasket();
        basket.setBasket(basketMap);

        assertDoesNotThrow(() -> adultTicketsRequestedIsValid(basket));
    }

    @Test
    void validAdultTickets_MoreChildThanAdult() {
        Integer ADULT_TICKETS = 2;
        Integer CHILD_TICKETS = 4;
        EnumMap<TicketRequest.Type, Integer> basketMap = new EnumMap<>(
                Map.of(
                        TicketRequest.Type.ADULT, ADULT_TICKETS,
                        TicketRequest.Type.CHILD, CHILD_TICKETS));
        final TicketBasket basket = new TicketBasket();
        basket.setBasket(basketMap);

        assertDoesNotThrow(() -> adultTicketsRequestedIsValid(basket));
    }

    @Test
    void validAdultTickets_InfantNoChild() {
        Integer ADULT_TICKETS = 4;
        Integer INFANT_TICKETS = 2;
        EnumMap<TicketRequest.Type, Integer> basketMap = new EnumMap<>(
                Map.of(
                        TicketRequest.Type.ADULT, ADULT_TICKETS,
                        TicketRequest.Type.INFANT, INFANT_TICKETS));
        final TicketBasket basket = new TicketBasket();
        basket.setBasket(basketMap);

        assertDoesNotThrow(() -> adultTicketsRequestedIsValid(basket));
    }

    @Test
    void invalidAdultTickets_MoreInfantThanAdult() {
        Integer ADULT_TICKETS = 4;
        Integer INFANT_TICKETS = 6;
        EnumMap<TicketRequest.Type, Integer> basketMap = new EnumMap<>(
                Map.of(
                        TicketRequest.Type.ADULT, ADULT_TICKETS,
                        TicketRequest.Type.INFANT, INFANT_TICKETS));
        final TicketBasket basket = new TicketBasket();
        basket.setBasket(basketMap);

        assertThrows(InsufficientAdultTicketsRequestedException.class,
                () -> adultTicketsRequestedIsValid(basket));
    }

    @Test
    void invalidAdultTickets_ChildNoAdults() {
        Integer CHILD_TICKETS = 6;
        EnumMap<TicketRequest.Type, Integer> basketMap = new EnumMap<>(
                Map.of(
                        TicketRequest.Type.CHILD, CHILD_TICKETS));
        final TicketBasket basket = new TicketBasket();
        basket.setBasket(basketMap);

        assertThrows(InsufficientAdultTicketsRequestedException.class,
                () -> adultTicketsRequestedIsValid(basket));
    }

    @Test
    void validNumberOfTickets_FewerThanMaxTickets() {
        Integer ADULT_TICKETS = 4;
        Integer CHILD_TICKETS = 3;
        Integer INFANT_TICKETS = 2;
        EnumMap<TicketRequest.Type, Integer> basketMap = new EnumMap<>(
                Map.of(
                        TicketRequest.Type.ADULT, ADULT_TICKETS,
                        TicketRequest.Type.CHILD, CHILD_TICKETS,
                        TicketRequest.Type.INFANT, INFANT_TICKETS));
        final TicketBasket basket = new TicketBasket();
        basket.setBasket(basketMap);

        assertDoesNotThrow(() -> ticketQuantityIsValid(basket, MAX_TICKETS));
    }

    @Test
    void validNumberOfTickets_ExactlyMaxTickets() {
        Integer ADULT_TICKETS = 15;
        Integer CHILD_TICKETS = 3;
        Integer INFANT_TICKETS = 2;
        EnumMap<TicketRequest.Type, Integer> basketMap = new EnumMap<>(
                Map.of(
                        TicketRequest.Type.ADULT, ADULT_TICKETS,
                        TicketRequest.Type.CHILD, CHILD_TICKETS,
                        TicketRequest.Type.INFANT, INFANT_TICKETS));
        final TicketBasket basket = new TicketBasket();
        basket.setBasket(basketMap);

        assertDoesNotThrow(() -> ticketQuantityIsValid(basket, MAX_TICKETS));
    }

    @Test
    void invalidNumberOfTickets_MoreThanMaxTickets() {
        Integer ADULT_TICKETS = 15;
        Integer CHILD_TICKETS = 5;
        Integer INFANT_TICKETS = 10;
        EnumMap<TicketRequest.Type, Integer> basketMap = new EnumMap<>(
                Map.of(
                        TicketRequest.Type.ADULT, ADULT_TICKETS,
                        TicketRequest.Type.CHILD, CHILD_TICKETS,
                        TicketRequest.Type.INFANT, INFANT_TICKETS));
        final TicketBasket basket = new TicketBasket();
        basket.setBasket(basketMap);

        assertThrows(InvalidTicketQuantityException.class,
                () -> ticketQuantityIsValid(basket, MAX_TICKETS));
    }

}
