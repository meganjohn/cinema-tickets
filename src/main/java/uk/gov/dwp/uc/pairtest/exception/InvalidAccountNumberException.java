package uk.gov.dwp.uc.pairtest.exception;

public class InvalidAccountNumberException extends InvalidPurchaseException {
    public InvalidAccountNumberException(String message) {
        super(message);
    }
}
