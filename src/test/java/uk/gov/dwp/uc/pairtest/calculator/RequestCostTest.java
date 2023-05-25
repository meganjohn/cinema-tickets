package uk.gov.dwp.uc.pairtest.calculator;

import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketBasket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.dwp.uc.pairtest.domain.calculators.RequestCost.calculatePrice;

public class RequestCostTest {

    @Test
    void calculatesCorrectCost() {
        TicketBasket basket = new TicketBasket(10, 5, 7);

        assertEquals(calculatePrice(basket), 250);
    }

}
