package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static uk.gov.dwp.uc.pairtest.validation.TicketPurchaseRequestValidator.validateTicketPurchaseRequest;


public class TicketServiceImpl implements TicketService {

    private final TicketPaymentService paymentService;
    private final SeatReservationService seatReservationService;
    public TicketServiceImpl (TicketPaymentServiceImpl paymentService,
                              SeatReservationService seatReservationService) {
        this.paymentService = paymentService;
        this.seatReservationService = seatReservationService;
    }

    /**
     * Should only have private methods other than the one below.
     */
    @Override
    public void purchaseTickets(TicketPurchaseRequest ticketPurchaseRequest) throws InvalidPurchaseException {

        // validate ticket purchase request
        validateTicketPurchaseRequest(ticketPurchaseRequest);

        // calculate amount to pay
        int totalToPay = 0;
        int totalSeats = 0;

        for (TicketRequest ticketRequest: ticketPurchaseRequest.getTicketTypeRequests()
             ) {
            switch (ticketRequest.getTicketType()) {
                case ADULT -> {
                    totalToPay = totalToPay + (ticketRequest.getNoOfTickets() * 20);
                    totalSeats = totalSeats + ticketRequest.getNoOfTickets();
                }
                case CHILD -> {
                    totalToPay = totalToPay + (ticketRequest.getNoOfTickets() *10);
                    totalSeats = totalSeats + ticketRequest.getNoOfTickets();
                }
            }
        }

        // make call to ticket payment service
        paymentService.makePayment(ticketPurchaseRequest.getAccountId(), totalToPay);

        //make call to seat reservation service
        seatReservationService.reserveSeat(ticketPurchaseRequest.getAccountId(), totalSeats);
    }
}
