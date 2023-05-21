package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TicketServiceTest {

    private final long ACCOUNT_ID = 123;
    private final TicketRequest ADULT_TICKET_REQUEST = new TicketRequest(TicketRequest.Type.ADULT, 5);
    private final TicketRequest CHILD_TICKET_REQUEST = new TicketRequest(TicketRequest.Type.CHILD, 2);
    private final TicketRequest INFANT_TICKET_REQUEST = new TicketRequest(TicketRequest.Type.INFANT, 3);

    private final TicketPaymentServiceImpl paymentService = Mockito.mock(TicketPaymentServiceImpl.class);
    private final SeatReservationService reservationService = Mockito.mock(SeatReservationService.class);

    private final TicketServiceImpl ticketService = new TicketServiceImpl(paymentService, reservationService);


    @Test
    void validRequest_OneTicketRequestForEachType() {

        // given a request
        TicketRequest[] ticketRequests = new TicketRequest[] {ADULT_TICKET_REQUEST, CHILD_TICKET_REQUEST, INFANT_TICKET_REQUEST};
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(ACCOUNT_ID, ticketRequests);

        // when
        ticketService.purchaseTickets(ticketPurchaseRequest);

        // then
        verify(paymentService, times(1)).makePayment(ACCOUNT_ID, 120);
        verify(reservationService, times(1)).reserveSeat(ACCOUNT_ID, 7);
    }

    @Test
    void validRequest_TwoOfOneTypeOfTicketRequests() {

        // given a request
        TicketRequest ADDITIONAL_ADULT_TICKET_REQUEST = new TicketRequest(TicketRequest.Type.ADULT, 2);

        TicketRequest[] ticketRequests = new TicketRequest[] {ADULT_TICKET_REQUEST, CHILD_TICKET_REQUEST, INFANT_TICKET_REQUEST, ADDITIONAL_ADULT_TICKET_REQUEST};
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(ACCOUNT_ID, ticketRequests);

        // when
        ticketService.purchaseTickets(ticketPurchaseRequest);

        // then
        verify(paymentService, times(1)).makePayment(ACCOUNT_ID, 160);
        verify(reservationService, times(1)).reserveSeat(ACCOUNT_ID, 9);
    }

    @Test
    void validRequest_Exactly20TicketsRequested() {

        // given a request
        TicketRequest ADDITIONAL_ADULT_TICKET_REQUEST = new TicketRequest(TicketRequest.Type.ADULT, 13);

        TicketRequest[] ticketRequests = new TicketRequest[] {ADULT_TICKET_REQUEST, CHILD_TICKET_REQUEST, ADDITIONAL_ADULT_TICKET_REQUEST};
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(ACCOUNT_ID, ticketRequests);

        // when
        ticketService.purchaseTickets(ticketPurchaseRequest);

        // then
        verify(paymentService, times(1)).makePayment(ACCOUNT_ID, 380);
        verify(reservationService, times(1)).reserveSeat(ACCOUNT_ID, 20);
    }

    @Test
    void invalidRequest_NoAdultTickets() {

        // given an invalid request
        TicketRequest[] ticketRequests = new TicketRequest[] {CHILD_TICKET_REQUEST, INFANT_TICKET_REQUEST};
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(ACCOUNT_ID, ticketRequests);

        // when
        Exception exception = assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(ticketPurchaseRequest));

        // then
        assertTrue(exception.getMessage().contentEquals("Insufficient adult tickets requested."));
        verifyNoInteractions(paymentService);
        verifyNoInteractions(reservationService);
    }

    @Test
    void invalidRequest_MoreInfantThanAdultTickets() {

        // given an invalid request
        TicketRequest LARGE_INFANT_TICKET_REQUEST = new TicketRequest(TicketRequest.Type.INFANT, 10);

        TicketRequest[] ticketRequests = new TicketRequest[] {ADULT_TICKET_REQUEST, CHILD_TICKET_REQUEST, LARGE_INFANT_TICKET_REQUEST};
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(ACCOUNT_ID, ticketRequests);

        // when
        Exception exception = assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(ticketPurchaseRequest));

        // then
        assertTrue(exception.getMessage().contentEquals("Insufficient adult tickets requested."));
        verifyNoInteractions(paymentService);
        verifyNoInteractions(reservationService);
    }

    @Test
    void invalidRequest_TooManyTicketsOfOneType() {

        // given an invalid request
        final TicketRequest INVALID_ADULT_TICKET_REQUEST = new TicketRequest(TicketRequest.Type.ADULT, 21);

        TicketRequest[] ticketRequests = new TicketRequest[] {INVALID_ADULT_TICKET_REQUEST, CHILD_TICKET_REQUEST, INFANT_TICKET_REQUEST};
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(ACCOUNT_ID, ticketRequests);

        // when
        Exception exception = assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(ticketPurchaseRequest));

        // then
        assertTrue(exception.getMessage().contentEquals("Invalid quantity of tickets requested."));
        verifyNoInteractions(paymentService);
        verifyNoInteractions(reservationService);
    }

    @Test
    void invalidRequest_TooManyTicketsAcrossAllTypes() {

        // given an invalid request
        final TicketRequest INVALID_ADULT_TICKET_REQUEST = new TicketRequest(TicketRequest.Type.ADULT, 15);
        final TicketRequest INVALID_CHILD_TICKET_REQUEST = new TicketRequest(TicketRequest.Type.CHILD, 5);
        final TicketRequest INVALID_INFANT_TICKET_REQUEST = new TicketRequest(TicketRequest.Type.INFANT, 3);


        TicketRequest[] ticketRequests = new TicketRequest[] {INVALID_ADULT_TICKET_REQUEST, INVALID_CHILD_TICKET_REQUEST, INVALID_INFANT_TICKET_REQUEST};
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(ACCOUNT_ID, ticketRequests);

        // when
        Exception exception = assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(ticketPurchaseRequest));

        // then
        assertTrue(exception.getMessage().contentEquals("Invalid quantity of tickets requested."));
        verifyNoInteractions(paymentService);
        verifyNoInteractions(reservationService);
    }

    @Test
    void invalidRequest_InvalidAccountId() {

        // given an invalid request
        final long INVALID_ACCOUNT_ID = 0;

        TicketRequest[] ticketRequests = new TicketRequest[] {ADULT_TICKET_REQUEST, CHILD_TICKET_REQUEST, INFANT_TICKET_REQUEST};
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(INVALID_ACCOUNT_ID, ticketRequests);

        // when
        Exception exception = assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(ticketPurchaseRequest));

        // then
        assertTrue(exception.getMessage().contentEquals("Invalid account ID provided."));
        verifyNoInteractions(paymentService);
        verifyNoInteractions(reservationService);
    }

}
