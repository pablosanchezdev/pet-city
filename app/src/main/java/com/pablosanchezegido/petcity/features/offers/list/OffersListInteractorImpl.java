package com.pablosanchezegido.petcity.features.offers.list;

import com.pablosanchezegido.petcity.features.common.OffersInteractor;
import com.pablosanchezegido.petcity.models.LatLng;
import com.pablosanchezegido.petcity.models.Offer;
import com.pablosanchezegido.petcity.utils.BoundaryLatLng;
import com.pablosanchezegido.petcity.utils.LocationUtilsKt;
import com.pablosanchezegido.petcity.utils.ModelMapperKt;

import java.util.List;

class OffersListInteractorImpl implements OffersListInteractor {

    private OffersInteractor offersInteractor;

    OffersListInteractorImpl() {
        offersInteractor = new OffersInteractor();
    }

    @Override
    public void fetchData(LatLng position, double radius, OnFetchDataListener listener) {
        BoundaryLatLng boundary = LocationUtilsKt.getBoundaryLatLngForRadius(ModelMapperKt.latLngToLatLng(position), radius);

        offersInteractor.fetchOffersWithinBounds(boundary, new OffersInteractor.OnOffersFetched() {
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

    @Override
    public void fetchDataWithoutLocation(OnFetchDataListener listener) {
        offersInteractor.fetchAllOffers(new OffersInteractor.OnOffersFetched() {
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
