package com.pablosanchezegido.petcity.features.offers.detail;

public interface OfferDetailPresenter {

    void fetchOfferDetail(String offerId);
    void acceptOfferRequested();
    void acceptOffer();
    void destroy();
}
