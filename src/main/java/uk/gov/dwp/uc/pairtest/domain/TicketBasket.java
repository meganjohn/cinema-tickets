package uk.gov.dwp.uc.pairtest.domain;

public final class TicketBasket {

    private Integer adultTickets;
    private Integer childTickets;
    private Integer infantTickets;

    public TicketBasket() {
        adultTickets = childTickets = infantTickets = 0;
    }

    public TicketBasket(Integer adultTickets, Integer childTickets, Integer infantTickets) {
        this.adultTickets = adultTickets;
        this.childTickets = childTickets;
        this.infantTickets = infantTickets;
    }

    public Integer getAdultTickets() {
        return adultTickets;
    }

    public Integer getChildTickets() {
        return childTickets;
    }

    public Integer getInfantTickets() {
        return infantTickets;
    }

    public void setAdultTickets(Integer noOfTickets) {
        this.adultTickets = noOfTickets;
    }

    public void setChildTickets(Integer noOfTickets) {
        this.childTickets = noOfTickets;
    }

    public void setInfantTickets(Integer noOfTickets) {
        this.infantTickets = noOfTickets;
    }

    public void addAdultTickets(Integer noOfTickets) {
        this.adultTickets = getAdultTickets() + noOfTickets;
    }

    public void addChildTickets(Integer noOfTickets) {
        this.childTickets = getChildTickets() + noOfTickets;
    }

    public void addInfantTickets(Integer noOfTickets) {
        this.infantTickets = getInfantTickets() + noOfTickets;
    }

    public TicketBasket buildBasket(TicketPurchaseRequest purchaseRequest) {
        TicketBasket basket = new TicketBasket();

        for (TicketRequest request : purchaseRequest.getTicketTypeRequests()
        ) {
            switch (request.getTicketType()) {
                case ADULT -> basket.addAdultTickets(request.getNoOfTickets());
                case CHILD -> basket.addChildTickets(request.getNoOfTickets());
                case INFANT -> basket.addInfantTickets(request.getNoOfTickets());
            }
        }

        return basket;
    }
}