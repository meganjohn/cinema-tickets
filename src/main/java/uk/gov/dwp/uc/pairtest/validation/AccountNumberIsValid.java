package uk.gov.dwp.uc.pairtest.validation;

public class AccountNumberIsValid {

    public static boolean accountNumberIsValid(long accountId) {
        return accountId > 0;
    }
}
