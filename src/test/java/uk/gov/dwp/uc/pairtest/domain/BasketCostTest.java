package uk.gov.dwp.uc.pairtest.domain;

import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketBasket;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.domain.BasketCost;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasketCostTest {

    private final BasketCost basketCost = new BasketCost();

    @Test
    void calculatesCorrectCost() {
        EnumMap<TicketRequest.Type, Integer> basketMap = new EnumMap<>(
                Map.of(
                        TicketRequest.Type.ADULT, 10,
                        TicketRequest.Type.CHILD, 5,
                        TicketRequest.Type.INFANT, 7));
        TicketBasket basket = new TicketBasket();
        basket.setBasket(basketMap);

        assertEquals(basketCost.calculatePrice(basket), 250);
    }

}
