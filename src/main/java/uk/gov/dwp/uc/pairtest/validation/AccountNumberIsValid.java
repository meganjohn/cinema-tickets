package uk.gov.dwp.uc.pairtest.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.gov.dwp.uc.pairtest.exception.InvalidAccountNumberException;

public class AccountNumberIsValid {

    private static final Logger LOGGER = LogManager.getLogger(AccountNumberIsValid.class);

    public static void accountNumberIsValid(long accountId) {
        if (!(accountId > 0)) {
            LOGGER.error("Invalid account ID: {}.", accountId);
            throw new InvalidAccountNumberException("Invalid account ID provided.");
        }
    }
}
