package com.pablosanchezegido.petcity.features.offers.map;

import com.pablosanchezegido.petcity.models.LatLng;
import com.pablosanchezegido.petcity.models.Offer;

import java.util.List;

interface OffersMapInteractor {

    interface OnOffersFetchedListener {
        void onSuccess(List<Offer> offers);
        void onError(String error);
    }

    void fetchData(LatLng latLng, double radius, OnOffersFetchedListener listener);
    void fetchDataWithoutLocation(OnOffersFetchedListener listener);
}
