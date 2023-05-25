package uk.gov.dwp.uc.pairtest.exception;

public class InsufficientAdultTicketsRequestedException extends InvalidPurchaseException {
    public InsufficientAdultTicketsRequestedException(String message) {
        super(message);
    }
}
