package uk.gov.dwp.uc.pairtest.exception;

public class InvalidAccountIdException extends InvalidPurchaseException {
    public InvalidAccountIdException(String message) {
        super(message);
    }
}
