package uk.gov.dwp.uc.pairtest.domain;


import uk.gov.dwp.uc.pairtest.domain.TicketBasket;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.exception.UnsupportedTicketTypeException;

import java.util.EnumMap;

public class BasketCost {

    EnumMap<TicketRequest.Type, Integer> priceMap;

    public BasketCost() {
        this.priceMap = new EnumMap<>(TicketRequest.Type.class);

        // With Spring, these could be configurable values stored in the application.yml
        // Or use PropertiesConfiguration
        priceMap.put(TicketRequest.Type.ADULT, 20);
        priceMap.put(TicketRequest.Type.CHILD, 10);
        priceMap.put(TicketRequest.Type.INFANT, 0);
    }

    private Integer getPriceForType(TicketRequest.Type type) {
        if (priceMap.get(type) != null) {
            return priceMap.get(type);
        } else {
            throw new UnsupportedTicketTypeException(String.format("Cannot get price for ticket type: %s", type));
        }
    }

    public int calculatePrice(TicketBasket ticketBasket) {
        int totalCost = 0;
        for (TicketRequest.Type type : TicketRequest.Type.values()) {
            totalCost = totalCost + ticketBasket.getTicketsForType(type) * getPriceForType(type);
        }
        return totalCost;
    }
}
