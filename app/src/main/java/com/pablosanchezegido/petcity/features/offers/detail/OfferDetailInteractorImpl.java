package com.pablosanchezegido.petcity.features.offers.detail;

import com.pablosanchezegido.petcity.features.common.OffersInteractor;
import com.pablosanchezegido.petcity.models.Offer;

public class OfferDetailInteractorImpl implements OfferDetailInteractor {

    OfferDetailInteractorImpl() { }

    @Override
    public void fetchOfferDetail(String offerId, OnOfferFetchedListener listener) {
        new OffersInteractor().fetchOffer(offerId, new OffersInteractor.OnOfferFetchedListener() {
            @Override
            public void onSuccess(Offer offer) {
                listener.onSuccess(offer);
            }

            @Override
            public void onError(String error) {
                listener.onError(error);
            }
        });
    }

    @Override
    public void acceptOffer(Offer offer, OnOfferAcceptedListener listener) {
        new OffersInteractor().acceptOffer(offer, new OffersInteractor.OnOfferAcceptedListener() {
            @Override
            public void onSuccess() {
                listener.onSuccess();
            }

            @Override
            public void onError(String error) {
                listener.onError(error);
            }
        });
    }
}
