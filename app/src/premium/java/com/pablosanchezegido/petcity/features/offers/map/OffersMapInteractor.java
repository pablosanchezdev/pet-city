package com.pablosanchezegido.petcity.features.offers.map;

import android.support.annotation.Nullable;

import com.pablosanchezegido.petcity.models.Offer;
import com.pablosanchezegido.petcity.models.LatLng;

import java.util.List;

interface OffersMapInteractor {

    interface OnOffersFetchedListener {
        void onSuccess(List<Offer> offers);
        void onError(String error);
    }

    void fetchData(@Nullable LatLng latLng, double radius, OnOffersFetchedListener listener);
}
