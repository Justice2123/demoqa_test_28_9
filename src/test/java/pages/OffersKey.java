package pages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class OffersKey {
    @JsonIgnoreProperties(ignoreUnknown = true)

    public List<Offers> getOffers() {
        return offers;
    }

    public void setOffers(List<Offers> offers) {
        this.offers = offers;
    }

    private List<Offers> offers;


}
