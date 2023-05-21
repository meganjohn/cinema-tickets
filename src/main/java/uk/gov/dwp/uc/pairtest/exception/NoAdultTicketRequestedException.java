package uk.gov.dwp.uc.pairtest.exception;

public class NoAdultTicketRequestedException extends InvalidPurchaseException{
    public NoAdultTicketRequestedException(String message) {
        super(message);
    }
}
