package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketBasket;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static uk.gov.dwp.uc.pairtest.domain.calculators.RequestCost.calculatePrice;
import static uk.gov.dwp.uc.pairtest.validation.TicketPurchaseRequestValidator.validateTicketPurchaseRequest;


public class TicketServiceImpl implements TicketService {

    private final TicketPaymentService paymentService;
    private final SeatReservationService seatReservationService;

    public TicketServiceImpl(TicketPaymentServiceImpl paymentService,
                             SeatReservationService seatReservationService) {
        this.paymentService = paymentService;
        this.seatReservationService = seatReservationService;
    }

    /**
     * Should only have private methods other than the one below.
     */
    @Override
    public void purchaseTickets(TicketPurchaseRequest ticketPurchaseRequest) throws InvalidPurchaseException {

        TicketBasket ticketBasket = new TicketBasket().buildBasket(ticketPurchaseRequest);

        validateTicketPurchaseRequest(ticketBasket, ticketPurchaseRequest.getAccountId());

        paymentService.makePayment(ticketPurchaseRequest.getAccountId(), calculatePrice(ticketBasket));

        seatReservationService.reserveSeat(ticketPurchaseRequest.getAccountId(), ticketBasket.getAdultTickets() + ticketBasket.getChildTickets());
    }
}
