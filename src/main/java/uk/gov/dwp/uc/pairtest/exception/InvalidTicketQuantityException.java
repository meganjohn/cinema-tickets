package uk.gov.dwp.uc.pairtest.exception;

public class InvalidTicketQuantityException extends InvalidPurchaseException {
    public InvalidTicketQuantityException(String message) {
        super(message);
    }
}
