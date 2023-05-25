package uk.gov.dwp.uc.pairtest.domain;

import java.util.EnumMap;

public class TicketBasket {

    EnumMap<TicketRequest.Type, Integer> basket;

    public TicketBasket() {
        this.basket = new EnumMap<>(TicketRequest.Type.class);
    }

    public TicketBasket(TicketPurchaseRequest purchaseRequest) {
        this.basket = new EnumMap<>(TicketRequest.Type.class);

        for (TicketRequest request : purchaseRequest.getTicketTypeRequests()
        ) {
            addTicketsToBasket(request.getTicketType(), request.getNoOfTickets());
        }
    }

    public EnumMap<TicketRequest.Type, Integer> getBasket() {
        return basket;
    }

    public Integer getTicketsForType(TicketRequest.Type type) {
        return basket.getOrDefault(type, 0);
    }

    public void setBasket(EnumMap<TicketRequest.Type, Integer> ticketBasket) {
        basket = ticketBasket;
    }

    public void addTicketsToBasket(TicketRequest.Type type, Integer noOfTickets) {
        Integer currentTicketsForType = getTicketsForType(type);
        basket.put(type, currentTicketsForType + noOfTickets);
    }

    public Integer getTotalTickets() {
        int totalTickets = 0;
        for (TicketRequest.Type type : TicketRequest.Type.values()) {
            totalTickets = totalTickets + basket.getOrDefault(type, 0);
        }
        return totalTickets;
    }
}