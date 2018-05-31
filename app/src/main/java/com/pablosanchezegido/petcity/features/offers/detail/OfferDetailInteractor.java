package com.pablosanchezegido.petcity.features.offers.detail;

import com.pablosanchezegido.petcity.models.Offer;

public interface OfferDetailInteractor {

    interface OnOfferFetchedListener {
        void onSuccess(Offer offer);
        void onError(String error);
    }

    interface OnOfferAcceptedListener {
        void onSuccess();
        void onError(String error);
    }

    void fetchOfferDetail(String offerId, OnOfferFetchedListener listener);
    void acceptOffer(Offer offer, OnOfferAcceptedListener listener);
}
