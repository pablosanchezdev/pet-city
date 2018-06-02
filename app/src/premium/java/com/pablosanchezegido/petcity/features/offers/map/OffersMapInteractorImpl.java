package com.pablosanchezegido.petcity.features.offers.map;

import android.support.annotation.Nullable;

import com.pablosanchezegido.petcity.features.common.OffersInteractor;
import com.pablosanchezegido.petcity.models.Offer;
import com.pablosanchezegido.petcity.utils.BoundaryLatLng;
import com.pablosanchezegido.petcity.utils.LatLng;
import com.pablosanchezegido.petcity.utils.LocationUtilsKt;

import java.util.List;

class OffersMapInteractorImpl implements OffersMapInteractor {

    OffersMapInteractorImpl() { }

    @Override
    public void fetchData(@Nullable LatLng latLng, double radius, OnOffersFetchedListener listener) {
        BoundaryLatLng boundary = null;
        if (latLng != null) {
            boundary = LocationUtilsKt.getBoundaryLatLngForRadius(new com.google.android.gms.maps.model.LatLng(latLng.getLat(), latLng.getLng()), radius);
        }
        new OffersInteractor().fetchOffersWithinBounds(boundary, new OffersInteractor.OnOffersFetched() {
            @Override
            public void onSuccess(List<Offer> offers) {
                listener.onSuccess(offers);
            }

            @Override
            public void onError(String error) {
                listener.onError(error);
            }
        });
    }
}
