package com.pablosanchezegido.petcity.features.offers.list;

import com.google.android.gms.maps.model.LatLng;
import com.pablosanchezegido.petcity.features.common.OffersInteractor;
import com.pablosanchezegido.petcity.models.Offer;
import com.pablosanchezegido.petcity.utils.BoundaryLatLng;
import com.pablosanchezegido.petcity.utils.LocationUtilsKt;

import java.util.List;

class OffersListInteractorImpl implements OffersListInteractor {

    @Override
    public void fetchData(OnFetchDataListener listener) {
        // Mock user location and radius
        BoundaryLatLng boundary = LocationUtilsKt.getBoundaryLatLngForRadius(new LatLng(40.974808, -5.6649207), 5000.0);
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