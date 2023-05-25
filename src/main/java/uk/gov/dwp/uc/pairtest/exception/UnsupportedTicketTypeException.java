package uk.gov.dwp.uc.pairtest.exception;

public class UnsupportedTicketTypeException extends InvalidPurchaseException {
    public UnsupportedTicketTypeException(String message) {
        super(message);
    }
}
