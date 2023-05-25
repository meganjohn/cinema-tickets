package uk.gov.dwp.uc.pairtest.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.gov.dwp.uc.pairtest.exception.InvalidAccountIdException;

public class AccountIdIsValid {

    private static final Logger LOGGER = LogManager.getLogger(AccountIdIsValid.class);

    public static void AccountIdIsValid(long accountId) {
        if (!(accountId > 0)) {
            LOGGER.error("Invalid account ID: {}.", accountId);
            throw new InvalidAccountIdException(String.format("Invalid account ID provided. Account ID: %s", accountId));
        }
    }
}
